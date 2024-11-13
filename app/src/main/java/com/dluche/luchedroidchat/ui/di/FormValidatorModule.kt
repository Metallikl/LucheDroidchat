package com.dluche.luchedroidchat.ui.di

import com.dluche.luchedroidchat.ui.feature.signup.SignUpFormState
import com.dluche.luchedroidchat.ui.feature.signup.SignUpFormValidator
import com.dluche.luchedroidchat.ui.validator.FormValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FormValidatorModule {

    @Binds
    fun bindsSignUpFormValidator(validator: SignUpFormValidator): FormValidator<SignUpFormState>

}