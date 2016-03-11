package com.despegar.patroller.services

import com.despegar.patroller.dao.ReputationDao
import com.typesafe.scalalogging.slf4j.StrictLogging
import com.despegar.patroller.dao.AlienVaultReputationEntry


object AlienVaultReputationProcessor extends StrictLogging {
  
  def loadData() =  {
    ReputationDao.loadAlienVaultReputation().map ( x => ( x.ip -> x  ) ).toMap
  }
  
  val reputationData = loadData()
  
  def checkIp(ip:String) = {
    reputationData.get(ip)
  }
  
  def processLine(line:String) = {
    logger.debug(s"about to processs ${line}")
    val r = """^((([0-9]{1,3}\.){3})[0-9]{1,3}) # ([^,]+)([A-Z]{2}).+""".r
    line match {
      case r(ip,pad1,pad2,desc,country) => Some(AlienVaultReputationEntry(ip, desc, country))
      case _ => None
    }
  }
  
  def generateDataToInsert(contentFile:String) = 
    contentFile.split("\n").map (processLine (_))
  
  def processFile(contentFile:String) = com.despegar.patroller.dao.transactional { implicit session => 
    ReputationDao.truncateAlienVaultReputation()
    generateDataToInsert(contentFile).map ( _.map (x => ReputationDao.saveAlienVaultReputation(x.ip, x.desc, x.country)) )
  }
  
}