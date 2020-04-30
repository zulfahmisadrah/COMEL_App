package com.zulfahmi.comelapp.model

data class User(
    var userId: String? = "",
    var role: String = "",
    var username: String = "",
    var name: String? = "",
    var email: String = "",
    var address: String? = "",
    var phonenumber: String? = ""
)