package com.example.cfoodies

import Ingredient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class InsertActivity : AppCompatActivity() {
    private lateinit var insertBtn:TextView
    private lateinit var backBtn:ImageView
    private lateinit var inputIngredient:EditText
    private lateinit var inputCalories:EditText
    private lateinit var inputDesc:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)



        backBtn=findViewById(R.id.back)
        backBtn.setOnClickListener{
            onBackPressed()
        }

        inputIngredient=findViewById(R.id.input_ingredient)
        inputCalories=findViewById(R.id.input_calories)
        inputDesc=findViewById(R.id.input_desc)

        insertBtn= findViewById(R.id.btn_insert)
        insertBtn.setOnClickListener{
            val ingredientName = inputIngredient.text.toString()
            val ingredientDesc = inputDesc.text.toString()
            val ingredientCalories = inputCalories.text.toString()

//          Checking all field has been filled
            if (ingredientName.isNotBlank()&&ingredientDesc.isNotBlank()
                &&ingredientCalories.isNotBlank()){
                val calories= ingredientCalories.toInt()
                if (calories>0){
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("ingredients")

                    val ingredientId = myRef.push().key

                    if (ingredientId != null) {
                        val newIngredient = Ingredient(ingredientName, ingredientDesc, calories)
                        myRef.child(ingredientId).setValue(newIngredient)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data Has Been Inserted Successfully!!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Something Wrong Try Again Later", Toast.LENGTH_SHORT).show()
                            }
                    }
                }else{
                    Toast.makeText(this,"It's impossible to have calories 0 or under!!!",
                        Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"All field must be filled!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}