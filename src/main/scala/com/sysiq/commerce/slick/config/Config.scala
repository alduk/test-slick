package com.sysiq.commerce.slick.config

import com.typesafe.slick.driver.db2.DB2Driver.simple._

object Config {
  val driver = com.typesafe.slick.driver.db2.DB2Driver.simple
  val db = Database.forURL("jdbc:db2://wsc-dev-db-01.kiev.ecofabric.com:50001/SAASFEP7", driver = "COM.ibm.db2.jdbc.app.DB2Driver", user = "db2inst1", password = "passw0rd")
}
