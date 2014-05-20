package com.sysiq.commerce.slick.pagelayouts

import scala.slick.jdbc._
import com.sysiq.commerce.slick.config.Config.driver._

object PageLayoutsMappings {
  case class PageLayout(id: Long, name: String, viewName: Option[String], storeEntId: Long)
  class PageLayouts(tag: Tag) extends Table[PageLayout](tag, "PAGELAYOUT") {
    def id = column[Long]("PAGELAYOUT_ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def viewName = column[Option[String]]("VIEWNAME")
    def storeEntId = column[Long]("STOREENT_ID")
    def * = (id, name, viewName, storeEntId) <> (PageLayout.tupled, PageLayout.unapply)
  }

  val PageLayouts = TableQuery[PageLayouts]

  case class Widget(id: Long, layoutId: Long, defId: Long, adminName: String)
  class Widgets(tag: Tag) extends Table[Widget](tag, "PLWIDGET") {
    def id = column[Long]("PLWIDGET_ID", O.PrimaryKey)
    def layoutId = column[Long]("PAGELAYOUT_ID")
    def defId = column[Long]("PLWIDGETDEF_ID")
    def adminName = column[String]("ADMINNAME")
    def * = (id, layoutId, defId, adminName) <> (Widget.tupled, Widget.unapply)
  }

  val Widgets = TableQuery[Widgets]
}