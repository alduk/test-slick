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
    def baseItem2 = BaseItems.filter(_.id === baseItemId)
    def listPrice = foreignKey("F_419", id, ListPrices)(_.catentryId)
    def listPrice2 = ListPrices.filter(_.catentryId === id)
    def * = (id, catentryType, partNumber, manufacturerPartNumber, manufacturerName, baseItemId) <> (CatalogEntry.tupled, CatalogEntry.unapply)
  }
  
  val CatalogEntries = TableQuery[CatalogEntries]
  
  case class ListPrice(catentryId:Int,currency:String,price:Double)
  class ListPrices(tag:Tag)extends Table[ListPrice](tag,"LISTPRICE"){
    def catentryId = column[Int]("CATENTRY_ID")
    def currency = column[String]("CURRENCY")
    def price = column[Double]("LISTPRICE")
    def * = (catentryId,currency,price) <>(ListPrice.tupled, ListPrice.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (catentryId, currency))
  }
  
  val ListPrices = TableQuery[ListPrices]
}