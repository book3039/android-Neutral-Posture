package com.example.knou_cih.posture

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentSecondBinding

// 자세 설명을 위한 프래그먼트2
class PostureLearnSecondFragment : Fragment() {

    private var _binding: PostureFragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val imageShow =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_idealposture_show)
        val imageShow2 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_idealposture_show2)
        val imageShow3 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_idealposture_show3)
        val textNextShow = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show)
        val textBoxShow =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturegood_show)
        val alphaShow = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_show)

        val mHandler = Handler(Looper.getMainLooper())

        mHandler.postDelayed({
            binding.imageIdealPosture.startAnimation(imageShow)
        }, 500)

        mHandler.postDelayed({
            binding.imageIdealPosture.startAnimation(imageShow2)
        }, 2500)

        mHandler.postDelayed({
            binding.imageIdealPosture.startAnimation(imageShow3)
        }, 7000)

        mHandler.postDelayed({
            binding.textBox.isVisible = true
            binding.textBox.startAnimation(textBoxShow)
            binding.imageIdealPostureCheck.startAnimation(alphaShow)
        }, 8000)

        mHandler.postDelayed({
            binding.textNext.startAnimation(textNextShow)
            binding.textNext.setOnClickListener { viewPager?.currentItem = 2 }
            binding.textNext.isVisible = true
        }, 9500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PostureLearnSecondFragment()
    }
}