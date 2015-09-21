package com.despegar.patroller.services

import org.joda.time.DateTime
import com.despegar.patroller.dao.PatrollerDao
import com.despegar.patroller.dao.ReportEvent
import com.despegar.patroller.dao.ReportEventElem


object Report {
  def reportUnknownDestEvent(r:ReportEvent) = {
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