package com.despegar.patroller.services

import com.despegar.patroller.dao.PatrollerDao
import scala.collection.mutable.HashMap
import scala.util.Random
import org.joda.time.DateTime

case class Trace(shouldTrace:Boolean, tcpdumpParams:String, timeToTraceInMinutes:Int  )

object PatrollSettings {
  val timeToTraceInMinutes = 10
  val percentageToTrace = 40
  val random = new Random
  
  def shouldTrace(host:String) = {
    Random.nextInt(100) < percentageToTrace
  }
  
  
  def currentTcpdump() = {   
    "(not src net 192.168.0.0/16) and " + PatrollerDao.listAllowed().map ( x => s"(not dst ${x.typeAllowed} ${x.allowed}) "  ).mkString(" and ") 
  }
   
  def trace(host:String) = {
    val st = Trace(shouldTrace(host), currentTcpdump, timeToTraceInMinutes)   
    PatrollerDao.insertTraceQuestion(host, DateTime.now(), st.shouldTrace.toString())
    st
  }
}