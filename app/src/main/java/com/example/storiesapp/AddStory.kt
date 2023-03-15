package com.example.storiesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.storiesapp.database.DatabaseHelper
import com.example.storiesapp.database.UserData
import com.example.storiesapp.helpers.InputValidation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class AddStory : AppCompatActivity() {

    private lateinit var iv_addstory_back : ImageView
    private lateinit var tv_login_email : TextView
    private lateinit var tv_login_username : TextView

    private lateinit var et_dialog_author : TextInputEditText
    private lateinit var et_dialog_story : TextInputEditText
    private lateinit var et_story_title : TextInputEditText

    private lateinit var btn_dialog_cancel : MaterialButton
    private lateinit var btn_dialog_submit : MaterialButton

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var inputValidation: InputValidation

    private lateinit var email : String
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)

        initViews()
        email = intent.getStringExtra("email").toString()
        username = intent.getStringExtra("username").toString()
        tv_login_email.text = email
        tv_login_username.text = username

        iv_addstory_back.setOnClickListener {

            val useractintent = Intent(this, UserActivity::class.java)
            useractintent.putExtra("email", tv_login_email!!.text.toString().trim { it <= ' ' })
            useractintent.putExtra("username", tv_login_username!!.text.toString().trim { it <= ' ' })
            startActivity(useractintent)
            finish()
        }

        btn_dialog_submit.setOnClickListener {

            addStory()

        }
        btn_dialog_cancel.setOnClickListener {

            val useractintent = Intent(this, UserActivity::class.java)
            useractintent.putExtra("email", tv_login_email!!.text.toString().trim { it <= ' ' })
            useractintent.putExtra("username", tv_login_username!!.text.toString().trim { it <= ' ' })
            startActivity(useractintent)
            finish()
        }
    }

    private fun addStory(){

        if (!inputValidation!!.isInputEditTextFilledAuthor(et_dialog_author, this)) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilledTitle(et_story_title, this)) {
            return
        }

        if (!inputValidation!!.isInputEditTextFilledStory(et_dialog_story, this)) {
            return
        }

        var userdata = UserData(
            c_email = tv_login_email!!.text.toString().trim(),
            author = et_dialog_author!!.text.toString().trim(),
            story_title = et_story_title!!.text.toString().trim(),
            story = et_dialog_story!!.text.toString().trim(),
        )

        databaseHelper!!.addUserData(userdata)

        val useractintent = Intent(this, UserActivity::class.java)
        useractintent.putExtra("email", tv_login_email!!.text.toString().trim { it <= ' ' })
        useractintent.putExtra("username", tv_login_username!!.text.toString().trim { it <= ' ' })
        startActivity(useractintent)
        finish()

    }

    private fun initViews(){

        iv_addstory_back = findViewById(R.id.iv_addstory_back)
        tv_login_email = findViewById(R.id.tv_login_email)
        tv_login_username = findViewById(R.id.tv_login_username)

        et_dialog_author = findViewById(R.id.et_dialog_author)
        et_dialog_story = findViewById(R.id.et_dialog_story)
        et_story_title = findViewById(R.id.et_story_title)

        btn_dialog_cancel = findViewById(R.id.btn_dialog_cancel)
        btn_dialog_submit = findViewById(R.id.btn_dialog_submit)
    }
}