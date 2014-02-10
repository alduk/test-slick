package catalog

import config.Config.driver._

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
}