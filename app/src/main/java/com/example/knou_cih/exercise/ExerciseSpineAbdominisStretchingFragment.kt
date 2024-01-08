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

//'[굽은등]상복부스트레칭'을 클릭하면 띄워지는 프래그먼트
class ExerciseSpineAbdominisStretchingFragment : Fragment() {

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
                0, R.drawable.image_abdominis_description1,
                "※굽은등 교정의 원리", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.drawable.image_abdominis_description2,
                "※굽은등으로 수축된 근육", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.abdominis_stretching1, "[동작1]",
                "매트에 가지런히 엎드려,\n 손을 머리 옆에 위치합니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.abdominis_stretching2, "[동작2]",
                "천천히 몸을 일으켜,\n상복부를 스트레칭 합니다.\n" +
                        "이때, 골반은 바닥에 위치하고\n허리는 과하게 접지 않습니다.", "<", ">"
            ),
            Exercise(
                R.raw.abdominis_stretching3, 0, "[전체동작]",
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
            intent.putExtra("EXERCISE", "abdominisstretching")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "abdominisstretching")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
