package com.codeturtle.cafe.utils

import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


object Validation {

    fun isEmptyOrNot(
        text: String,
        textField:TextInputLayout
    ): Boolean {
        var valid = false
        if(text.isEmpty()){
            textField.helperText = "This field cannot be empty"
        }else{
            textField.helperText = ""
            valid = true
        }
        return valid
    }

    fun isValidEmail(
        email: String,
        textField: TextInputLayout
    ) : Boolean {
        var valid = false
        val emailRegex = "^(.+)@(.+)$"
        val pattern: Pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        if(email.isEmpty()){
            textField.helperText = "This field cannot be empty"
        }else if(!matcher.matches()){
            textField.helperText = "Enter valid email"
        }else{
            textField.helperText = ""
            valid = true
        }
        return valid
    }

    fun isPasswordSame(
        password:String,
        confirmPassword:String,
        textFieldPassword: TextInputLayout,
        textFieldConfirmPassword: TextInputLayout
    ): Boolean{
        var valid = false
        if(password.isEmpty()){
            textFieldPassword.helperText = "This field cannot be empty"
        }else if(confirmPassword.isEmpty()){
            textFieldConfirmPassword.helperText = "This field cannot be empty"
        }else if(password != confirmPassword){
            textFieldConfirmPassword.helperText = "password are not same"
        }else{
            textFieldPassword.helperText = ""
            textFieldConfirmPassword.helperText = ""
            valid = true
        }
        return valid
    }
}