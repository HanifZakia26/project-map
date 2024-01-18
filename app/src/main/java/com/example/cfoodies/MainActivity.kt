package com.example.cfoodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var btnInsert:TextView
    private lateinit var btnCompare:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsert= findViewById(R.id.btn_insert)
        btnInsert.setOnClickListener{
            val intent = Intent(this,InsertActivity::class.java)
            startActivity(intent)
        }

        btnCompare=findViewById(R.id.btn_compare)
        btnCompare.setOnClickListener{
            val intent = Intent(this,CompareActivity::class.java)
            startActivity(intent)
        }
    }
}