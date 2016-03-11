package com.despegar.patroller

import scalikejdbc._
import com.despegar.sbt.madonna.MadonnaConf



package object dao {
  
  def apply() { 
  }
  def transactional[T](f: (DBSession) => T) = DB.localTx { f } 

}