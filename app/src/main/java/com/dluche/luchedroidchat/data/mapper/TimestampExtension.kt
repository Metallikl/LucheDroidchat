package com.dluche.luchedroidchat.data.mapper

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toTimestamp(): String {
    val messageDate = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val now = LocalDateTime.now()
    return if(messageDate.toLocalDate() == now.toLocalDate()){
        messageDate.format(DateTimeFormatter.ofPattern("HH:mm"))
    } else{
        messageDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}