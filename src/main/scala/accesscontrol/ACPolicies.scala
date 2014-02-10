package accesscontrol

import config.Config.driver._

/*
 * Mappings for access policies
 * http://www.ibm.com/developerworks/websphere/library/techarticles/0805_callaghan/0805_callaghan.html
 */
object ACPoliciyMappings {

  case class ACPolicy(id: Int, policyName: String, resourseGroupId: Int, actionGroupId: Int)
  class ACPolicies(tag: Tag) extends Table[ACPolicy](tag, "ACPOLICY") {
    def id = column[Int]("ACPOLICY_ID", O.PrimaryKey)
    def policyName = column[String]("POLICYNAME")
    def resourseGroupId = column[Int]("ACRESGRP_ID")
    def actionGroupId = column[Int]("ACACTGRP_ID")
    def * = (id, policyName, resourseGroupId, actionGroupId) <> (ACPolicy.tupled, ACPolicy.unapply)
  }

  val ACPolicies = TableQuery[ACPolicies]

  case class ACAction(id: Int, action: String)
  class ACActions(tag: Tag) extends Table[ACAction](tag, "ACACTION") {
    def id = column[Int]("ACACTION_ID", O.PrimaryKey)
    def action = column[String]("ACTION")
    def * = (id, action) <> (ACAction.tupled, ACAction.unapply)
  }

  val ACActions = TableQuery[ACActions]

  class ACActionGroups(tag: Tag) extends Table[(Int, String)](tag, "ACACTGRP") {
    def id = column[Int]("ACACTGRP_ID", O.PrimaryKey)
    def groupName = column[String]("GROUPNAME")
    def * = (id, groupName)
  }

  val ACActionGroups = TableQuery[ACActionGroups]

  class ACActionGroupsRel(tag: Tag) extends Table[(Int, Int)](tag, "ACACTACTGP") {
    def actionGroupId = column[Int]("ACACTGRP_ID")
    def actionId = column[Int]("ACACTION_ID")
    def * = (actionGroupId, actionId)
    def pk = primaryKey("<SYSTEM-GENERATED>", (actionGroupId, actionId))
  }

  val ACActionGroupsRel = TableQuery[ACActionGroupsRel]

  case class ACResourceCategory(id: Int, resourceClassName: String)
  class ACResourceCategories(tag: Tag) extends Table[ACResourceCategory](tag, "ACRESCGRY") {
    def id = column[Int]("ACRESCGRY_ID", O.PrimaryKey)
    def resourceClassName = column[String]("RESCLASSNAME")
    def * = (id, resourceClassName) <> (ACResourceCategory.tupled, ACResourceCategory.unapply)
  }
  val ACResourceCategories = TableQuery[ACResourceCategories]

  class ACResourceGroups(tag: Tag) extends Table[(Int, String, String)](tag, "ACRESGRP") {
    def id = column[Int]("ACRESGRP_ID", O.PrimaryKey)
    def groupName = column[String]("GRPNAME")
    def description = column[String]("DESCRIPTION")
    def * = (id, groupName, description)
  }

  val ACResourceGroups = TableQuery[ACResourceGroups]

  class ACResourceGroupsRel(tag: Tag) extends Table[(Int, Int)](tag, "ACRESGPRES") {
    def resourceGroupId = column[Int]("ACRESGRP_ID")
    def resourceCategoryId = column[Int]("ACRESCGRY_ID")
    def * = (resourceGroupId, resourceCategoryId)
    def pk = primaryKey("SQL110329030721310", (resourceGroupId, resourceCategoryId))
  }

  val ACResourceGroupsRel = TableQuery[ACResourceGroupsRel]
}