package com.example.knou_cih.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knou_cih.R

//척추 운동 아이콘들을 보여주는 리사이클러뷰를 위한 어댑터
class ExerciseSpineItemAdapter(val exerciseMenuList: ArrayList<ExerciseMenu>) :
    RecyclerView.Adapter<ExerciseSpineItemAdapter.CustomViewHolder>() {

    interface OnItemClickListener {
        fun onSpineItemClick(pos: Int)
    }

    lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_menu_item, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                mListener.onSpineItemClick(curPos)
            }
        }
    }

    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int
    ) {
        holder.exerciseMenuImage.setImageResource(exerciseMenuList[position].ExerciseMenu)
        holder.exerciseMenuName.text = exerciseMenuList[position].ExerciseMenuName
    }

    override fun getItemCount(): Int {
        return exerciseMenuList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseMenuImage: ImageView = itemView.findViewById(R.id.img_exercise_menu_item)
        val exerciseMenuName: TextView = itemView.findViewById(R.id.text_exercise_menu_item)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}