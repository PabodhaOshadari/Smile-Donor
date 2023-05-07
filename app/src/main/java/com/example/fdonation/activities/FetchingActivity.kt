package com.example.fdonation.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fdonation.R
import com.example.fdonation.adapters.DonAdapter
import com.example.fdonation.models.DonorModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    private  lateinit var donRecyclerView: RecyclerView
    private lateinit var  tvLoadingData: TextView
    private lateinit var donList: ArrayList<DonorModel>
    private lateinit var dbRef : DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        donRecyclerView = findViewById(R.id.rvdon)
        donRecyclerView.layoutManager = LinearLayoutManager(this)
        donRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        donList = arrayListOf<DonorModel>()

        getDonorsData()
    }

    private fun getDonorsData()
    {
        donRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Donor")

        dbRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                donList.clear()
                if(snapshot.exists())
                {
                    for(donsnap in snapshot.children)
                    {
                        val donData = donsnap.getValue(DonorModel::class.java)
                        donList.add(donData!!)
                    }
                    val mAdapter = DonAdapter(donList)
                    donRecyclerView.adapter= mAdapter

                    mAdapter.setOnItemClickListner(object: DonAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,DonorDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("donId",donList[position].donorId)
                            intent.putExtra("donlocation",donList[position].location)
                            intent.putExtra("dondate",donList[position].date)
                            intent.putExtra("dontime",donList[position].time)
                            intent.putExtra("donphone",donList[position].phone)
                            startActivity(intent)
                        }

                    })

                    donRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}