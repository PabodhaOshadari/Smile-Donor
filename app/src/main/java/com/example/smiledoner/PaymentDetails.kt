package com.example.smiledoner


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.smiledoner.model.cardModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PaymentDetails : AppCompatActivity() {
    private lateinit var etCardNumber: EditText
    private lateinit var etCardHolderName: EditText
    private lateinit var etMonthYear: EditText
    private lateinit var etCVV: EditText
    private lateinit var btnSave:Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        etCardHolderName = findViewById(R.id.card_name2)
        etCardNumber = findViewById(R.id.card_number2)

        etMonthYear = findViewById(R.id.card_date)
        etCVV = findViewById(R.id.card_cvv)
        btnSave = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("cards")



//val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnSave.setOnClickListener {
            savePaymentDetails()
        }


    }
    private fun savePaymentDetails(){
//getting values
        val holderName = etCardHolderName.text.toString()
        val cardNo = etCardNumber.text.toString()

        val expDate = etMonthYear.text.toString()
        val cvv = etCVV.text.toString()


        if(holderName.isEmpty()){
            etCardHolderName.error = "Please Enter Card Holder Name"
        }
        if(cardNo.isEmpty()){
            etCardNumber.error = "Please Card Number"
        }

        if(expDate.isEmpty()){
            etMonthYear.error = "Please Enter Expire date"
        }

        val payId = dbRef.push().key!!

        val payment = cardModel(payId, holderName,cardNo , expDate,cvv)

        dbRef.child(holderName).setValue(payment)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etCardHolderName.text.clear()
                etCardNumber.text.clear()

                etMonthYear.text.clear()
                etCVV.text.clear()


            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()

            }

        val secondAct = findViewById<Button>(R.id.button6)
        secondAct.setOnClickListener{
            val intent = Intent(this,card_fetch::class.java)
            startActivity(intent)
        }
    }

}


