package com.example.numberguesser.DB.Contract

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.numberguesser.DB.User.User

object Contract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_CURRENT_SCORE = "current_score"
    }

    private const val SQL_CREATE_ENTRIES =
        """CREATE TABLE ${UserEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${UserEntry.COLUMN_NAME_USERNAME} TEXT,
                ${UserEntry.COLUMN_NAME_PASSWORD} TEXT,
                ${UserEntry.COLUMN_NAME_CURRENT_SCORE} INTEGER
            )"""
    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"

    class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "NumberGuesser.db"
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        val scoreBoard: List<User>
            get() {
                val listUser = ArrayList<User>()
                val selectQuery = "SELECT * FROM ${UserEntry.TABLE_NAME}"
                val db = this.writableDatabase
                val cursor = db.rawQuery(selectQuery, null)
                if (cursor.moveToFirst()) {
                    do {
                        val user = User()
                        user._ID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                        user.username = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_USERNAME))

                        listUser.add(user)
                    } while (cursor.moveToNext())
                }
                db.close()
                return listUser
            }

        fun addUser(user: User) {
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(UserEntry.COLUMN_NAME_USERNAME, user.username)
            values.put(UserEntry.COLUMN_NAME_PASSWORD, user.password)
            values.put(UserEntry.COLUMN_NAME_CURRENT_SCORE, user.currentScore)

            db.insert(UserEntry.TABLE_NAME, null, values)
            db.close()
        }

        fun getUserByUsername(username: String) : User {
            val db = this.readableDatabase
            val selectQuery = "SELECT * FROM ${UserEntry.TABLE_NAME} WHERE ${UserEntry.COLUMN_NAME_USERNAME} LIKE \"${username}\""
            val cursor = db.rawQuery(selectQuery, null)
            val user = User()
            if (cursor.moveToFirst()) {
                with(cursor){
                    user._ID = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    user.username = getString(getColumnIndexOrThrow(UserEntry.COLUMN_NAME_USERNAME))
                    user.password = getString(getColumnIndexOrThrow(UserEntry.COLUMN_NAME_PASSWORD))
                    user.currentScore = getInt(getColumnIndexOrThrow(UserEntry.COLUMN_NAME_CURRENT_SCORE))
                }
            }
            return user
        }

        fun updateUserScore(username: String, score: Int) : Int {
            val db = this.writableDatabase

            val values = ContentValues().apply{
                put(UserEntry.COLUMN_NAME_CURRENT_SCORE, score)
            }

            val selection = "${UserEntry.COLUMN_NAME_USERNAME} LIKE ?"
            val selectionArgs = arrayOf(username)
            val count = db.update(UserEntry.TABLE_NAME, values, selection, selectionArgs)
            return count
        }
    }
}