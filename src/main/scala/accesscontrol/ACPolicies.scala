package accesscontrol

import com.typesafe.slick.driver.db2.DB2Driver.simple._

/*
 * Mappings for access policies
 * http://www.ibm.com/developerworks/websphere/library/techarticles/0805_callaghan/0805_callaghan.html
 */
object ACPoliciyMappings {

  class ACPolicies(tag: Tag) extends Table[(Int, String, Int,Int)](tag, "ACPOLICY") {
    def id = column[Int]("ACPOLICY_ID", O.PrimaryKey)
    def policyName = column[String]("POLICYNAME")
    def resourseGroupId = column[Int]("ACRESGRP_ID")
    def actionGroupId = column[Int]("ACACTGRP_ID")
    def * = (id, policyName, resourseGroupId,actionGroupId)
  }

  val ACPolicies = TableQuery[ACPolicies]

  class ACActions(tag: Tag) extends Table[(Int, String)](tag, "ACACTION") {
    def id = column[Int]("ACACTION_ID", O.PrimaryKey)
    def action = column[String]("ACTION")
    def * = (id, action)
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

  class ACResourceCategories(tag: Tag) extends Table[(Int, String)](tag, "ACRESCGRY") {
    def id = column[Int]("ACRESCGRY_ID", O.PrimaryKey)
    def resourceClassName = column[String]("RESCLASSNAME")
    def * = (id, resourceClassName)
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