package com.despegar.patroller.services

import org.joda.time.DateTime
import com.despegar.patroller.dao.PatrollerDao
import com.despegar.patroller.dao.ReportEvent
import com.despegar.patroller.dao.ReportEventElem
import com.typesafe.scalalogging.slf4j.StrictLogging
import com.despegar.patroller.dao.Allowed
import org.apache.commons.net.util.SubnetUtils
import com.despegar.patroller.dao._
import scala.collection.mutable.MapBuilder


object Report extends StrictLogging {
  def reportUnknownDestEvent(r:ReportEvent) = {
    logger.debug(s"about to insert ${r.toString()}")
    val reports = scala.collection.mutable.HashMap.empty[ReputationProcessors.Value,String]
    AlienVaultReputationProcessor.checkIp(r.dstIp).map ( x => reports.put( ReputationProcessors.AlienVault , x.desc)) 
    logger.debug(s"about to report ${r} - reputation ${reports.toMap}")
    PatrollerDao.saveReport(ReportEvent(r, reports.toMap))
  }
  
  def addAllowed(reason:String, filterValue:String, filterType:String) = com.despegar.patroller.dao.transactional {   implicit session =>
    PatrollerDao.insertAllowed(None, reason, filterValue, filterType)
  }
  
  def buildFunctionCheckRule(allowedRule:Allowed) = {
    if (allowedRule.typeAllowed == "net") {
      logger.debug(s"rule ${allowedRule} is net")
      (re:ReportEventElem) => { 
        val r = (new SubnetUtils(allowedRule.allowed)).getInfo.isInRange(re.event.dstIp)
        logger.debug(s"ip ${re.event.dstIp} against ${allowedRule.allowed} is ${r}")
        r
      }
    } else {
      logger.debug(s"rule ${allowedRule} is host")
      (re:ReportEventElem) => { re.event.dstIp == allowedRule.allowed  }
    }
  }
  
  def isIncludedNet(re:ReportEventElem, allowedRule:Allowed) = {
    val addr = new SubnetUtils(allowedRule.allowed)
    addr.getInfo.isInRange(re.event.dstIp)
  }
  
  def testExplainBulk(idExplain:Int,start:DateTime, end:DateTime ) = com.despegar.patroller.dao.transactional {   implicit session =>
    val reports = PatrollerDao.allVms(0, start, end )
    val allowedFunction = buildFunctionCheckRule( PatrollerDao.getAllowed(idExplain) )
    reports.filter( x => allowedFunction(x))
  }
  
  def explainBulk(idExplain:Int,start:DateTime, end:DateTime ) = com.despegar.patroller.dao.transactional {   implicit session =>
    val reportsToExplain = testExplainBulk(idExplain, start, end)
    for ( r <- reportsToExplain) { PatrollerDao.updateStatus(r.id, 1, Some(idExplain))}
    reportsToExplain
  }
  
  def addSpuriousVm(vm:String) = com.despegar.patroller.dao.transactional { implicit session => 
    PatrollerDao.insertSpuriousVm(vm)
  }

}