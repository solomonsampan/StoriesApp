package com.example.storiesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storiesapp.database.DatabaseHelper
import com.example.storiesapp.helpers.InputValidation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {

    private lateinit var iv_login_back : ImageView
    private lateinit var tv_login_signup: TextView

    private lateinit var et_login_username : TextInputEditText
    private lateinit var et_login_password : TextInputEditText

    private lateinit var login_username : TextInputLayout
    private lateinit var login_password : TextInputLayout

    private lateinit var login : MaterialButton
    private lateinit var sv_login : ScrollView

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var inputValidation: InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)

        iv_login_back.setOnClickListener {

            val login_home_intent = Intent(this, MainActivity::class.java)
            login_home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(login_home_intent)
            finish()
        }

        tv_login_signup.setOnClickListener {

            val signup_intent = Intent(this, SignUp::class.java)
            startActivity(signup_intent)
            finish()

        }

        login.setOnClickListener {

            verifyFromSQLite()
        }
    }

    private fun initViews(){

        iv_login_back = findViewById(R.id.iv_login_back)
        tv_login_signup = findViewById(R.id.tv_login_signup)
        sv_login = findViewById(R.id.sv_login)

        et_login_username = findViewById(R.id.et_login_username)
        et_login_password = findViewById(R.id.et_login_password)
        login = findViewById(R.id.login)

        login_username = findViewById(R.id.login_username)
        login_password = findViewById(R.id.login_password)

    }

    private fun verifyFromSQLite() {

        if (!inputValidation!!.isInputEditTextFilled(et_login_username!!, login_username!!, "Enter Valid Email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(et_login_username!!, login_username!!, "Enter Valid Email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(et_login_password!!, login_password!!, "Enter Password")) {
            return
        }

        if (databaseHelper!!.checkUser(et_login_username!!.text.toString().trim { it <= ' ' }, et_login_password!!.text.toString().trim { it <= ' ' })) {

            val user = databaseHelper.getUserName(et_login_username!!.text.toString().trim { it <= ' ' }, et_login_password!!.text.toString().trim { it <= ' ' })
            Log.i("MyTag", "Username: $user")
            val login_intent = Intent(this, UserActivity::class.java)
            login_intent.putExtra("email", et_login_username!!.text.toString().trim { it <= ' ' })
            login_intent.putExtra("username", user)
            emptyInputEditText()
            startActivity(login_intent)


        } else {

            Snackbar.make(sv_login!!, "Invalid Username or Password", 3000).setAction("SIGNUP", View.OnClickListener {

                val signup_intent = Intent(this, SignUp::class.java)
                startActivity(signup_intent)
                finish()

            }).show()
        }
    }

    private fun emptyInputEditText() {
        et_login_username!!.text = null
        et_login_password!!.text = null
    }

}