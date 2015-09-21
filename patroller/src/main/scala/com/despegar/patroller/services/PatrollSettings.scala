package com.despegar.patroller.services

import com.despegar.patroller.dao.PatrollerDao

case class Trace(shouldTrace:Boolean, tcpdumpParams:String  )

object PatrollSettings {
  def currentTcpdump() = {
    PatrollerDao.listAllowed().map ( x => s"(not dst ${x.typeAllowed} ${x.allowed}) "  ).mkString(" and ") 
  }
  
  def trace(server:String) = {
      Trace(true, currentTcpdump)    
  }
}