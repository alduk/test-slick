package commands

import config.Config.driver._

object CommandMapings {
  // Definition of the CmdReg table
  case class Command(storeEntId: Int, interfaceName: String, className: String)
  class CommandRegistry(tag: Tag) extends Table[Command](tag, "CMDREG") {
    def storeEntId = column[Int]("STOREENT_ID")
    def interfaceName = column[String]("INTERFACENAME")
    def className = column[String]("CLASSNAME")
    def pk = primaryKey("SQL140117144819710", (storeEntId, interfaceName))
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (storeEntId, interfaceName, className) <> (Command.tupled, Command.unapply)
  }
  val CommandRegistry = TableQuery[CommandRegistry]
}