package com.despegar.patroller.controllers

import org.scalatra.ScalatraServlet
import com.typesafe.scalalogging.slf4j.Logging
import net.liftweb.json._
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import com.despegar.patroller.services.Report
import scala.language.implicitConversions
import java.text.SimpleDateFormat
import net.liftweb.json.ext.JodaTimeSerializers
import net.liftweb.json.Serialization.{ read, write }
import com.despegar.patroller.dao.ReportEvent
import com.despegar.patroller.services.PatrollSettings


class ReportController extends ScalatraServlet with Logging  {
  val formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
    private val default: DefaultFormats with Object {def dateFormatter: SimpleDateFormat} = new DefaultFormats {
        override def dateFormatter = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    }  
  implicit val liftJsonFormats = default ++ JodaTimeSerializers.all

  before() {
    contentType = "application/json"
  }


  post ("/report") {
    val j = parse(request.body)
    val host = (j \ "host").values.toString()
    val vm = (j \ "vm").values.toString()
    val inport = (j \ "inport").values.toString().toInt
    val outport = (j \ "outport").values.toString().toInt
    val ts = formatter.parseDateTime(( j \ "timestamp").values.toString())
    val ip = (j \ "ip").values.toString()
    Report.reportUnknownDestEvent(new ReportEvent(host, vm, ip, outport, inport,  ts))
    "OK"
  }
  
  get("/currentalerts/:begin/:end") {
   val start = formatter.parseDateTime(params("begin")) 
   val end = formatter.parseDateTime(params("end"))
   write(Report.listCurrentAlerts(start, end))
  }
  
  post("/explain") {
    val j = parse(request.body)
    val id = (j \ "id").values.toString().toInt
    val reason = ( j \ "reason" ).values.toString()
    val filterValue = ( j \ "filterValue").values.toString()
    val filterType = ( j \ "filterType" ).values.toString()
    if (filterType != "net" && filterType != "host") {
      halt(400, reason = s"filter type should be net or host")
    } else {
      Report.explain(id, reason, filterValue, filterType)
      "OK"
    }
  }
  
  post("/allowed") {
    val j = parse(request.body)
    val reason = ( j \ "reason" ).values.toString()
    val filterValue = ( j \ "filterValue").values.toString()
    val filterType = ( j \ "filterType" ).values.toString()
    if (filterType != "net" && filterType != "host") {
      halt(400, reason = s"filter type should be net or host")
    } else {
      Report.addAllowed(reason, filterValue, filterType)
      "OK"
    }    
  }
  
  get("/trace/:host") {
    val host = params("host")
    write(PatrollSettings.trace(host))
  } 
  
}