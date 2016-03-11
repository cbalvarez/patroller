package com.despegar.patroller.controllers

import org.scalatra.ScalatraServlet
import com.typesafe.scalalogging.slf4j.StrictLogging
import org.scalatra.servlet.FileUploadSupport
import org.scalatra.servlet.MultipartConfig
import com.despegar.patroller.services.AlienVaultReputationProcessor

class ReputationFilesUploader extends ScalatraServlet with FileUploadSupport with StrictLogging {
  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3*1024*1024)))


  get("/aa") {
    "aaaaaaa"
  }
  
  post("/alienvault") {
    val item = fileParams("datafile")
    val is = scala.io.Source.fromInputStream(item.getInputStream).getLines().mkString("\n")
    AlienVaultReputationProcessor.processFile(is)
  }
}