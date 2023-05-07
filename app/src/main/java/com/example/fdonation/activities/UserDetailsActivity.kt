package com.example.fdonation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fdonation.R
import com.example.fdonation.models.UserModel
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity:AppCompatActivity() {

    private lateinit var tvUserId:TextView
    private lateinit var tvName: TextView
    private lateinit var tvFoodType:TextView
    private lateinit var tvQuantity:TextView
    private lateinit var tvDate:TextView
    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("foodName").toString()

            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }



    }

    private fun deleteRecord(
        id: String,

    ){
        val dbRef=FirebaseDatabase.getInstance().getReference("FoodDonation").child(id)
        val mTask=dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Donor Details deleted",Toast.LENGTH_LONG).show()
            val intent= Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Can't delete because of error ${error.message}",Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvUserId=findViewById(R.id.tvUserId)
        tvName=findViewById(R.id.tvName)
        tvFoodType=findViewById(R.id.tvFoodType)
        tvQuantity=findViewById(R.id.tvQuantity)
        tvDate=findViewById(R.id.tvDate)

        btnUpdate=findViewById(R.id.btnUpdate)
        btnDelete=findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvUserId.text=intent.getStringExtra("userId")
        tvName.text=intent.getStringExtra("foodName")
        tvFoodType.text=intent.getStringExtra("foodType")
        tvQuantity.text=intent.getStringExtra("quantity")
        tvDate.text=intent.getStringExtra("date")
    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        userId:String,
        foodName:String
    ){
         val mDialog=AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView=inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etFoodName=mDialogView.findViewById<EditText>(R.id.etFoodName)
        val etFoodType=mDialogView.findViewById<EditText>(R.id.etFoodType)
        val etFoodQuantity=mDialogView.findViewById<EditText>(R.id.etFoodQuantity)
        val etDate=mDialogView.findViewById<EditText>(R.id.etDate)
        val btnUpdateData=mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etFoodName.setText( intent.getStringExtra("foodName").toString())
        etFoodType.setText( intent.getStringExtra("foodType").toString())
        etFoodQuantity.setText( intent.getStringExtra("quantity").toString())
        etDate.setText( intent.getStringExtra("date").toString())

        mDialog.setTitle("Updating $foodName Record")

        val alertDialog=mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateUserData(
                userId,etFoodName.text.toString(),
                etFoodType.text.toString(),
                etFoodQuantity.text.toString(),
                etDate.text.toString()
            )
            Toast.makeText(applicationContext,"Donor Details Updated",Toast.LENGTH_LONG).show()

            tvName.text=etFoodName.text.toString()
            tvFoodType.text=etFoodType.text.toString()
            tvQuantity.text=etFoodQuantity.text.toString()
            tvDate.text=etDate.text.toString()

            alertDialog.dismiss()
        }


    }

    private fun updateUserData(
        id:String,
        name:String,
        type:String,
        quantity:String,
        date:String
    ){
        val dbRef=FirebaseDatabase.getInstance().getReference("FoodDonation").child(id)
        val userInfo=UserModel(id,name,type,quantity,date)
        dbRef.setValue(userInfo)
    }



}