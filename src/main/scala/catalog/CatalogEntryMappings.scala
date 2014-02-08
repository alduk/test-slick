package catalog

import com.typesafe.slick.driver.db2.DB2Driver.simple._

object CatalogEntryMappings {
  class CatalogEntries(tag: Tag) extends Table[(Int, String, String, Option[String], Option[String], Option[Int])](tag, "CATENTRY") {
    def id = column[Int]("CATENTRY_ID", O.PrimaryKey)
    def catentryType = column[String]("CATENTTYPE_ID")
    def partNumber = column[String]("PARTNUMBER")
    def manufacturerPartNumber = column[Option[String]]("MFPARTNUMBER")
    def manufacturerName = column[Option[String]]("MFNAME")
    def baseItemId = column[Option[Int]]("BASEITEM_ID")
    def * = (id, catentryType, partNumber, manufacturerPartNumber, manufacturerName, baseItemId)
  }

  val CatalogEntries = TableQuery[CatalogEntries]
}