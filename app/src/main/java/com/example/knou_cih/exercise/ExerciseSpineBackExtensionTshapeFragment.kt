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

//'[굽은등]백익스텐션T'를 클릭하면 띄워지는 프래그먼트
class ExerciseSpineBackExtensionTshapeFragment : Fragment() {

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
                0, R.drawable.image_back_extension_t_shape_description,
                "※굽은등으로 늘어난 근육", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.backextension_t_1, "[동작1]",
                "매트에 엎드려,\n몸을 T자로 만들어 줍니다", "<", ">"
            ),
            Exercise(
                0, R.raw.backextension_t_2, "[동작2]",
                "등과 팔을 바닥에서\n들어올려 줍니다.\n" +
                        "다리도 자연스럽게\n들어 줍니다.", "<", ">"
            ),
            Exercise(
                R.raw.backextension_t_3, 0, "[전체동작]",
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
            intent.putExtra("EXERCISE", "backextensionT")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "backextensionT")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
