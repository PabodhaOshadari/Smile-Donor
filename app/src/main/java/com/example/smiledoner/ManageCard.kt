package com.example.smiledoner

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.smiledoner.databinding.ActivityManageCardBinding
import com.example.smiledoner.model.cardModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ManageCard : AppCompatActivity() {


    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpNo: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpDate: TextView
    private lateinit var tvEmpcvv: TextView
    private lateinit var btnUpdate: Button

    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_manage_card)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empName").toString()
            )
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_payment_details, null)

        mDialog.setView(mDialogView)

        val etEmpName = mDialogView.findViewById<EditText>(R.id.card_name2)
        val etEmpNo = mDialogView.findViewById<EditText>(R.id.card_number2)
        val etEmpDate = mDialogView.findViewById<EditText>(R.id.card_date)
        val etEmpCvv = mDialogView.findViewById<EditText>(R.id.card_cvv)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.button)

        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpNo.setText(intent.getStringExtra("empNumber").toString())
        etEmpDate.setText(intent.getStringExtra("empDate").toString())
        etEmpCvv.setText(intent.getStringExtra("empCVV").toString())



        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpNo.text.toString(),
                etEmpDate.text.toString(),
                etEmpCvv.text.toString()

            )

            Toast.makeText(applicationContext, "Card Detailes Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etEmpName.text.toString()
            tvEmpNo.text = etEmpNo.text.toString()
            tvEmpDate.text = etEmpDate.text.toString()
            tvEmpcvv.text = etEmpCvv.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        empID: String,
        empName: String,
        empNumber: String,
        empDate: String,
        empCvv: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("cards").child(empID)
        val empInfo = cardModel(empID, empName, empNumber, empDate, empCvv)
        dbRef.setValue(empInfo)
    }


        private fun initView() {
            tvEmpName = findViewById(R.id.cardname2)
            tvEmpNo = findViewById(R.id.no2)
            tvEmpDate = findViewById(R.id.expdate2)
            tvEmpcvv = findViewById(R.id.cvv2)

            btnUpdate = findViewById(R.id.btnUpdate)
            btnDelete = findViewById(R.id.button2)


        }

        private fun setValuesToViews() {

            tvEmpName.text = intent.getStringExtra("empName")
            tvEmpNo.text = intent.getStringExtra("empNumber")

            tvEmpDate.text = intent.getStringExtra("empDate")
            tvEmpcvv.text = intent.getStringExtra("empCVV")

        }

    private fun deleteRecord(
        empID: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("cards").child(empID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this,card_fetch::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}



















