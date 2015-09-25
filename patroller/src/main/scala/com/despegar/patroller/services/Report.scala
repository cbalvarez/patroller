package com.despegar.patroller.services

import org.joda.time.DateTime
import com.despegar.patroller.dao.PatrollerDao
import com.despegar.patroller.dao.ReportEvent
import com.despegar.patroller.dao.ReportEventElem
import com.typesafe.scalalogging.slf4j.StrictLogging


object Report extends StrictLogging {
  def reportUnknownDestEvent(r:ReportEvent) = {
    logger.debug(s"about to insert ${r.toString()}")
    PatrollerDao.saveReport(r)
  }
  
  def listCurrentAlerts(start:DateTime, end:DateTime) = {
    PatrollerDao.listReports(0, start, end)
  }
  
  def explain(id:Int, reason:String, filterValue:String, filterType:String) = PatrollerDao.transactional { implicit session => 
    PatrollerDao.updateStatus(id, 1)
    PatrollerDao.insertAllowed(Some(id), reason, filterValue, filterType)
  }
  
  def addAllowed(reason:String, filterValue:String, filterType:String) = PatrollerDao.transactional {   implicit session =>
    PatrollerDao.insertAllowed(None, reason, filterValue, filterType)
  }
}