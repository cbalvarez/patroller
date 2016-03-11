package com.despegar.patroller.dao

import scalikejdbc._
import com.despegar.sbt.madonna.MadonnaConf
import org.joda.time.DateTime
import java.sql.Timestamp
import com.despegar.patroller.services.ReputationProcessors
import com.despegar.patroller.services.ReputationProcessors._


object TTrafficType extends Enumeration {
  type TTrafficType = Value
  val TCP, UDP, UNK, NTPv4, NULL = Value
}

import TTrafficType._


case class TrafficType(code:TTrafficType, desc:String) 
case class ReportEvent(val host:String, val vm:String, val srcIp:String, val dstIp:String, val srcPort:Int, val dstPort:Int, val trafficType:TrafficType, val timestamp:DateTime, val reputation:Map[ReputationProcessors, String]) 
case class ReportEventElem(id:Int, event:ReportEvent)
case class Allowed(typeAllowed:String, allowed:String )

object ReportEvent {
  def apply(r:ReportEvent, reports:Map[ReputationProcessors,String]) = {
    new ReportEvent(r.host, r.vm, r.srcIp, r.dstIp, r.srcPort, r.dstPort, r.trafficType, r.timestamp, reports )
  }
  
  def apply( host:String,  vm:String,  srcIp:String,  dstIp:String,  srcPort:Int, 
                           dstPort:Int,  trafficType:String,  timestamp:DateTime, reputation:Map[ReputationProcessors, String]) = {
    
    val tt = {if ( TTrafficType.values.map (_.toString).contains(trafficType) ) { TTrafficType.withName(trafficType) }
             else if (trafficType.startsWith("Flags")) { TTrafficType.TCP } 
             else TTrafficType.UNK
             }
    new ReportEvent(host, vm, srcIp, dstIp, srcPort, dstPort, TrafficType(tt, trafficType), timestamp, reputation)
  }
}

object PatrollerDao {

  def insertAllowed(idReport:Option[Int], reason:String, filterValue:String, filterType:String )(implicit session:DBSession) = {
    val stmt = sql"""insert into allowed_dest (dst, dst_type, reason, report_id) values 
      (  ${filterValue}, ${filterType}, ${reason}, ${idReport} )"""
    stmt.updateAndReturnGeneratedKey().apply()
  }
  
  def insertTraceQuestion(host:String, timestamp:DateTime, answer:String) = DB localTx { implicit session =>
    val stmt = sql"""insert into trace_question(host, date_question, answer) values 
                        (${host}, ${timestamp}, ${answer})"""
    stmt.update().apply()
  }
  
  def updateStatus(id:Int, status:Int, allowedRuleId:Option[Int])(implicit session:DBSession) = {
    val stmt = sql"""update log_report set status = ${status}, allowed_rule_id = ${allowedRuleId} where id = ${id}"""
    stmt.update().apply
  }

  def enumToBddColumb(rp:ReputationProcessors) = {
   reg
  }
  
  
  def mapReportEventElem(x:WrappedResultSet) = {
    
    val a = ReputationProcessors.values.map { rp => rp -> x.stringOpt(rp.toString()) }
    val b = a.filter( x => x._2.isDefined ).map ( x => (x._1 -> x._2.get )).toMap
    
    ReportEventElem(x.int("id"), ReportEvent( x.string("host"), 
          x.string("vm"),
          x.string("src_ip"), x.string("dst_ip"), 
          x.int("src_port"), x.int("dst_port"), x.string("traffic_type"),  
          new DateTime(x.timestamp("ts_reported")),
          b ))
  }
  
  def notSpuriousVms(status:Int, start:DateTime, end:DateTime) = DB readOnly { implicit session =>
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
                           and vm not in (select vm from spurious_vms) 
      """.map { mapReportEventElem }
      stmt.list.apply()
  }
  
  def byVm(status:Int, start:DateTime, end:DateTime, vm:String) = DB readOnly { implicit session =>
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
                           and vm = ${vm} """.map { mapReportEventElem }
      stmt.list.apply()    
  }

  def byVm(status:Int, start:DateTime, end:DateTime, vm:String, trafficType:String) = DB readOnly { implicit session =>
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
                           and vm = ${vm} and traffic_type = ${trafficType}""".map { mapReportEventElem }
      stmt.list.apply()    
  }  
   
  def allVms(status:Int, start:DateTime, end:DateTime) = DB readOnly { implicit session =>       
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
      """.map { mapReportEventElem }
    stmt.list().apply()
  }
  
  def singleVm(status:Int, start:DateTime, end:DateTime, vm:String) = DB readOnly { implicit session =>
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result
            from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
                           and vm = ${vm} """.map { mapReportEventElem }
    stmt.list.apply()                         
  }
  
  
  def trafficType(status:Int, start:DateTime, end:DateTime, trafficType:String) = DB readOnly { implicit session =>
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, traffic_type, ts_reported, alien_vault_result 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
                and traffic_type = ${trafficType} """.map { mapReportEventElem }
    stmt.list().apply()    
  }
  
  
  def saveReport(event:ReportEvent) = DB localTx { implicit session => 
    val stmt = sql"""insert into log_report (host, vm, src_ip, dst_ip, src_port, dst_port, traffic_type, traffic_type_id, ts_reported, status, alien_vault_result) 
          values (${event.host}, ${event.vm} , ${event.srcIp}, ${event.dstIp}, ${event.srcPort}, ${event.dstPort}, 
                   ${event.trafficType.desc}, ${event.trafficType.code.id}, ${new Timestamp(  event.timestamp.getMillis )}, 0, 
                     ${event.reputation.get(ReputationProcessors.AlienVault)}  )"""
    stmt.update().apply           
  }
  
  def listAllowed() = DB localTx { implicit session =>
    val stmt = sql"""select dst, dst_type from allowed_dest""".map { x => Allowed( x.string("dst_type"), x.string("dst") )}
    stmt.list.apply()
  }
  
  def getAllowed(id:Int) = DB localTx { implicit session => 
    val stmt = sql"""select dst, dst_type from allowed_dest where id = ${id} """.map { x => Allowed( x.string("dst_type"), x.string("dst") )}
    stmt.list().apply().head
  }
  
  def insertSpuriousVm(vm:String) = DB localTx { implicit session => 
    val stmt = sql"""insert into spurious_vms (vm) values ( ${vm} )"""
    stmt.update.apply()
  }
  

  
}