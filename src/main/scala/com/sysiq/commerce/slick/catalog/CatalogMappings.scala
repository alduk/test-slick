package com.sysiq.commerce.slick.catalog

import com.sysiq.commerce.slick.config.Config.driver._

object CatalogMappings {
  case class Catalog(id: Long, memberId: Long, identifier: String, description: Option[String])
  class Catalogs(tag: Tag) extends Table[Catalog](tag, "CATALOG") {
    def id = column[Long]("CATALOG_ID", O.PrimaryKey)
    def memberId = column[Long]("MEMBER_ID")
    def identifier = column[String]("IDENTIFIER")
    def description = column[String]("DESCRIPTION")
    def * = (id, memberId, identifier, description.?) <> (Catalog.tupled, Catalog.unapply)
  }

  val Catalogs = TableQuery[Catalogs]

  case class CatalogGroup(id: Long, memberId: Long, identifier: String, markForDelete: Int)
  class CatalogGroups(tag: Tag) extends Table[CatalogGroup](tag, "CATGROUP") {
    def id = column[Long]("CATGROUP_ID", O.PrimaryKey)
    def memberId = column[Long]("MEMBER_ID")
    def identifier = column[String]("IDENTIFIER")
    def markForDelete = column[Int]("MARKFORDELETE")
    def * = (id, memberId, identifier, markForDelete) <> (CatalogGroup.tupled, CatalogGroup.unapply)
  }

  val CatalogGroups = TableQuery[CatalogGroups]

  case class CatalogToGroupRelation(catalogId: Long, groupId: Long)
  class CatalogToGroupRelations(tag: Tag) extends Table[CatalogToGroupRelation](tag, "CATGROUP") {
    def catalogId = column[Long]("CATALOG_ID")
    def groupId = column[Long]("CATGROUP_ID")
    def * = (catalogId, groupId) <> (CatalogToGroupRelation.tupled, CatalogToGroupRelation.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (catalogId, groupId))
  }

  val CatalogToGroupRelations = TableQuery[CatalogToGroupRelations]
}