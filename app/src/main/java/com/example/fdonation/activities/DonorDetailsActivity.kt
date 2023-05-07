package com.example.fdonation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.fdonation.R
import com.example.fdonation.models.DonorModel
import com.google.firebase.database.FirebaseDatabase

class DonorDetailsActivity : AppCompatActivity() {

    private lateinit var tvlocation : TextView
    private lateinit var tvdate : TextView
    private lateinit var tvtime : TextView
    private lateinit var tvphone : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("donId").toString(),
                intent.getStringExtra("donlocation").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("donId").toString()
            )
        }
    }
    private fun deleteRecord(
        id :String
        )
    {
    val dbRef = FirebaseDatabase.getInstance().getReference("Donor").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Donors data deleted!",Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting ERR ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvlocation = findViewById(R.id.tvlocation) // replace with the actual id of the TextView in your layout file
        tvdate = findViewById(R.id.tvdate)
        tvtime = findViewById(R.id.tvtime)
        tvphone = findViewById(R.id.tvphone)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvlocation.text= intent.getStringExtra("donlocation")
        tvdate.text= intent.getStringExtra("dondate")
        tvtime.text= intent.getStringExtra("dontime")
        tvphone.text= intent.getStringExtra("donphone")
    }

    private fun openUpdateDialog(
        donId:String,
       donlocation:String
    )
    {
     val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

      mDialog.setView(mDialogView)

        val etlocation = mDialogView.findViewById<EditText>(R.id.etlocation)
        val etdate = mDialogView.findViewById<EditText>(R.id.etdate)
        val ettime = mDialogView.findViewById<EditText>(R.id.ettime)
        val etphone = mDialogView.findViewById<EditText>(R.id.etphone)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etlocation.setText(intent.getStringExtra("donlocation").toString())
        etdate.setText(intent.getStringExtra("dondate").toString())
        ettime.setText(intent.getStringExtra("dontime").toString())
        etphone.setText(intent.getStringExtra("donphone").toString())

       mDialog.setTitle("Updating $donlocation Record")

          val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateDonData(
                donId,
                etlocation.text.toString(),
                etdate.text.toString(),
                ettime.text.toString(),
                etphone.text.toString()
            )

            Toast.makeText(applicationContext,"Donor data Updated",Toast.LENGTH_LONG).show()

            //we are setting updated data to  our textviews

            tvlocation.text=etlocation.text.toString()
            tvdate.text=etdate.text.toString()
            tvtime.text=ettime.text.toString()
            tvphone.text=etphone.text.toString()

            alertDialog.dismiss()
        }


    }
    private fun updateDonData(
        id:String,
        location:String,
        date:String,
        time:String,
        phone:String
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Donor").child(id)    //we are not getting reference to the hall data base
        val doninfo = DonorModel(id,location,date,time,phone)
        dbRef.setValue(doninfo)

    }

}
