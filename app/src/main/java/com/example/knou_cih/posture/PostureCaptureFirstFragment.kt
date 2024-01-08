package com.example.knou_cih.posture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentCaptureFirstBinding

//자세 사진찍기를 위한 프래그먼트1
class PostureCaptureFirstFragment : Fragment() {

    private var _binding: PostureFragmentCaptureFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentCaptureFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textNextShow = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show)

        binding.textCapture.startAnimation(textNextShow)

        binding.textHome.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container_posture, PostureHomeFragment::class.java, Bundle())
            }
            requireActivity().supportFragmentManager.popBackStack()

        }
        binding.textCapture.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(
                    R.id.fragment_container_posture,
                    PostureCaptureSecondFragment::class.java,
                    Bundle()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
