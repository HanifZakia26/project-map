package com.example.cfoodies

import Ingredient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompareActivity : AppCompatActivity() {
    private lateinit var btnCompare:TextView
    private lateinit var btnBack:ImageView
    private lateinit var firstSpinner: Spinner
    private lateinit var secondSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)
        btnBack=findViewById(R.id.back)
        btnBack.setOnClickListener{
            onBackPressed()
        }
        firstSpinner=findViewById(R.id.spinner1)
        secondSpinner=findViewById(R.id.spinner2)
        btnCompare=findViewById(R.id.compare_btn)

        val databaseReference = FirebaseDatabase.getInstance().getReference("ingredients")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ingredientsList = mutableListOf<String>()

                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    ingredientsList.add(name ?: "")
                }

                val adapter = ArrayAdapter(this@CompareActivity, android.R.layout.simple_spinner_item, ingredientsList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                firstSpinner.adapter = adapter
                secondSpinner.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@CompareActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })


        btnCompare.setOnClickListener {
            val ingredients1 = firstSpinner.selectedItem
            val ingredients2= secondSpinner.selectedItem
            if(ingredients1==null || ingredients2==null){
                Toast.makeText(this, "Retrieving data", Toast.LENGTH_SHORT).show()
            }else{
                val selectedIngredient1 = ingredients1.toString()
                val selectedIngredient2 = ingredients2.toString()
                val databaseReference = FirebaseDatabase.getInstance().getReference("ingredients")

                if (selectedIngredient1 != selectedIngredient2) {
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var ingredient1: Ingredient = Ingredient("", "", 0)
                            var ingredient2: Ingredient = Ingredient("", "", 0)


                            for (snapshot in dataSnapshot.children) {
                                val name = snapshot.child("name").getValue(String::class.java)
                                val cal = snapshot.child("cal").getValue(Int::class.java)?:0
                                val desc = snapshot.child("desc").getValue(String::class.java)?:""

                                if (name == selectedIngredient1) {
                                    ingredient1 = Ingredient(name, desc, cal)
                                } else if (name == selectedIngredient2) {
                                    ingredient2 = Ingredient(name, desc, cal)
                                }
                            }
                            val intent = Intent(this@CompareActivity, ResultActivity::class.java)

                            if (ingredient1.cal > ingredient2.cal) {
                                intent.putExtra("name1", ingredient1.name)
                                intent.putExtra("cal1", ingredient1.cal)
                                intent.putExtra("desc1", ingredient1.desc)
                                intent.putExtra("name2", ingredient2.name)
                                intent.putExtra("cal2", ingredient2.cal)
                                intent.putExtra("desc2", ingredient2.desc)
                            } else {
                                intent.putExtra("name1", ingredient2.name)
                                intent.putExtra("cal1", ingredient2.cal)
                                intent.putExtra("desc1", ingredient2.desc)
                                intent.putExtra("name2", ingredient1.name)
                                intent.putExtra("cal2", ingredient1.cal)
                                intent.putExtra("desc2", ingredient1.desc)
                            }
                            startActivity(intent)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(this@CompareActivity, "Please select valid ingredients", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@CompareActivity, "You need to choose different ingredients", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}