package com.sysiq.commerce.slick.catalog

import com.sysiq.commerce.slick.config.Config.driver._
import java.sql.Date

object CatalogEntryMappings{
  
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
    def parent = for {
      r <- CatentryRelations.filter(_.childId === id)
      c <- CatalogEntries.filter(_.id === r.parentId)
    } yield (c)
    def children = for {
      r <- CatentryRelations.filter(_.parentId === id)
      c <- CatalogEntries.filter(_.id === r.childId)
    } yield (c)
    def offerPrices = for {
      o <- Offers if o.catentryId === id
      p <- OfferPrices if p.offerId === o.offerId
    } yield (o, p)
    def * = (id, catentryType, partNumber, manufacturerPartNumber, manufacturerName, baseItemId) <> (CatalogEntry.tupled, CatalogEntry.unapply)
  }

  val CatalogEntries = TableQuery[CatalogEntries]

  case class CatentryRelation(parentId: Int, childId: Int, relationType: String)
  class CatentryRelations(tag: Tag) extends Table[CatentryRelation](tag, "CATENTREL") {
    def parentId = column[Int]("CATENTRY_ID_PARENT")
    def childId = column[Int]("CATENTRY_ID_CHILD")
    def relationType = column[String]("CATRELTYPE_ID")
    def * = (parentId, childId, relationType) <> (CatentryRelation.tupled, CatentryRelation.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (relationType, parentId, childId))
  }

  val CatentryRelations = TableQuery[CatentryRelations]

  case class ListPrice(catentryId: Int, currency: String, price: Double)
  class ListPrices(tag: Tag) extends Table[ListPrice](tag, "LISTPRICE") {
    def catentryId = column[Int]("CATENTRY_ID")
    def currency = column[String]("CURRENCY")
    def price = column[Double]("LISTPRICE")
    def * = (catentryId, currency, price) <> (ListPrice.tupled, ListPrice.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (catentryId, currency))
  }

  val ListPrices = TableQuery[ListPrices]

  case class Offer(offerId: Int, catentryId: Int, published:Int, identifier:Long, minQty: Option[Double], maxQty: Option[Double], startDate: Option[Date], endDate: Option[Date])
  class Offers(tag: Tag) extends Table[Offer](tag, "OFFER") {
    def offerId = column[Int]("OFFER_ID", O.PrimaryKey)
    def catentryId = column[Int]("CATENTRY_ID")
    def published = column[Int]("PUBLISHED")
    def identifier = column[Long]("IDENTIFIER")
    def startDate = column[Date]("STARTDATE")
    def endDate = column[Date]("ENDDATE")
    def minQty = column[Double]("MINIMUMQUANTITY")
    def maxQty = column[Double]("MAXIMUMQUANTITY")
    def * = (offerId, catentryId, published, identifier, minQty.?, maxQty.?, startDate.?, endDate.?) <> (Offer.tupled, Offer.unapply)
  }

  val Offers = TableQuery[Offers]

  case class OfferPrice(offerId: Int, currency: String, price: Double)
  class OfferPrices(tag: Tag) extends Table[OfferPrice](tag, "OFFERPRICE") {
    def offerId = column[Int]("OFFER_ID")
    def currency = column[String]("CURRENCY")
    def price = column[Double]("PRICE")
    def * = (offerId, currency, price) <> (OfferPrice.tupled, OfferPrice.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (offerId, currency))
  }

  val OfferPrices = TableQuery[OfferPrices]
}