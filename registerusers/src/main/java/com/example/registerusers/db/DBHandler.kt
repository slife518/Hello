package com.example.registerusers.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 사용자 정보를 담을 데이터 클래스 생성
data class UserInfo( val name: String = "No Name", val age: String = "0",
                     val telNum: String = "No TelNum", val picPath: String)

class DBHandler(context: Context) :SQLiteOpenHelper(context, DB_NAME, null, DB_VERTION){
    companion object {
        val DB_NAME : String = "user.db"
        val DB_VERTION : Int = 1
    }

    val TABLE_NAME: String = "user"
    val ID: String = "_id"
    val NAME: String = "name"
    val AGE: String = "age"
    val TEL_NUM: String = "telnum"
    val PIC_PATH: String = "pic_path"
    val TABLE_CREATE: String = "CREATE TABLE IF NOT EXITST ${TABLE_NAME} ( " +
            " ${ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${NAME} TEXT, ${AGE} TEXT " +
            " ${AGE} TEXT, ${TEL_NUM} TEXT, ${PIC_PATH} TEXT ) "


    //DB가 생성되면 자동으로 호출되는 메소드.
    //최초에 테이블이 만들어지도록 코딩
    override fun onCreate(db: SQLiteDatabase?) {
        writableDatabase.execSQL(TABLE_CREATE)
    }

    //DB_VERSION 이 변경되었을 때, 자동으로 호출되는 메소드.
    //기능에 따라 추가되거나 삭제되는 컬럼 및 테이블의 쿼리를 작성해 수행시킨다.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    //DB 에 저장된 모든 데이터를 가져올 메소드
    fun getUserAllWithCursor(): Cursor {
        return readableDatabase.query(
                TABLE_NAME,
                arrayOf(ID, NAME, AGE, TEL_NUM, PIC_PATH),
                null,
                null,
                null,
                null,
                null
        )
    }

    //전달받은 인자를 DB에 저장할 메소드
    fun addUser(user: UserInfo){
        var info = ContentValues()
        info.put(NAME, user.name)
        info.put(AGE, user.age)
        info.put(TEL_NUM, user.telNum)
        info.put(PIC_PATH, user.picPath)

        writableDatabase.insert(TABLE_NAME, null, info)
    }

    //전달받은 아이디로 DB에 저장된 데이터를 삭제할 메소드
    fun deleteUser(id: Long) {
        writableDatabase.execSQL("DELETE FROM ${TABLE_NAME} WHERE ${ID} = ${id} ")
    }

}