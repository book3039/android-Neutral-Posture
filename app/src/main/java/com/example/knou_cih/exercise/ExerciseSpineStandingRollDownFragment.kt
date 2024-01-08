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

//'[편평등]스탠딩롤다운'를 클릭하면 띄워지는 프래그먼트
class ExerciseSpineStandingRollDownFragment : Fragment() {

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
            Exercise(0, R.drawable.image_curl_up_description1, "※편평등 교정의 원리", "슬라이드>>", "", ""),
            Exercise(
                0,
                R.drawable.image_standing_roll_down_description,
                "※척추 분절 운동",
                "슬라이드>>",
                "",
                ""
            ),
            Exercise(
                0, R.raw.standing_roll_down1, "[동작1]",
                "두 발을 골반 너비로 벌리고,\n편하게 섭니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down2, "[동작2]",
                "턱 끝을 당기며,\n머리, 가슴, 복부 순으로\n둥글게 말아 내려갑니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down3, "[동작3]",
                "턱 끝을 당기며,\n머리, 가슴, 복부 순으로\n둥글게 말아 내려갑니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down4, "[동작4]",
                "턱 끝을 당기며,\n머리, 가슴, 복부 순으로\n둥글게 말아 내려갑니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down5, "[동작5]",
                "아래에서 부터\n척추를 하나씩 세워\n엉덩이가 뒤로 빠지지 않게\n 천천히 올라옵니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down4, "[동작6]",
                "아래에서 부터\n척추를 하나씩 세워\n엉덩이가 뒤로 빠지지 않게\n 천천히 올라옵니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down3, "[동작7]",
                "아래에서 부터\n척추를 하나씩 세워\n엉덩이가 뒤로 빠지지 않게\n 천천히 올라옵니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down2, "[동작8]",
                "아래에서 부터\n척추를 하나씩 세워\n엉덩이가 뒤로 빠지지 않게\n 천천히 올라옵니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.standing_roll_down1, "[동작9]",
                "", "<", ">"
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
            intent.putExtra("EXERCISE", "standingrolldown")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "standingrolldown")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
