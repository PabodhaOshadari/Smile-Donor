package com.example.fdonation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fdonation.R
import com.example.fdonation.adapters.UserAdapter
import com.example.fdonation.models.UserModel
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.sql.Ref

class FetchingActivity:AppCompatActivity() {

    private lateinit var userRecycleView:RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        userRecycleView=findViewById(R.id.rvUser)
        userRecycleView.layoutManager=LinearLayoutManager(this)
        userRecycleView.setHasFixedSize(true)
        tvLoadingData=findViewById(R.id.tvLoadingData)

        userList= arrayListOf<UserModel>()

        getUsersData()
    }

    private fun getUsersData(){
        userRecycleView.visibility= View.GONE
        tvLoadingData.visibility=View.VISIBLE

        dbRef=FirebaseDatabase.getInstance().getReference("FoodDonation")

        dbRef.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if(snapshot.exists()){
                    for(userSnap in snapshot.children){
                        val userData=userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter=UserAdapter(userList)
                    userRecycleView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object :UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,UserDetailsActivity::class.java)

                            intent.putExtra("userId",userList[position].userId)
                            intent.putExtra("foodName",userList[position].foodName)
                            intent.putExtra("foodType",userList[position].foodType)
                            intent.putExtra("quantity",userList[position].quantity)
                            intent.putExtra("date",userList[position].date)
                            startActivity(intent)
                        }

                    })

                    userRecycleView.visibility=View.VISIBLE
                    tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}