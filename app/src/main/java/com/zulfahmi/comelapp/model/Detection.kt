package com.zulfahmi.comelapp.model

data class Detection(
    var userId: String = "",
    var date: String = "",
    var location: String = "",
    var counter: Int = 0,
    var responded: Boolean = false
)