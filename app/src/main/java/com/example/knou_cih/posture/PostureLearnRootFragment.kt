package com.example.knou_cih.posture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.knou_cih.databinding.PostureFragmentRootBinding

//Viewpager2를 사용하여 PostrueFirstFragment에서 PostureSixthFragment까지 연결
class PostureLearnRootFragment : Fragment() {

    private var _binding: PostureFragmentRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        val fragmentList = arrayListOf(
            PostureLearnFirstFragment.newInstance(),
            PostureLearnSecondFragment.newInstance(),
            PostureLearnThirdFragment.newInstance(),
            PostureLearnFourthFragment.newInstance(),
            PostureLearnFifthFragment.newInstance(),
            PostureLearnSixthFragment.newInstance()
        )

        val adapter = PostureViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}