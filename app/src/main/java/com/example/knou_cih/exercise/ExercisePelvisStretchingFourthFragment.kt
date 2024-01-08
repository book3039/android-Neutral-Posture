package com.example.knou_cih.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.knou_cih.R
import com.example.knou_cih.databinding.ExerciseFragmentRecyclerViewBinding

//'[후방경사]스트레칭'을 클릭하면 띄워지는 프래그먼트
class ExercisePelvisStretchingFourthFragment : Fragment() {

    private var _binding: ExerciseFragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseFragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseList = arrayListOf(
            Exercise(
                0, R.drawable.image_pelvis_stretching4_description,
                "※골반 후방경사와 수축된 근육", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.pelvis_stretching4_1, "[동작1]",
                "편한 자세로 눕습니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching4_2, "[동작2]",
                "한쪽 다리를 들어올려,\n양손으로 무릎 뒤쪽을 잡습니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching4_3, "[동작3]",
                "발 끝을 몸쪽으로 당겨줍니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching4_4, "[동작4]",
                "천천히 다리를\n들어올리며 펴줍니다.", "<", ">"
            ),
            Exercise(
                R.raw.pelvis_stretching4_5, 0, "[전체동작]",
                "1회에 10초간 유지하고,\n3회 반복합니다.", "<", ""
            )
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExerciseAdapter(exerciseList)
        binding.recyclerView.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.setHasFixedSize(true)

        binding.textAr.setOnClickListener {
            val intent = Intent(activity, ArActivity::class.java)
            intent.putExtra("EXERCISE", "pelvisstretching4")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "pelvisstretching4")
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
