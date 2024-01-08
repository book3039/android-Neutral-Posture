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

//'[전방경사]스트레칭2'를 클릭하면 띄워지는 프래그먼트
class ExercisePelvisStretchingSecondFragment : Fragment() {

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
                0, R.drawable.image_pelvis_stretching2_description,
                "※골반 전방경사와 수축된 근육2", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.pelvis_stretching2_1, "[동작1]",
                "한쪽 다리를 앞으로,\n반대쪽 다리는\n뒤로 적당히 벌립니다.\n무릎은 땅에 대셔도 괜찮습니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching2_2, "[동작2]",
                "앞으로     내민 다리의\n반대쪽     손을 높이 들어줍니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.pelvis_stretching2_3, "[동작3]",
                "천천히 팔을 기울여, 몸의 안쪽 근육을\n늘려주는 느낌으로, 스트레칭 합니다.", "<", ">"
            ),
            Exercise(
                R.raw.pelvis_stretching2_4, 0, "[전체동작]",
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
            intent.putExtra("EXERCISE", "pelvisstretching2")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "pelvisstretching2")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
