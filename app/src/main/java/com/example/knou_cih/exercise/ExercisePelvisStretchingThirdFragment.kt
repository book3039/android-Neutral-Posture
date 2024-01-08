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

//'[전방경사]스트레칭3'을 클릭하면 띄워지는 프래그먼트
class ExercisePelvisStretchingThirdFragment : Fragment() {

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
                0, R.drawable.image_pelvis_stretching3_description,
                "※골반 전방경사와 수축된 근육3", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.pelvis_stretching3_1, "[동작1]",
                "몸의 긴장을 풀고,\n편한 자세로 눕습니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching3_2, "[동작2]",
                "다리를 반대쪽 다리위에 올립니다.\n허벅지 옆 쪽을 늘려주는\n느낌으로 스트레칭 합니다.", "<", ">"
            ),
            Exercise(
                R.raw.pelvis_stretching3_3, 0, "[전체동작]",
                "1회에 10초간,\n3회 반복합니다.\n반대쪽 부위도 스트레칭합니다.", "<", ""
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
            intent.putExtra("EXERCISE", "pelvisstretching3")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "pelvisstretching3")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
