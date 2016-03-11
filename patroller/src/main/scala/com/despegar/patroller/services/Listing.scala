package com.despegar.patroller.services

import com.typesafe.scalalogging.slf4j.StrictLogging
import org.joda.time.DateTime
import com.despegar.patroller.dao.PatrollerDao
import com.despegar.patroller.dao.ReportEventElem
import java.lang.Boolean
import com.despegar.patroller.dao.ReportEventElem

object Listing extends StrictLogging {
  
  def filterVoid(ree:ReportEventElem) = {
    true
  }
  
  def filterBadReputation(ree:ReportEventElem) = {
    ree.event.reputation.size > 0
  }
  
  
  def filterPostProcess(d: () => List[ReportEventElem], f:(ReportEventElem) => Boolean) = {
    d().filter { x => f(x) }
  }
  
  def listCurrentAlerts(typeVms:String, start:DateTime, end:DateTime) = {
    typeVms match {
      case "ALL" => PatrollerDao.allVms(0, start, end)
      case "WATCHED" => PatrollerDao.notSpuriousVms(0, start, end)
    }
  }
  
  def listByVm(vm:String, start:DateTime, end:DateTime, trafficType:String) = {
    trafficType match {
      case "ALL" => PatrollerDao.byVm(0, start, end, vm)
      case _ => PatrollerDao.byVm(0, start, end, vm, trafficType)
    }
  }
  
  def listByTrafficType(trafficType:String, start:DateTime, end:DateTime) = {
      PatrollerDao.trafficType(0, start, end, trafficType)
  }

}