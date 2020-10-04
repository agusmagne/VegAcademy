package com.vegdev.vegacademy.helpers.enums

enum class Enums {

}

enum class OrgMemberRolesEnum(val role: String) {
    FOUNDER("founding_member"),
    COORDINATOR("project_coordinator"),
    ADMINISTRATOR("member_administrator"),
    ACTIVIST("activist"),
    OTHER("other");

    companion object {
        fun getAllRoles(): MutableList<String> {
            return mutableListOf(FOUNDER.role, COORDINATOR.role, ADMINISTRATOR.role, ACTIVIST.role, OTHER.role)
        }
    }
}