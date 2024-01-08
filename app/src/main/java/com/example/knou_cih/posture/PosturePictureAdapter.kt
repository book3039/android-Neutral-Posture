package com.example.knou_cih.posture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knou_cih.R

//리사이클러뷰를 위한 어댑터
class PosturePictureAdapter(val posturePictureList: ArrayList<PosturePicture>) :
    RecyclerView.Adapter<PosturePictureAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.posture_list_item_pictures, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.postureImage.setImageResource(posturePictureList[position].PostureImage)
        holder.postureName.text = posturePictureList[position].PostureName
        holder.slidePrev.text = posturePictureList[position].SlidePrev
        holder.slideNext.text = posturePictureList[position].SlideNext
    }

    override fun getItemCount(): Int {
        return posturePictureList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postureImage: ImageView = itemView.findViewById(R.id.image_posture_pictures)
        val postureName: TextView = itemView.findViewById(R.id.text_posture_picture_name)
        val slidePrev: TextView = itemView.findViewById(R.id.text_prev_picture)
        val slideNext: TextView = itemView.findViewById(R.id.text_next_picture)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}