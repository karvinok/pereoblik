package com.vilinesoft.ui.model

data class IdName(val id: String, val name: String) {
    override fun toString(): String {
        return name
    }
}

data class IntIdName(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}