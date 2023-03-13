package com.example.storiesapp.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation

    (private val context: Context) {

    fun isInputEditTextFilled(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }

    fun isInputEditTextFilledSignupName(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            Toast.makeText(context, "Enter name", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }

        return true
    }

    fun isInputEditTextFilledAuthor(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            Toast.makeText(context, "Enter author name", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }

        return true
    }

    fun isInputEditTextFilledTitle(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            Toast.makeText(context, "Enter title", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }

        return true
    }

    fun isInputEditTextFilledStory(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            Toast.makeText(context, "Enter story", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }

        return true
    }

    fun isInputEditTextFilledSignupPassword(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            Toast.makeText(context, "Enter password", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }

        return true
    }

    fun isInputEditTextFilledSignupPhone(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.length !=10) {

            Toast.makeText(context, "Phone number not valid!", Toast.LENGTH_LONG).show()
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(value).matches();
        }

        return true
    }

    fun isInputEditTextEmail(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }

    fun isInputEditTextEmailSignup(textInputEditText: TextInputEditText, context: Context): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            Toast.makeText(context, "Enter Valid Email Address", Toast.LENGTH_LONG).show()
            hideKeyboardFrom(textInputEditText)
            return false
        } else {

        }
        return true
    }

    private fun hideKeyboardFrom(view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}