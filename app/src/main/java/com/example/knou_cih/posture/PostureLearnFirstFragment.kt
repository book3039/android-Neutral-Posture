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
import com.example.knou_cih.databinding.PostureFragmentFirstBinding

// 자세 설명을 위한 프래그먼트1
class PostureLearnFirstFragment : Fragment() {

    private var _binding: PostureFragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)


        val textShow = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show)
        val imageShow =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturegood_show)
        val imageShowBad1 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturebad1_show)
        val imageShowBad2 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturebad2_show)
        val imageShowBad3 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturebad3_show)
        val imageShowBad4 =
            AnimationUtils.loadAnimation(requireContext(), R.anim.image_posturebad4_show)
        val mHandler = Handler(Looper.getMainLooper())

        mHandler.postDelayed({
            binding.imagePostureGood.startAnimation(imageShow)
            binding.imagePostureBad1.startAnimation(imageShowBad1)
            binding.imagePostureBad2.startAnimation(imageShowBad2)
            binding.imagePostureBad3.startAnimation(imageShowBad3)
            binding.imagePostureBad4.startAnimation(imageShowBad4)
            binding.textGoodposture.startAnimation(imageShow)
            binding.textGoodposture2.startAnimation(imageShowBad3)
            binding.textBadposture.startAnimation(imageShowBad1)
            binding.textBadposture2.startAnimation(imageShowBad4)

            binding.imagePostureGood.isVisible = true
            binding.imagePostureBad1.isVisible = true
            binding.imagePostureBad2.isVisible = true
            binding.imagePostureBad3.isVisible = true
            binding.imagePostureBad4.isVisible = true
            binding.textGoodposture.isVisible = true
            binding.textGoodposture2.isVisible = true
            binding.textBadposture.isVisible = true
            binding.textBadposture2.isVisible = true
        }, 500)

        mHandler.postDelayed({
            binding.textNext.startAnimation(textShow)
            binding.textNext.isVisible = true
            binding.textNext.setOnClickListener {
                viewPager?.currentItem = 1
            }
        }, 3500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PostureLearnFirstFragment()
    }
}