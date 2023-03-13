package com.example.storiesapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // create user table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_PHONE + " NUMBER,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"


    // create userdata table sql query
    private val CREATE_USERDATA_TABLE = (((((("CREATE TABLE IF NOT EXISTS $TABLE_USERDATA" + "(" +
            COLUMN_USERDATA_ID) + " INTEGER PRIMARY KEY ,"
            + COLUMN_STORY_TITLE) + "  TEXT ,"
            + COLUMN_STORY) + "  TEXT ,"
            + COLUMN_AUTHOR) + "  TEXT ,"
            + COLUMN_C_EMAIL) + " TEXT, " + "FOREIGN KEY(" +
            COLUMN_C_EMAIL + ") "
            + "REFERENCES " + TABLE_USER) + "(" + COLUMN_USER_EMAIL + ")" + ");"

    // drop table sql query
    private val DROP_USERDATA_TABLE = "DROP TABLE IF EXISTS $TABLE_USERDATA"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_USERDATA_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_USERDATA_TABLE)

        // Create tables again
        onCreate(db)

    }

    @SuppressLint("Range")
    fun getAllUserData(c_email : String): List<UserData> {

        val columns = arrayOf(COLUMN_USERDATA_ID, COLUMN_STORY_TITLE,  COLUMN_STORY, COLUMN_AUTHOR, COLUMN_C_EMAIL)

        val userList = ArrayList<UserData>()

        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_USERDATA,
            columns,
            null,
            null,
            null,
            null,
            null)

        if (cursor.moveToFirst()) {
            do {
                val userData = UserData(
                    cid = cursor.getString(cursor.getColumnIndex(COLUMN_USERDATA_ID)).toInt(),
                    story_title = cursor.getString(cursor.getColumnIndex(COLUMN_STORY_TITLE)),
                    story = cursor.getString(cursor.getColumnIndex(COLUMN_STORY)),
                    author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                    c_email = cursor.getString(cursor.getColumnIndex(COLUMN_C_EMAIL)),
                )

                userList.add(userData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    @SuppressLint("Range")
    fun getMyUserData(c_email : String): List<UserData> {

        val columns = arrayOf(COLUMN_USERDATA_ID, COLUMN_STORY_TITLE,  COLUMN_STORY, COLUMN_AUTHOR, COLUMN_C_EMAIL)

        val userList = ArrayList<UserData>()

        val selection = "$COLUMN_C_EMAIL = ?"

        val selectionArgs = arrayOf(c_email)

        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_USERDATA,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        if (cursor.moveToFirst()) {
            do {
                val userData = UserData(
                    cid = cursor.getString(cursor.getColumnIndex(COLUMN_USERDATA_ID)).toInt(),
                    story_title = cursor.getString(cursor.getColumnIndex(COLUMN_STORY_TITLE)),
                    story = cursor.getString(cursor.getColumnIndex(COLUMN_STORY)),
                    author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                    c_email = cursor.getString(cursor.getColumnIndex(COLUMN_C_EMAIL)),
                )

                userList.add(userData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    //get username
    @SuppressLint("Range")
    fun getUserName (email: String, password: String): String {

        val columns = arrayOf(COLUMN_USER_NAME)

        val sortOrder = "$COLUMN_USER_NAME ASC"

        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"

        val selectionArgs = arrayOf(email, password)

        var username = ""

        val db = this.readableDatabase

        val cursor = db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder)
        if (cursor.moveToFirst()) {
            do {
                    username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return username
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_PHONE, user.phone)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun addUserData(user: UserData) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_C_EMAIL, user.c_email)
        values.put(COLUMN_AUTHOR, user.author)
        values.put(COLUMN_STORY_TITLE, user.story_title)
        values.put(COLUMN_STORY, user.story)

        // Inserting Row
        db.insert(TABLE_USERDATA, null, values)
        db.close()
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {

        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase

        val selection = "$COLUMN_USER_EMAIL = ?"

        val selectionArgs = arrayOf(email)

        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {

        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase

        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"

        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }

    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "UserManager.db"

        // User table name
        private val TABLE_USER = "user"

        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_PHONE = "user_phone"
        private val COLUMN_USER_PASSWORD = "user_password"

        // Userdata table name
        private val TABLE_USERDATA = "userdata"

        private val COLUMN_USERDATA_ID = "userdata_id"
        private val COLUMN_C_EMAIL = "c_email"
        private val COLUMN_AUTHOR = "author"
        private val COLUMN_STORY = "story"
        private val COLUMN_STORY_TITLE = "story_title"
    }
}