package com.example.smiledoner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        var btnNext = findViewById<Button>(R.id.back)
        btnNext.setOnClickListener {
            val intent = Intent(this,home_new::class.java)
            startActivity(intent)
        }
    }
}