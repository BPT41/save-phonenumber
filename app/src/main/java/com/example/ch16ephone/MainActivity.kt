package com.example.ch16ephone

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.view.ViewParent
import android.widget.*
import com.example.ch16ephone.SQLiteHelper.Companion.getInstance


class MainActivity : AppCompatActivity() {
    private lateinit var mSQLiteHelper: SQLiteHelper
    private lateinit var mDb :SQLiteDatabase
    private lateinit var mCursor: Cursor
    private lateinit var mSpinner:Spinner
    private lateinit var mEditPhone: EditText
    private var _id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSQLiteHelper = getInstance(this)
        mDb = mSQLiteHelper.writableDatabase

        mSpinner = findViewById(R.id.spinner)
        mEditPhone = findViewById(R.id.editText)

        readData()
        btnAddClick()
        btnUpdateClick()
        btnDeleteClick()
    }
    private fun btnAddClick(){
        findViewById<Button>(R.id.button_add).apply {
            setOnClickListener {
                val intent = Intent(baseContext,AddDataActivity::class.java)
                startActivityForResult(intent,999)
            }
        }

    }
    private fun btnUpdateClick(){
        findViewById<Button>(R.id.button_save).apply {
            setOnClickListener {
                val cv = ContentValues()
                cv.put("phone",mEditPhone.text.toString())
                mDb.update("emergency_call",cv,"_id =?", arrayOf("$_id"))

                toast("บันทึกข้อมูล")
                readData()
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(baseContext,msg,Toast.LENGTH_LONG).show()
    }
    private fun btnDeleteClick(){
        findViewById<Button>(R.id.button_delete).apply{
            setOnClickListener {
                val sql = "DElETE FROM emergency_call WHERE _id = ?"
                val args = arrayOf(_id)
                mDb.execSQL(sql,args)
                toast("ลบข้อมูลแล้ว")
                readData()
            }
        }
    }
    private fun readData(){
        val sql = "SELECT * FROM emergency_call"
        mCursor = mDb.rawQuery(sql,null)
        updateSpinner()
        spinnerChange()
    }
    private fun updateSpinner(){
        val items = mutableListOf<String>()
        while (mCursor.moveToNext()){
            items.add(mCursor.getString(1))
        }
        val adapter =ArrayAdapter(baseContext,android.R.layout.simple_spinner_dropdown_item,items)
        mSpinner.adapter =adapter
    }
    private fun spinnerChange(){
        mSpinner.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent:AdapterView<*>?, v: View?,postion:Int,id:Long){
                    mCursor.moveToPosition(postion)
                    val phone = mCursor.getString(2)
                    mEditPhone.setText(phone)
                    _id = mCursor.getInt(0)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        )
    }
}