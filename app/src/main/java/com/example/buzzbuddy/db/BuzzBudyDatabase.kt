package com.example.buzzbuddy.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import com.example.buzzbuddy.data.DataDto
import com.example.buzzbuddy.data.UserDto
import java.util.GregorianCalendar


class BuzzBudyDatabase(mContext: Context) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION
){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableuser = """
            CREATE TABLE $USER_TABLE_NAME(
                $USER_ID integer PRIMARY KEY,
                $USER_FIRST_NAME varchar(20),
                $USER_LAST_NAME varchar(20),
                $USER_PHONE varchar(20)
            )
        """.trimIndent()

        val createTabledata = """
            CREATE TABLE $DATA_TABLE_NAME(
                $DATA_ID integer PRIMARY KEY,
                $DATA_PHONE varchar(20),
                $DATA_LAST_LOG default current_timestamp,
                $DATA_HEADER_COLOR integer
            )
        """.trimIndent()
        db?.execSQL(createTableuser)
        db?.execSQL(createTabledata)

    }

    fun addMessage(time: Long): Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DATA_LAST_LOG, time)

        val result = db.update(DATA_TABLE_NAME, values, "$DATA_ID=?", arrayOf("1")).toInt()
        db.close()
        return result != -1
    }

    fun editLastLog(time: Long): Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DATA_LAST_LOG, time)

        val result = db.update(DATA_TABLE_NAME, values, "$DATA_ID=?", arrayOf("1")).toInt()
        db.close()
        return result != -1
    }

    fun editColor(color: Int): Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DATA_HEADER_COLOR, color)

        val result = db.update(DATA_TABLE_NAME, values, "$DATA_ID=?", arrayOf("1")).toInt()
        db.close()
        return result != -1
    }

    fun getLastLog(): Long {
        var time: Long = 0
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $DATA_TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null)
            if(cursor.moveToFirst()) {
                time = cursor.getLong(cursor.getColumnIndexOrThrow(DATA_LAST_LOG))
            }
        db.close()
        return time
    }

    fun getHeaderColor(): Int {
        var color: Int = 0
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $DATA_TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null)
            if(cursor.moveToFirst()) {
                color = cursor.getInt(cursor.getColumnIndexOrThrow(DATA_HEADER_COLOR))
            }
        db.close()
        return color
    }

    fun checkLogin(): DataDto? {
        var data: DataDto? = null
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $DATA_TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null)
            if(cursor.moveToFirst()) {
                val dataid = cursor.getInt(cursor.getColumnIndexOrThrow(DATA_ID))
                val dataphone = cursor.getString(cursor.getColumnIndexOrThrow(DATA_PHONE))
                val datalastlog =  cursor.getLong(cursor.getColumnIndexOrThrow(DATA_LAST_LOG))
                val dataheadercolor = cursor.getInt(cursor.getColumnIndexOrThrow(DATA_HEADER_COLOR))
                val data = DataDto(dataid, dataphone, datalastlog, dataheadercolor)
                return data
            }
        db.close()
        return data
    }

    fun firstLogin(phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        val time = GregorianCalendar()

        values.put(DATA_PHONE, phone)
        values.put(DATA_LAST_LOG, time.timeInMillis)
        values.put(DATA_HEADER_COLOR, Color.parseColor("aqua"))

        val result = db.insert(DATA_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    fun addUser(user: UserDto): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_FIRST_NAME, user.user_first_name)
        values.put(USER_LAST_NAME, user.user_last_name)
        values.put(USER_PHONE, user.user_phone)

        val result = db.insert(USER_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    fun findUser(user_phone: String) : UserDto?
    {
        var user: UserDto? = null
        val db = this.readableDatabase
        val selectionArgs = arrayOf(user_phone)
        val cursor = db.query(USER_TABLE_NAME, null,"$USER_PHONE=?" ,selectionArgs, null, null, null)
        if(cursor != null)
            if(cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                val firstname = cursor.getString(cursor.getColumnIndexOrThrow(USER_FIRST_NAME))
                val lastname = cursor.getString(cursor.getColumnIndexOrThrow(USER_LAST_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(USER_PHONE))
                val user = UserDto(id, firstname, lastname, phone)
                return user
            }
        db.close()
        return user
    }

    fun getAllUsers() : ArrayList<UserDto>
    {
        var userArray = ArrayList<UserDto>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null)
            if(cursor.moveToFirst()) {
                do{
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                    val firstname = cursor.getString(cursor.getColumnIndexOrThrow(USER_FIRST_NAME))
                    val lastname = cursor.getString(cursor.getColumnIndexOrThrow(USER_LAST_NAME))
                    val phone = cursor.getString(cursor.getColumnIndexOrThrow(USER_PHONE))
                    val user = UserDto(id, firstname, lastname, phone)
                    userArray.add(user)
                } while(cursor.moveToNext())
            }
        db.close()
        return userArray
    }

    fun deleteUser(user_phone: String): Int
    {
        val db = writableDatabase

        val rowDeleted = db.delete(USER_TABLE_NAME, "$USER_PHONE=?", arrayOf(user_phone))
        db.close()
        return rowDeleted
    }

    fun updateUser(user: UserDto): Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_FIRST_NAME, user.user_first_name)
        values.put(USER_LAST_NAME, user.user_last_name)
        values.put(USER_PHONE, user.user_phone)

        val result = db.update(USER_TABLE_NAME, values, "$USER_ID=?", arrayOf(user.id.toString())).toInt()
        db.close()
        return result != -1
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $DATA_TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "buzzbudy_db"
        private const val DB_VERSION = 1
        private const val USER_TABLE_NAME = "users"
        private const val USER_FIRST_NAME = "user_firstname"
        private const val USER_LAST_NAME = "user_lastname"
        private const val USER_ID = "user_id"
        private const val USER_PHONE = "user_phone"

        private const val DATA_TABLE_NAME = "data"
        private const val DATA_ID = "data_id"
        private const val DATA_PHONE = "data_phone"
        private const val DATA_LAST_LOG = "data_last_log"
        private const val DATA_HEADER_COLOR = "data_header_color"
    }

}
