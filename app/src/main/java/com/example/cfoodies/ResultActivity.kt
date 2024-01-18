package com.example.cfoodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private lateinit var result1: TextView
    private lateinit var textCalories1: TextView
    private lateinit var textDesc1: TextView
    private lateinit var title1:TextView
    private lateinit var result2: TextView
    private lateinit var textCalories2: TextView
    private lateinit var textDesc2: TextView
    private lateinit var title2:TextView
    private lateinit var doneBtn:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        result1 = findViewById(R.id.text_result1)
        textCalories1 = findViewById(R.id.text_calories1)
        textDesc1 = findViewById(R.id.text_desc1)
        title1=findViewById(R.id.title_desc1)
        result2 = findViewById(R.id.text_result2)
        textCalories2 = findViewById(R.id.text_calories2)
        textDesc2 = findViewById(R.id.text_desc2)
        title2=findViewById(R.id.title_desc2)
        doneBtn=findViewById(R.id.done_btn)
        val intent = intent
        if (intent != null) {
            val name1 = intent.getStringExtra("name1")
            val cal1 = intent.getIntExtra("cal1", 0)
            val desc1 = intent.getStringExtra("desc1")

            val name2 = intent.getStringExtra("name2")
            val cal2 = intent.getIntExtra("cal2", 0)
            val desc2 = intent.getStringExtra("desc2")

            // Set textViews dengan data yang diterima dari intent
            result1.text = name1
            textCalories1.text = "$cal1 KCal"
            textDesc1.text = desc1
            title1.text=name1
            result2.text = name2
            textCalories2.text = "$cal2 KCal"
            textDesc2.text = desc2
            title2.text=name2
        }

        doneBtn.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}