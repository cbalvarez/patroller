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
import javax.servlet.http.HttpServletRequest
import net.liftweb.json.ext.EnumSerializer
import com.despegar.patroller.dao.TTrafficType
import com.despegar.patroller.services.ReputationProcessors._
import com.despegar.patroller.services.Listing
import com.despegar.patroller.dao.ReportEventElem
import org.scalatra._

class ReportController extends ScalatraServlet with StrictLogging  {
  val formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
    private val default: DefaultFormats with Object {def dateFormatter: SimpleDateFormat} = new DefaultFormats {
        override def dateFormatter = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    }  
  implicit val liftJsonFormats = default ++ JodaTimeSerializers.all + new EnumSerializer(TTrafficType)

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
      Report.reportUnknownDestEvent(ReportEvent(host, vm, srcIp, dstIp, srcPort, dstPort, trafficType, ts, Map.empty[ReputationProcessors, String]))
      "OK"
    } catch {
      case e:Exception => logger.debug("exception e:" + e.getStackTraceString)
      throw e
    }
     
  }
  
  def parseDates(params:org.scalatra.Params) = {
   val start = formatter.parseDateTime(params("begin")) 
   val end = formatter.parseDateTime(params("end"))
   (start,end)
  }
  

  def currentAlerts(params:Params,filter:(ReportEventElem) => java.lang.Boolean ) = {
   val typeVms = params("typeVms")
   if (typeVms != "ALL" && typeVms != "WATCHED") {
     halt(400, s"type not allowed ${typeVms}")
   }
   val (start, end) = parseDates(params)    
   Listing.filterPostProcess(() => { Listing.listCurrentAlerts(typeVms, start, end) }, filter )  
  }
  
  get("/currentalerts/:typeVms/:begin/:end/badrep") {
    write(currentAlerts(params, Listing.filterBadReputation ))
  }
  
  get("/currentalerts/:typeVms/:begin/:end") {
   write(currentAlerts(params, (re:ReportEventElem) => {true}))
  }

  def byVmAlerts(params:Params, f:(ReportEventElem) =>  java.lang.Boolean )  = {
    val vm = params("vm")
    val trafficType = params("trafficType")
    val (start, end) = parseDates(params)
    Listing.filterPostProcess(() => { Listing.listByVm(vm, start, end, trafficType) }, f )
  }
  
  get("/byvmalerts/:vm/:begin/:end/:trafficType") {
    write(byVmAlerts(params, (re:ReportEventElem) => {true} ))   
  }
  
  get("/byvmalerts/:vm/:begin/:end/:trafficType/badrep") {
    write(byVmAlerts(params, Listing.filterBadReputation ))   
  }
  
  def byTrafficType(params:Params, f:(ReportEventElem) => java.lang.Boolean) = {
    val trafficType = params("trafficType")
    val (start, end) = parseDates(params)
    Listing.filterPostProcess(() => { Listing.listByTrafficType(trafficType, start, end ) }, f )    
  }
  
  get("/bytraffictype/:traffictype/:begin:/end") {
    write(byTrafficType(params, (re:ReportEventElem) => { true } ))    
  }

  get("/bytraffictype/:traffictype/:begin:/end/badrep") {
    write(byTrafficType(params, Listing.filterBadReputation))    
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
      val id = Report.addAllowed(reason, filterValue, filterType)
      write(id.toString())
    }    
  }
  
  get("/trace/:host") {
    val host = params("host")
    write(PatrollSettings.trace(host))
  } 
  
  post("/svm") {
    val j = parse(request.body)
    val vm = (j \ "vm").values.toString()
    Report.addSpuriousVm(vm)
    write("OK")
  }
  
  
  
}