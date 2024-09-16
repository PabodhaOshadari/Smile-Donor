package com.example.fdonation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fdonation.R
import com.example.fdonation.models.DonorModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etlocation : EditText
    private lateinit var etdate : EditText
    private lateinit var ettime : EditText
    private lateinit var etphone : EditText
    private lateinit var btnsave : Button

    private lateinit var dbref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etlocation = findViewById(R.id.etlocation)
        etdate = findViewById(R.id.etdate)
        ettime = findViewById(R.id.ettime)
        etphone = findViewById(R.id.etphone)
        btnsave = findViewById(R.id.btnSave)

        dbref = FirebaseDatabase.getInstance().getReference("Donor")

        btnsave.setOnClickListener {
           saveDonordata()
        }
    }

    private fun saveDonordata()
    {
        //getting values
        val location = etlocation.text.toString()
        val date = etdate.text.toString()
        val time = ettime.text.toString()
        val phone = etphone.text.toString()

        if(location.isEmpty())
        {
            etlocation.error = "please enter location"
        }
        if(location.isEmpty())
        {
            etdate.error = "please enter date"
        }
        if(location.isEmpty())
        {
            ettime.error = "please enter Time"
        }
        if(location.isEmpty())
        {
            etphone.error = "please enter phone"
        }
        val donorId = dbref.push().key!!

        val donor = DonorModel(donorId,location,date,time,phone)

        dbref.child(donorId).setValue(donor)
            .addOnCompleteListener{
                Toast.makeText(this,"inserted succesfully",Toast.LENGTH_LONG).show()

                etlocation.text.clear()
                etdate.text.clear()
                ettime.text.clear()
                etphone.text.clear()


            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }
}