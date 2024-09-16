package com.example.fdonation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fdonation.R
import com.example.fdonation.models.DonorModel


class DonAdapter(private val donList:ArrayList<DonorModel>):
    RecyclerView.Adapter<DonAdapter.ViewHolder> (){

     private lateinit var mListner: onItemClickListner
    interface onItemClickListner{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner)
    {
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.don_list_item , parent, false)
        return ViewHolder(itemView,mListner)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentDon = donList[position] //give current donor
        holder.tvDonName.text = currentDon.location //this part confusion
    }



    override fun getItemCount(): Int {
        return donList.size
    }

    class ViewHolder (itemView: View,clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
       val tvDonName : TextView = itemView.findViewById(R.id.tvDonName)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }

}
