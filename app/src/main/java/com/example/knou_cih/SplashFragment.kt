package com.example.knou_cih

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.knou_cih.databinding.CommonFragmentSplashBinding
import com.example.knou_cih.utils.PermissionController

class SplashFragment : Fragment() {

    private var _binding: CommonFragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommonFragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        val textShow = AnimationUtils.loadAnimation(requireContext(), R.anim.splash_text_show)
        val slideShow = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_show_fast)

        binding.splashLayout.startAnimation(slideShow)

        val mHandler = Handler(Looper.getMainLooper())
        val mAction = Runnable {
            binding.titleText.isVisible = true
            binding.titleText.startAnimation(textShow)
            binding.splashLayout.setOnClickListener {
                PermissionController.requestPermissions(activity) // 권한 승인을 위한 메서드

                //모든 권한이 승인 되어야만 HomeFragment로 이동
                if (PermissionController.allPermissionsGranted(activity)) {
                    requireActivity().supportFragmentManager.commit {
                        replace(R.id.fragment_container_main, HomeFragment::class.java, Bundle())
                    }
                }
            }
        }
        mHandler.postDelayed(mAction, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}