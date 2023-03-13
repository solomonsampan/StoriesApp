package com.example.storiesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storiesapp.adapter.UsersRecyclerAdapter
import com.example.storiesapp.database.DatabaseHelper
import com.example.storiesapp.database.UserData
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class UserActivity : AppCompatActivity() {

    private lateinit var iv_useract_back : ImageView
    private lateinit var tv_login_email : TextView
    private lateinit var tv_login_username : TextView

    private lateinit var fab_useract: FloatingActionButton
    private lateinit var rv_userdata : RecyclerView


    private lateinit var listUsers: MutableList<UserData>
    private lateinit var MyList: MutableList<UserData>

    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var rl_useractivity : RelativeLayout
    private lateinit var tv_no_stories : TextView

    private lateinit var cv_other : MaterialCardView
    private lateinit var cv_my : MaterialCardView
    private lateinit var tv_other : TextView
    private lateinit var tv_my : TextView
    private lateinit var tv_my_count : TextView
    private lateinit var tv_other_count : TextView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        databaseHelper = DatabaseHelper(this)

        initViews()
        var email = intent.getStringExtra("email")
        var username = intent.getStringExtra("username")
        tv_login_email.text = email
        tv_login_username.text = username
        if (email != null) {
            initObjects(email)
        }

        iv_useract_back.setOnClickListener {

            showLogoutDialog()

        }
        fab_useract.setOnClickListener {

            val addStoryIntent = Intent(this, AddStory::class.java)
            addStoryIntent.putExtra("email", tv_login_email!!.text.toString().trim { it <= ' ' })
            addStoryIntent.putExtra("username", tv_login_username!!.text.toString().trim { it <= ' ' })
            startActivity(addStoryIntent)

        }

        cv_other.setOnClickListener {

            cv_other.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary));
            tv_other.setTextColor(ContextCompat.getColor(this, R.color.white));

            cv_my.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            tv_my.setTextColor(ContextCompat.getColor(this, R.color.black));

            tv_my_count.setTextColor(ContextCompat.getColor(this, R.color.white));
            tv_other_count.setTextColor(ContextCompat.getColor(this, R.color.primary));

            tv_other_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape) );
            tv_my_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape_color) );

            listUsers = ArrayList()
            val email: String = tv_login_email.text.toString()

            listUsers = databaseHelper.getAllUserData(email).toMutableList()

            tv_other_count.setText(""+listUsers.size)
            tv_my_count.text = MyList.size.toString()

            if (listUsers.size <= 0){

                tv_no_stories.visibility = VISIBLE
                rv_userdata.visibility = GONE

            }else{

                tv_no_stories.visibility = GONE
                rv_userdata.visibility = VISIBLE
            }

            usersRecyclerAdapter = UsersRecyclerAdapter(listUsers)

            val linearLayoutManager = LinearLayoutManager(this)
            rv_userdata.setLayoutManager(linearLayoutManager)
            rv_userdata.setHasFixedSize(true)

            val mAdapter = UsersRecyclerAdapter(listUsers)
            rv_userdata.setAdapter(mAdapter)

        }

        cv_my.setOnClickListener {

            cv_my.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary));
            tv_my.setTextColor(ContextCompat.getColor(this, R.color.white));

            cv_other.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            tv_other.setTextColor(ContextCompat.getColor(this, R.color.black));

            tv_my_count.setTextColor(ContextCompat.getColor(this, R.color.primary));
            tv_other_count.setTextColor(ContextCompat.getColor(this, R.color.white));

            tv_other_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape_color) );
            tv_my_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape) );

            MyList = ArrayList()
            val email: String = tv_login_email.text.toString()

            MyList = databaseHelper.getMyUserData(email).toMutableList()

            tv_other_count.setText(""+listUsers.size)
            tv_my_count.setText(""+MyList.size)

            if (MyList.size <= 0){

                tv_no_stories.visibility = VISIBLE
                rv_userdata.visibility = GONE

            }else{

                tv_no_stories.visibility = GONE
                rv_userdata.visibility = VISIBLE
            }

            usersRecyclerAdapter = UsersRecyclerAdapter(MyList)

            val linearLayoutManager = LinearLayoutManager(this)
            rv_userdata.setLayoutManager(linearLayoutManager)
            rv_userdata.setHasFixedSize(true)
            // databaseHelper = DatabaseHelper(this)

            val mAdapter = UsersRecyclerAdapter(MyList)
            rv_userdata.adapter = mAdapter

        }

    }

    private fun initObjects(email : String) {

        listUsers = ArrayList()
        MyList = ArrayList()

        listUsers = databaseHelper.getAllUserData(email).toMutableList()
        MyList = databaseHelper.getMyUserData(email).toMutableList()

        tv_my_count.setTextColor(ContextCompat.getColor(this, R.color.white));
        tv_other_count.setTextColor(ContextCompat.getColor(this, R.color.primary));

        tv_other_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape) );
        tv_my_count.setBackgroundDrawable( resources.getDrawable(R.drawable.round_shape_color) );

        tv_other_count.text = listUsers.size.toString()
        tv_my_count.text = MyList.size.toString()

        if (listUsers.size <= 0){

            tv_no_stories.visibility = VISIBLE
            rv_userdata.visibility = GONE

        }else{

            tv_no_stories.visibility = GONE
            rv_userdata.visibility = VISIBLE
        }

        usersRecyclerAdapter = UsersRecyclerAdapter(listUsers)

        val linearLayoutManager = LinearLayoutManager(this)
        rv_userdata.setLayoutManager(linearLayoutManager)
        rv_userdata.setHasFixedSize(true)
        // databaseHelper = DatabaseHelper(this)

        val mAdapter = UsersRecyclerAdapter(listUsers)
        rv_userdata.setAdapter(mAdapter)

    }

    private fun initViews(){

        iv_useract_back = findViewById(R.id.iv_useract_back)
        tv_login_email = findViewById(R.id.tv_login_email)
        tv_login_username = findViewById(R.id.tv_login_username)

        fab_useract = findViewById(R.id.fab_useract)
        rv_userdata = findViewById(R.id.rv_userdata)
        rl_useractivity = findViewById(R.id.rl_useractivity)

        tv_no_stories = findViewById(R.id.tv_no_stories)

        cv_other = findViewById(R.id.cv_other)
        cv_my = findViewById(R.id.cv_my)
        tv_other = findViewById(R.id.tv_other)
        tv_my = findViewById(R.id.tv_my)
        tv_my_count = findViewById(R.id.tv_my_count)
        tv_other_count = findViewById(R.id.tv_other_count)

    }

    private fun showLogoutDialog(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert!")
        builder.setMessage("Do you wish to Logout?")

        builder.setPositiveButton("Yes") { dialog, which ->

            val signin_intent = Intent(this, Login::class.java)
            startActivity(signin_intent)
            finish()

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        builder.show()
    }

    override fun onResume() {
        super.onResume()
        Log.i("MyTag", "OnResume called")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {}
}