package com.despegar.patroller.dao

import scalikejdbc._
import com.despegar.sbt.madonna.MadonnaConf
import org.joda.time.DateTime


case class ReportEvent(val host:String, val vm:String, val ip:String, val localPort:Int, val remotePort:Int, val timestamp:DateTime)
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
  
  def updateStatus(id:Int, status:Int)(implicit session:DBSession) = {
    val stmt = sql"""update log_report set status = ${status} where id = ${id}"""
    stmt.update().apply
  }
  
  def listReports(status:Int, start:DateTime, end:DateTime) = DB readOnly { implicit session => 
    val stmt = sql"""select id, host, ip, vm, local_port, remote_port, ts_reported 
      from log_report where status = ${status}  and ts_reported >= ${start} and ts_reported <= ${end}
      """.map { x => ReportEventElem(x.int("id"), ReportEvent( x.string("host"), x.string("ip"), 
          x.string("vm"), x.int("local_port"), x.int("remote_port"), new DateTime(x.timestamp("ts_reported") )))  }
    stmt.list().apply()
  }
  
  def saveReport(event:ReportEvent) = DB localTx { implicit session => 
    val stmt = sql"""insert into log_report (host, vm, ip, remote_port, local_port, ts_reported, status) 
          values (${event.host}, ${event.vm} , ${event.ip}, ${event.remotePort}, ${event.localPort}, ${event.timestamp}, 0  )"""
    stmt.update().apply           
  }
  
  def listAllowed() = DB localTx { implicit session =>
    val stmt = sql"""select dst, dst_type from allowed_dest""".map { x => Allowed( x.string("dst_type"), x.string("dst") )}
    stmt.list.apply()
  }
  

  
}