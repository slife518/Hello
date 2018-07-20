package com.example.registerusers.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.registerusers.db.DBHandler.Companion.DB_NAME
import org.jetbrains.anko.db.*

class DBHandler_Anko(context: Context) :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object {
        val DB_NAME: String = "user.db"
        val DB_VERSION: Int = 1
    }
    object UserTable {
        val TABLE_NAME = "user"
        val ID = "_id"
        val NAME = "name"
        val AGE = "age"
        val TEL_NUM = "telnum"
        val PIC_PATH = "pic_path"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(UserTable.TABLE_NAME, true,
                Pair(UserTable.ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT),
                Pair(UserTable.NAME, TEXT),
                Pair(UserTable.AGE, TEXT),
                Pair(UserTable.TEL_NUM, TEXT),
                Pair(UserTable.PIC_PATH, TEXT))
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getUserAllWithCursor(): Cursor {
        return readableDatabase.query(
                UserTable.TABLE_NAME,
                arrayOf(UserTable.ID, UserTable.NAME, UserTable.AGE,
                        UserTable.TEL_NUM, UserTable.PIC_PATH),
                null,
                null,
                null,
                null,
                null)
    }

    fun addUser(user: UserInfo) {
        var info = ContentValues()
        info.put(UserTable.NAME, user.name)
        info.put(UserTable.AGE, user.age)
        info.put(UserTable.TEL_NUM, user.telNum)
        info.put(UserTable.PIC_PATH, user.picPath)
        writableDatabase.use {
            it.insert(UserTable.TABLE_NAME, null, info)
        }
    }
    fun deleteUser(id: Long) {
        writableDatabase.use {
            it.execSQL("DELETE FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} = ${id} ")
        }
    }
}