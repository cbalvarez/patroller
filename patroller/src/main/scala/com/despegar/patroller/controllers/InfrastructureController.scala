package com.despegar.patroller.controllers

import org.scalatra.LifeCycle
import org.scalatra.ScalatraServlet

class InfrastructureController extends ScalatraServlet  {
  get("/health-check") {
    "space    "
  }
}