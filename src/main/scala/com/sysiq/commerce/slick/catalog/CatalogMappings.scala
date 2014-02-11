package com.sysiq.commerce.slick.catalog

import scala.slick.jdbc._
import com.sysiq.commerce.slick.config.Config.driver._
import CatalogEntryMappings._

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
    def children = for {
      g <- CatalogGroups if g.id === id
      r <- CatalogGroupToGroupRelations if r.parentId === g.id
      c <- CatalogGroups if r.childId === c.id
    } yield (c)

    def entries = for {
      g <- CatalogGroups if g.id === id
      r <- CatalogGroupToEntryRelations if r.groupId === g.id
      e <- CatalogEntries if r.entryId === e.id
    } yield (e)

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

  case class CatalogGroupToGroupRelation(parentId: Long, childId: Long, catalogId: Long)
  class CatalogGroupToGroupRelations(tag: Tag) extends Table[CatalogGroupToGroupRelation](tag, "CATGRPREL") {
    def parentId = column[Long]("CATGROUP_ID_PARENT")
    def childId = column[Long]("CATGROUP_ID_CHILD")
    def catalogId = column[Long]("CATALOG_ID")
    def * = (parentId, childId, catalogId) <> (CatalogGroupToGroupRelation.tupled, CatalogGroupToGroupRelation.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (parentId, childId, catalogId))
  }

  val CatalogGroupToGroupRelations = TableQuery[CatalogGroupToGroupRelations]

  case class CatalogGroupToEntryRelation(groupId: Long, catalogId: Long, entryId: Long)
  class CatalogGroupToEntryRelations(tag: Tag) extends Table[CatalogGroupToEntryRelation](tag, "CATGPENREL") {
    def groupId = column[Long]("CATGROUP_ID")
    def catalogId = column[Long]("CATALOG_ID")
    def entryId = column[Long]("CATENTRY_ID")
    def * = (groupId, catalogId, entryId) <> (CatalogGroupToEntryRelation.tupled, CatalogGroupToEntryRelation.unapply)
    def pk = primaryKey("<SYSTEM-GENERATED>", (groupId, catalogId, entryId))
  }

  val CatalogGroupToEntryRelations = TableQuery[CatalogGroupToEntryRelations]
}