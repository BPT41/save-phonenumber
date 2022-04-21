package com.example.ch16ephone

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddDataActivity : AppCompatActivity() {
    private lateinit var mEdtOrgName:EditText
    private lateinit var mEdtPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        mEdtOrgName = findViewById(R.id.editText_org_name)
        mEdtPhone = findViewById(R.id.editText_phone)
        btnOKClick()
    }
    private fun btnOKClick(){
        findViewById<Button>(R.id.button_ok).apply {
            setOnClickListener {
                val sqLiteHelper = SQLiteHelper.getInstance(baseContext)
                val db = sqLiteHelper.writableDatabase

                val cv = ContentValues()
                cv.put("name",mEdtOrgName.text.toString())
                cv.put("phone",mEdtPhone.text.toString())
                db.insert("emergency_call",null,cv)

                Toast.makeText(baseContext,"บันทึกข้อมูลแล้ว",Toast.LENGTH_LONG).show()
            }
        }
    }
}