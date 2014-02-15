package com.sysiq.commerce.slick.member

import com.sysiq.commerce.slick.config.Config.driver._

/*
 * Mappings for access policies
 * http://pic.dhe.ibm.com/infocenter/wchelp/v7r0m0/index.jsp?topic=%2Fcom.ibm.commerce.data.doc%2Frefs%2Frdmmember.htm
 */
object MemberMappings {

  case class User(id: Long, distinguishedName: Option[String], registrationType: String, profileType: Option[String], languageId: Option[Int], field1: Option[String], field2: Option[String], field3: Option[String], currency: Option[String])
  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Long]("USERS_ID", O.PrimaryKey)
    def distinguishedName = column[Option[String]]("DN")
    def registrationType = column[String]("REGISTERTYPE")
    def profileType = column[Option[String]]("PROFILETYPE")
    def languageId = column[Option[Int]]("LANGUAGE_ID")
    def field1 = column[Option[String]]("FIELD1")
    def field2 = column[Option[String]]("FIELD2")
    def field3 = column[Option[String]]("FIELD3")
    def currency = column[Option[String]]("SETCCURR")
    def * = (id, distinguishedName, registrationType, profileType, languageId, field1, field2, field3, currency) <> (User.tupled, User.unapply)
  }

  val Users = TableQuery[Users]
}