package com.sysiq.commerce.slick.examples

import com.sysiq.commerce.slick.config.Config._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.accesscontrol.ACPoliciyMappings._
import com.sysiq.commerce.slick.catalog.CatalogEntryMappings._
import com.sysiq.commerce.slick.commands.CommandMapings._

// Use the implicit threadLocalSession
//import scala.slick.jdbc.JdbcBackend.Database.dynamicSession

object Example extends App {

  case class Store(id: Int, directory: Option[String])

  // Definition of the SUPPLIERS table
  class Stores(tag: Tag) extends Table[(Int, String)](tag, "STORE") {
    def id = column[Int]("STORE_ID", O.PrimaryKey) // This is the primary key column
    def directory = column[Option[String]]("DIRECTORY")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, directory.getOrElse("NULL"))
  }
  val Stores = TableQuery[Stores]


  // Connect to the database and execute the following block within a session
  db withSession { implicit session =>
    // The session is never named explicitly. It is bound to the current
    // thread as the threadLocalSession that we imported

    // Iterate through all coffees and output them
    println("Stores:")
    Stores foreach {
      case (id, dir) =>
        println("  " + id + "\t" + dir)
    }

    // Why not let the database do the string conversion and concatenation?
    println("Stores (concatenated by DB):")
    val q1 = for (c <- Stores if c.id > 0) // Coffees lifted automatically to a Query
      yield (c.id, c.directory)
    // The first string constant needs to be lifted manually to a ConstColumn
    // so that the proper ++ operator is found
    println(q1.selectStatement)
    q1 foreach println

    //cmds foreach println
    val q2 = for (
      c <- CommandRegistry if c.className like "%sysiq%"
    ) yield (c)

    println(q2.selectStatement)
    //println(q2.countDistinct.value)

    val q3 = for {
      p <- ACPolicies
      rel <- ACResourceGroupsRel if rel.resourceGroupId === p.resourseGroupId
      a <- ACResourceCategories if a.id === rel.resourceCategoryId
      c <- CommandRegistry if (c.className.like("%.sysiq.%") || c.className.like("%.hm.%")) && a.resourceClassName === c.interfaceName
    } yield (p.policyName, c.interfaceName, c.className)

    println(q3.selectStatement)
    q3 foreach println

    val q4 = for {
      p <- ACPolicies
      rel <- ACActionGroupsRel if rel.actionGroupId === p.actionGroupId
      a <- ACActions if a.action.like("SI%") && a.id === rel.actionId
    } yield (p.policyName, a.action)

    println(q4.selectStatement)
    q4 foreach println

    val q5 = for {
      (e, b) <- CatalogEntries innerJoin BaseItems on (_.baseItemId === _.id)
    } yield (e, b)
    println(q5.selectStatement)
    q5 foreach println

    val q6 = for {
      e <- CatalogEntries
      b <- e.baseItem2
      p <- e.listPrice
    } yield (e, b, p.currency, p.price)
    println(q6.selectStatement)
    println(q6.list.length)
    q6 foreach println

    val v = CatalogEntries.list
    println(v.length)

    //CatalogEntries map { x => x.listPrice }
  }
}
