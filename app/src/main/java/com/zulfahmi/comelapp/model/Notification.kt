package com.zulfahmi.comelapp.model

data class Notification(
    var targetUID: String,
    var date: String,
    var message: String,
    var type: Int
)