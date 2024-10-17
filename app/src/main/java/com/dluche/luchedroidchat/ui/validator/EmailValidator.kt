package com.dluche.luchedroidchat.ui.validator

object EmailValidator {
    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"

    fun isValid(value: String) : Boolean {
        return EMAIL_REGEX.toRegex().matches(value)
    }
}