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

//'[거북목]턱당기기'를 클릭하면 띄워지는 프래그먼트
class ExerciseNeckStretchingFirstFragment : Fragment() {

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
                0,
                R.drawable.image_neck_stretching1_description1,
                "※거북목 교정의 원리2",
                "슬라이드>>",
                "",
                ""
            ),
            Exercise(
                0,
                R.drawable.image_neck_stretching1_description2,
                "※거북목으로 늘어난 근육",
                "슬라이드>>",
                "",
                ""
            ),
            Exercise(
                R.raw.neck_stretching1, 0, "[동작]",
                "편한 자세로 누워, 목에 긴장을 풀고\n 턱을 몸쪽으로 당겨줍니다.\n 당긴 상태로 10초정도 유지하고\n 3회 반복합니다.",
                "<", ""
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
            intent.putExtra("EXERCISE", "neckstretching1")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "neckstretching1")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
