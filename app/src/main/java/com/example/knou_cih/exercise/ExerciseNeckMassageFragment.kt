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

//'[거북목]목마사지'를 클릭하면 띄워지는 프래그먼트
class ExerciseNeckMassageFragment : Fragment() {

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
                R.drawable.image_neck_massage_description1,
                "※거북목 교정의 원리1",
                "슬라이드>>",
                "",
                ""
            ),
            Exercise(
                0,
                R.drawable.image_neck_massage_description2,
                "※거북목으로 인해 뭉치는 근육",
                "슬라이드>>",
                "",
                ""
            ),
            Exercise(
                R.raw.neck_massage1, 0, "[동작1]",
                "고개를 오른쪽으로 돌려,\n왼쪽 목 근육 주변을 풀어줍니다.", "<", ">"
            ),
            Exercise(
                R.raw.neck_massage2, 0, "[동작2]",
                "고개를 왼쪽으로 돌려,\n오른쪽 목 근육 주변을 풀어줍니다.", "<", ">"
            ),
            Exercise(R.raw.neck_massage3, 0, "[전체 동작]", "자유롭게 좌우를 번갈아가며,\n 근육을 풀어줍니다.", "<", "")
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
            intent.putExtra("EXERCISE", "neckmassage")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "neckmassage")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
