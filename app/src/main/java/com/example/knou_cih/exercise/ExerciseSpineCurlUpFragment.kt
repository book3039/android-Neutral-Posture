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

//'[편평등]컬업'를 클릭하면 띄워지는 프래그먼트
class ExerciseSpineCurlUpFragment : Fragment() {

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
                0, R.drawable.image_curl_up_description1,
                "※편평등 교정의 원리", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.drawable.image_curl_up_description2,
                "※편평등으로 늘어난 근육", "슬라이드>>", "", ""
            ),
            Exercise(
                0, R.raw.curl_up1, "[동작1]",
                "매트에 등을 대고,\n무릎을 세워 편하게 눕습니다.", "<", ">"
            ),
            Exercise(
                0, R.raw.curl_up2, "[동작2]",
                "턱 끝을 당기며,\n머리, 가슴, 명치 순으로\n 천천히 올라옵니다.", "<", ">"
            ),
            Exercise(
                R.raw.curl_up3, 0, "[전체동작]",
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
            intent.putExtra("EXERCISE", "curlup")
            startActivity(intent)
        }
        binding.text3d.setOnClickListener {
            val intent = Intent(activity, ModelActivity::class.java)
            intent.putExtra("EXERCISE", "curlup")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
