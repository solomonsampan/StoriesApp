package com.example.storiesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var login: MaterialButton
    private lateinit var signup: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        login.setOnClickListener {

            val login_intent = Intent(this, Login::class.java)
            startActivity(login_intent)
        }

        signup.setOnClickListener {

            val signup_intent = Intent(this, SignUp::class.java)
            startActivity(signup_intent)
        }

    }

    private fun initViews(){

        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Your logic here
        finish()
    }
}