package com.example.knou_cih.posture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentRecyclerViewBinding

// 리사이클러뷰로 대표적인 자세 유형들을 보여주는 프래그먼트
class PostureRecyclerViewFragment : Fragment() {

    private var _binding: PostureFragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PostureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postureList = arrayListOf(
            Posture(R.drawable.image_ideal_posture, "이상적인 자세", "C커브", "완만", "중립", "", ">"),
            Posture(
                R.drawable.image_kyphoticlordotic_posture,
                "굽은등 자세",
                "거북목",
                "굽은등",
                "전방경사",
                "<",
                ">"
            ),
            Posture(R.drawable.image_military_posture, "군인 자세", "일자목", "편평등", "전방경사", "<", ">"),
            Posture(R.drawable.image_swayback_posture, "스웨이 백", "거북목", "굽은등", "후방경사", "<", ">"),
            Posture(R.drawable.image_flatback_posture, "플랫 백", "일자목", "편평등", "후방경사", "<", "")
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = PostureAdapter(postureList)
        binding.recyclerView.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemViewCacheSize(5)

        binding.textHome.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container_posture, PostureHomeFragment::class.java, Bundle())
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
