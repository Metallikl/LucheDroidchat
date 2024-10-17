package com.dluche.luchedroidchat.ui.validator

interface FormValidator<FormState> {
    fun validate(formState: FormState): FormState
}