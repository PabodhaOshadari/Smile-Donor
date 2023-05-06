package com.example.fdonation.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fdonation.R
import com.example.fdonation.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FoodInsertion:AppCompatActivity() {

    private lateinit var etFoodName:EditText
    private lateinit var etFoodType:EditText
    private lateinit var etFoodQuantity:EditText
    private lateinit var etDate:EditText
    private lateinit var btnSaveData:Button

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.foodinsertion_main)

        etFoodName=findViewById(R.id.etFoodName)
        etFoodType=findViewById(R.id.etFoodType)
        etFoodQuantity=findViewById(R.id.etFoodQuantity)
        etDate=findViewById(R.id.etDate)
        btnSaveData=findViewById(R.id.btnSave)

        dbRef=FirebaseDatabase.getInstance().getReference("FoodDonation")

        btnSaveData.setOnClickListener {
            saveFoodDonationData()
        }


    }

    private fun saveFoodDonationData(){
        val foodName=etFoodName.text.toString()
        val foodType=etFoodType.text.toString()
        val quantity=etFoodQuantity.text.toString()
        val date=etDate.text.toString()

        if(foodName.isEmpty()){                        //validation
            etFoodName.error="Please Enter Food Name"
        }
        if(foodType.isEmpty()){
            etFoodType.error="Please Enter Food Type"
        }
        if(quantity.isEmpty()){
            etFoodQuantity.error="Please Enter Food Quantity"
        }
        if(date.isEmpty()){
            etDate.error="Please Enter Date"
        }

        val userId=dbRef.push().key!!

        val user= UserModel(userId,foodName,foodType,quantity,date)

        dbRef.child(userId).setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this,"Insert Succesfull",Toast.LENGTH_LONG).show()

                etFoodName.text.clear()
                etFoodType.text.clear()
                etFoodQuantity.text.clear()
                etDate.text.clear()

            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }
    }
}