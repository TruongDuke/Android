package com.example.gmaillist   // sửa cho đúng với package bạn thấy ở trên

data class Email(
    val sender: String,
    val subject: String,
    val snippet: String,
    val time: String,
    val starred: Boolean = false
)