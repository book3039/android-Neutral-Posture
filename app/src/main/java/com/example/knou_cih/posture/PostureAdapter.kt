package com.example.knou_cih.posture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knou_cih.R

//리사이클러뷰를 위한 어댑터
class PostureAdapter(val postureList: ArrayList<Posture>) :
    RecyclerView.Adapter<PostureAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.posture_list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.postureImage.setImageResource(postureList[position].PostureImage)
        holder.postureName.text = postureList[position].PostureName
        holder.postureNeck.text = postureList[position].PostureNeck
        holder.postureSpine.text = postureList[position].PostureSpine
        holder.posturePelvis.text = postureList[position].PosturePelvis
        holder.slidePrev.text = postureList[position].SlidePrev
        holder.slideNext.text = postureList[position].SlideNext
    }

    override fun getItemCount(): Int {
        return postureList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postureImage: ImageView = itemView.findViewById(R.id.image_posture_item)
        val postureName: TextView = itemView.findViewById(R.id.text_posture_name)
        val postureNeck: TextView = itemView.findViewById(R.id.text_posture_neck)
        val postureSpine: TextView = itemView.findViewById(R.id.text_posture_spine)
        val posturePelvis: TextView = itemView.findViewById(R.id.text_posture_pelvis)
        val slidePrev: TextView = itemView.findViewById(R.id.text_prev)
        val slideNext: TextView = itemView.findViewById(R.id.text_next)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}