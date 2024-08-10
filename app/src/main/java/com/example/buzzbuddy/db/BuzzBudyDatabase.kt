package com.example.buzzbuddy.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.buzzbuddy.data.UserDto


class BuzzBudyDatabase(mContext: Context) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION
){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableuser = """
            CREATE TABLE users(
                $USER_ID integer PRIMARY KEY,
                $USER_FIRST_NAME varchar(20),
                $USER_LAST_NAME varchar(20),
                $USER_PHONE varchar(20)
            )
        """.trimIndent()
        db?.execSQL(createTableuser)
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
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "buzzbudy_db"
        private const val DB_VERSION = 1
        private const val USER_TABLE_NAME = "users"
        private const val USER_FIRST_NAME = "firstname"
        private const val USER_LAST_NAME = "lastname"
        private const val USER_ID = "id"
        private const val USER_PHONE = "phone"
    }

}
