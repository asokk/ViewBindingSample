package com.github.ramonrabello.viewbinding.model

data class User(val name: String, val surname: String){
    override fun toString(): String {
        return "$name $surname"
    }
}