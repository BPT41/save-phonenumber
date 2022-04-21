package com.example.ch16ephone

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper private constructor(context: Context)
    :SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION)
{
        companion object {
            private val DB_NAME = "mydb"
            private val DB_VERSION = 1
            private var sqLiteHelper : SQLiteHelper? = null

            @Synchronized
            fun getInstance(c: Context): SQLiteHelper {
                return if(sqLiteHelper == null){
                    SQLiteHelper((c.applicationContext))
                }
                else{
                    sqLiteHelper!!
                }

            }
        }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql =  """CREATE TABLE emergency_call(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT,
            phone TEXT UNIQUE
            )"""

        db?.execSQL(sql)

        sql = """INSERT INTO emergency_call(_id, name, phone)VALUES
                    (null,'เหตุด่วนเหตุร้าย','191'),
                    (null,'เหตุด่วนเหตุร้าย','199'),
                    (null,'แพทย์ฉุก','1669'),
                    (null,'จส.100','1137'),
                    (null,'ตำรวจทางหลวง','193')
        """
        db?.execSQL(sql)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}