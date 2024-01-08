package com.example.knou_cih.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knou_cih.R

//운동 동작 설명시 사용되는 리사이클러뷰를 위한 어댑터
class ExerciseAdapter(val exerciseList: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_description_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        //움직이는 이미지인 gif를 리사이클러뷰에서 사용하기 위한 메서드
        holder.itemView.context
        Glide.with(holder.itemView.context).load(exerciseList[position].ExerciseImageGif)
            .into(holder.exerciseImageGif)
        holder.exerciseImageGif.setImageResource(exerciseList[position].ExerciseImageGif)

        holder.exerciseImage.setImageResource(exerciseList[position].ExerciseImage)
        holder.exerciseScriptFirst.text = exerciseList[position].ExerciseScriptFirst
        holder.exerciseScriptSecond.text = exerciseList[position].ExerciseScriptSecond
        holder.slidePrev.text = exerciseList[position].SlidePrev
        holder.slideNext.text = exerciseList[position].SlideNext
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseImageGif: ImageView = itemView.findViewById(R.id.image_exercise_item_gif)
        val exerciseImage: ImageView = itemView.findViewById(R.id.image_exercise_item)
        val exerciseScriptFirst: TextView = itemView.findViewById(R.id.text_exercise_script)
        val exerciseScriptSecond: TextView = itemView.findViewById(R.id.text_exercise_script2)
        val slidePrev: TextView = itemView.findViewById(R.id.text_prev_exercise)
        val slideNext: TextView = itemView.findViewById(R.id.text_next_exercise)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}