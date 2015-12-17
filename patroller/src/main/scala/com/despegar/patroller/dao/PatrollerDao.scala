package com.despegar.patroller.dao

import scalikejdbc._
import com.despegar.sbt.madonna.MadonnaConf
import org.joda.time.DateTime
import java.sql.Timestamp


case class ReportEvent(val host:String, val vm:String, val srcIp:String, val dstIp:String, val srcPort:Int, val dstPort:Int, val timestamp:DateTime)
case class ReportEventElem(id:Int, event:ReportEvent)
case class Allowed(typeAllowed:String, allowed:String )

object PatrollerDao {
  Class.forName("com.mysql.jdbc.Driver")
  val dbConfig = MadonnaConf.config.getConfig("db")
  val url = dbConfig.getString("url")
  val user = dbConfig.getString("user")
  val passwd = dbConfig.getString("passwd")
  
  ConnectionPool.singleton(url, user, passwd)  
  
  def transactional[T](f: (DBSession) => T) = DB.localTx { f } 

  def insertAllowed(idReport:Option[Int], reason:String, filterValue:String, filterType:String )(implicit session:DBSession) = {
    val stmt = sql"""insert into allowed_dest (dst, dst_type, reason, report_id) values 
      (  ${filterValue}, ${filterType}, ${reason}, ${idReport} )"""
    stmt.update.apply()
  }
  
  def updateStatus(id:Int, status:Int, allowedRuleId:Option[Int])(implicit session:DBSession) = {
    val stmt = sql"""update log_report set status = ${status}, allowed_rule_id = ${allowedRuleId} where id = ${id}"""
    stmt.update().apply
  }
  
  def listReports(status:Int, start:DateTime, end:DateTime) = DB readOnly { implicit session => 
    val stmt = sql"""select id, host, src_ip, dst_ip, vm, src_port, dst_port, ts_reported 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
      """.map { x => ReportEventElem(x.int("id"), ReportEvent( x.string("host"), 
          x.string("vm"),
          x.string("src_ip"), x.string("dst_ip"), 
           x.int("src_port"), x.int("dst_port"), new DateTime(x.timestamp("ts_reported") )))  }
    stmt.list().apply()
  }
  
  def saveReport(event:ReportEvent) = DB localTx { implicit session => 
    val stmt = sql"""insert into log_report (host, vm, src_ip, dst_ip, src_port, dst_port, ts_reported, status) 
          values (${event.host}, ${event.vm} , ${event.srcIp}, ${event.dstIp}, ${event.srcPort}, ${event.dstPort}, 
               ${new Timestamp(  event.timestamp.getMillis )}, 0  )"""
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
  

  
}