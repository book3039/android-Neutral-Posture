package com.example.knou_cih.posture

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentThirdBinding

// 자세 설명을 위한 프래그먼트3
class PostureLearnThirdFragment : Fragment() {

    private var _binding: PostureFragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val alphaShow = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_show)
        val alphaShowLate = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_show_late)
        val textNextShow = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show)

        binding.textNext.setOnClickListener {
            viewPager?.currentItem = 3
        }

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.imageBadPostures.startAnimation(alphaShow)
                binding.textBadPostures.startAnimation(alphaShowLate)
                val mHandler = Handler(Looper.getMainLooper())
                mHandler.postDelayed({
                    binding.textNext.startAnimation(textNextShow)
                }, 3000)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PostureLearnThirdFragment()
    }
}