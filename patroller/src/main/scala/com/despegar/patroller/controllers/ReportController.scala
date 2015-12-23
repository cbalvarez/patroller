package com.despegar.patroller.controllers

import org.scalatra.ScalatraServlet
import com.typesafe.scalalogging.slf4j.StrictLogging
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


class ReportController extends ScalatraServlet with StrictLogging  {
  val formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
    private val default: DefaultFormats with Object {def dateFormatter: SimpleDateFormat} = new DefaultFormats {
        override def dateFormatter = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    }  
  implicit val liftJsonFormats = default ++ JodaTimeSerializers.all

  before() {
    contentType = "application/json"
  }



  post ("/report") {
    try {
      val j = parse(request.body)
      println(j.toString())
      val host = (j \ "host").values.toString()
      val vm = (j \ "vm").values.toString()
      val srcPort = (j \ "srcPort").values.toString().toInt
      val dstPort = (j \ "dstPort").values.toString().toInt
      val ts = formatter.parseDateTime(( j \ "timestamp").values.toString())
      val srcIp = (j \ "srcIp").values.toString()
      val dstIp = (j \ "dstIp").values.toString()
      val trafficType = (j \ "trafficType").values.toString()
      Report.reportUnknownDestEvent(new ReportEvent(host, vm, srcIp, dstIp, srcPort, dstPort, trafficType, ts))
      "OK"
    } catch {
      case e:Exception => logger.debug("exception e:" + e.getStackTraceString)
      throw e
    }
     
  }
  
  get("/currentalerts/:begin/:end") {
   val start = formatter.parseDateTime(params("begin")) 
   val end = formatter.parseDateTime(params("end"))
   write(Report.listCurrentAlerts(start, end))
  }
  
  
  post("/testexplainbulk") {
    val j = parse(request.body)
    val id = ( j \ "idExplain").values.toString.toInt
    val start = formatter.parseDateTime(( j \ "start").values.toString())
    val end = formatter.parseDateTime(( j \ "end").values.toString())
    write(Report.testExplainBulk(id, start, end))
  }

  post("/explainbulk") {
    val j = parse(request.body)
    val id = ( j \ "idExplain").values.toString.toInt
    val start = formatter.parseDateTime(( j \ "start").values.toString())
    val end = formatter.parseDateTime(( j \ "end").values.toString())
    write(Report.explainBulk(id, start, end))
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