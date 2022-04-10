package com.example.jsondownloader.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.jsondownloader.db.user.User
import org.w3c.dom.Comment

object UserReaderContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
    }

    private const val SQL_CREATE_USER_ENTRIES =
        """
            CREATE TABLE ${UserEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${UserEntry.COLUMN_NAME} TEXT,
                ${UserEntry.COLUMN_EMAIL} TEXT
            )
        """

    private const val SQL_DELETE_USER_ENTRIES = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"

    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_TITLE = "title"
        const val COLUMN_COMPLETED = "completed"
        const val COLUMN_USER_ID = "userid"
    }

    const val SQL_CREATE_TODO_ENTRIES =
        """
            CREATE TABLE ${TodoEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${TodoEntry.COLUMN_TITLE} TEXT,
                ${TodoEntry.COLUMN_COMPLETED} INTEGER,
                ${TodoEntry.COLUMN_USER_ID} INTEGER NOT NULL,
                FOREIGN KEY (${TodoEntry.COLUMN_USER_ID})
                    REFERENCES ${UserEntry.TABLE_NAME} (${BaseColumns._ID})
            )
        """

    private const val SQL_DELETE_TODO_ENTRIES = "DROP TABLE IF EXISTS ${TodoEntry.TABLE_NAME}"

    object PostEntry : BaseColumns {
        const val TABLE_NAME = "post"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
        const val COLUMN_USER_ID = "userid"
    }

    const val SQL_CREATE_POST_ENTRIES =
        """
            CREATE TABLE ${PostEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${PostEntry.COLUMN_TITLE} TEXT,
                ${PostEntry.COLUMN_BODY} TEXT,
                ${PostEntry.COLUMN_USER_ID} INTEGER NOT NULL,
                FOREIGN KEY (${PostEntry.COLUMN_USER_ID})
                    REFERENCES ${UserEntry.TABLE_NAME} (${BaseColumns._ID})
            )
        """

    private const val SQL_DELETE_POST_ENTRIES = "DROP TABLE IF EXISTS ${PostEntry.TABLE_NAME}"

    object CommentEntry : BaseColumns {
        const val TABLE_NAME = "comment"
        const val COLUMN_NAME = "name"
        const val COLUMN_BODY = "body"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_POST_ID = "postid"
    }

    const val SQL_CREATE_COMMENT_ENTRIES =
        """
            CREATE TABLE ${CommentEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${CommentEntry.COLUMN_NAME} TEXT,
                ${CommentEntry.COLUMN_BODY} TEXT,
                ${CommentEntry.COLUMN_EMAIL} TEXT,
                ${CommentEntry.COLUMN_POST_ID} INTEGER NOT NULL,
                FOREIGN KEY (${CommentEntry.COLUMN_POST_ID})
                    REFERENCES ${PostEntry.TABLE_NAME} (${BaseColumns._ID})
            )
        """

    private const val SQL_DELETE_COMMENT_ENTRIES = "DROP TABLE IF EXISTS ${CommentEntry.TABLE_NAME}"

    class UserReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_USER_ENTRIES)
            db.execSQL(SQL_CREATE_POST_ENTRIES)
            db.execSQL(SQL_CREATE_TODO_ENTRIES)
            db.execSQL(SQL_CREATE_COMMENT_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_COMMENT_ENTRIES)
            db.execSQL(SQL_DELETE_POST_ENTRIES)
            db.execSQL(SQL_DELETE_TODO_ENTRIES)
            db.execSQL(SQL_DELETE_USER_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "UserReader.db"
        }

        fun addUser(user: User) {
            val db =
        }
    }
}