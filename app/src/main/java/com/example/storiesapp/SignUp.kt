package com.example.storiesapp

import android.content.Intent
import android.net.sip.SipErrorCode.TIME_OUT
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storiesapp.database.DatabaseHelper
import com.example.storiesapp.database.User
import com.example.storiesapp.helpers.InputValidation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class SignUp : AppCompatActivity() {

    private lateinit var iv_signup_back : ImageView
    private lateinit var tv_login_signup: TextView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var signup : MaterialButton
    private lateinit var sv_signup : ScrollView

    private lateinit var et_signup_email : TextInputEditText
    private lateinit var et_signup_name : TextInputEditText
    private lateinit var et_signup_password : TextInputEditText
    private lateinit var et_signup_phone : TextInputEditText

    private lateinit var inputValidation: InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)

        iv_signup_back.setOnClickListener {

            val signup_home_intent = Intent(this, MainActivity::class.java)
            startActivity(signup_home_intent)
            finish()
        }

        tv_login_signup.setOnClickListener {

            val signin_intent = Intent(this, Login::class.java)
            startActivity(signin_intent)
            finish()

        }

        signup.setOnClickListener {

            postDataToSQLite()
        }
    }

    private fun initViews(){

        iv_signup_back = findViewById(R.id.iv_signup_back)
        tv_login_signup = findViewById(R.id.tv_login_signup)
        sv_signup = findViewById(R.id.sv_signup)
        signup = findViewById(R.id.signup)

        et_signup_name = findViewById(R.id.et_signup_name)
        et_signup_email = findViewById(R.id.et_signup_email)
        et_signup_phone = findViewById(R.id.et_signup_phone)
        et_signup_password = findViewById(R.id.et_signup_password)

    }

    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilledSignupName(et_signup_name, this)) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilledSignupPhone(et_signup_phone, this)) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmailSignup(et_signup_email, this)) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmailSignup(et_signup_email, this)) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilledSignupPassword(et_signup_password, this)) {
            return
        }


        if (!databaseHelper!!.checkUser(et_signup_email!!.text.toString().trim())) {

            var user = User(
                name = et_signup_name!!.text.toString().trim(),
                email = et_signup_email!!.text.toString().trim(),
                phone = et_signup_phone!!.text.toString().trim(),
                password = et_signup_password!!.text.toString().trim())

            databaseHelper!!.addUser(user)

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(sv_signup!!, "Success", Snackbar.LENGTH_LONG).show()

            Handler().postDelayed(Runnable {
                val i = Intent(this, Login::class.java)
                startActivity(i)
                finish()
            }, 2000)
            emptyInputEditText()

        } else {

            Snackbar.make(sv_signup!!, "Exists", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun emptyInputEditText() {
        et_signup_name!!.text = null
        et_signup_email!!.text = null
        et_signup_phone!!.text = null
        et_signup_password!!.text = null
    }

}