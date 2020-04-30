package com.zulfahmi.comelapp.model

data class Chat (
    var user: String? = null,
    var messageId: String? = null,
    var message: String? = null,
    var time: String? = null,
    var isSameUser: Boolean? = false
)