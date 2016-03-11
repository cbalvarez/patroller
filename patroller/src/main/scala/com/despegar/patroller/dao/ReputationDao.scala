package com.despegar.patroller.dao

import scalikejdbc._
import org.joda.time.DateTime
import java.sql.Timestamp

case class AlienVaultReputationEntry(val ip:String, val desc:String, val country:String)


object ReputationDao  {
  
  def mapAlienVaultReputationEntry(x:WrappedResultSet) = {
    AlienVaultReputationEntry(x.string("ip"), x.string("description"), x.string("country"))
  }
  
  def saveAlienVaultReputation(ip:String, desc:String, country:String) = DB localTx { implicit session =>
    val stmt = sql"""insert into alien_vault_black(ip, description, country) values 
                        (${ip}, ${desc}, ${country})"""
    stmt.update().apply()
  }
  
 def truncateAlienVaultReputation() = DB localTx { implicit session =>
    val stmt = sql"""truncate table alien_vault_black """
    stmt.update().apply()
 }
 
 def loadAlienVaultReputation() = DB readOnly { implicit session =>
   val stmt = sql"""select ip, description, country from alien_vault_black""".map { mapAlienVaultReputationEntry(_) }
   stmt.list.apply()    
 }
}  
