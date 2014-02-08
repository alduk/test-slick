package catalog

import com.typesafe.slick.driver.db2.DB2Driver.simple._

object CatalogEntryMappings {

  case class BaseItem(id: Int, itemType: String, quantityMeasure: String, partNumber: String, quantityMultiple: Double)
  class BaseItems(tag: Tag) extends Table[BaseItem](tag, "BASEITEM") {
    def id = column[Int]("BASEITEM_ID", O.PrimaryKey)
    def itemType = column[String]("ITEMTYPE_ID")
    def quantityMeasure = column[String]("QUANTITYMEASURE")
    def partNumber = column[String]("PARTNUMBER")
    def quantityMultiple = column[Double]("QUANTITYMULTIPLE")
    def * = (id, itemType, quantityMeasure, partNumber, quantityMultiple) <> (BaseItem.tupled, BaseItem.unapply)
  }

  val BaseItems = TableQuery[BaseItems]

  case class CatalogEntry(id: Int, catentryType: String, partNumber: String, manufacturerPartNumber: Option[String], manufacturerName: Option[String], baseItemId: Option[Int])
  class CatalogEntries(tag: Tag) extends Table[CatalogEntry](tag, "CATENTRY") {
    def id = column[Int]("CATENTRY_ID", O.PrimaryKey)
    def catentryType = column[String]("CATENTTYPE_ID")
    def partNumber = column[String]("PARTNUMBER")
    def manufacturerPartNumber = column[Option[String]]("MFPARTNUMBER")
    def manufacturerName = column[Option[String]]("MFNAME")
    def baseItemId = column[Option[Int]]("BASEITEM_ID")
    def baseItem = foreignKey("F_202", baseItemId, BaseItems)(_.id)
    def * = (id, catentryType, partNumber, manufacturerPartNumber, manufacturerName, baseItemId) <> (CatalogEntry.tupled, CatalogEntry.unapply)
  }

  val CatalogEntries = TableQuery[CatalogEntries]
}