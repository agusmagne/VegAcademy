package com.vegdev.vegacademy.helpers.enums

enum class Enums {

}

enum class MemberRolesEnum(val value: String, val position: Int) {
    FOUNDER("founding_member", 0),
    COORDINATOR("project_coordinator", 1),
    ADMINISTRATOR("member_administrator", 2),
    ACTIVIST("activist", 3),
    OTHER("other", 4);

    companion object {
        fun getAllRoles(): MutableList<MemberRolesEnum> {
            return mutableListOf(FOUNDER, COORDINATOR, ADMINISTRATOR, ACTIVIST, OTHER)
        }

        fun getRoleFromValue(value: String?): MemberRolesEnum {
            val roles = getAllRoles()
            val role: List<MemberRolesEnum> = roles.filter { it.value == value }
            return if (role.isEmpty()) {
                OTHER
            } else {
                role.first()
            }
        }

        fun getRoleFromPosition(position: Int): MemberRolesEnum {
            val roles = getAllRoles()
            return roles.first { it.position == position }
        }
    }
}