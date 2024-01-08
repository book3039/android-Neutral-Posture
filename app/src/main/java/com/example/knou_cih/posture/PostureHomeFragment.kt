package com.example.knou_cih.posture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.knou_cih.MainActivity
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentHomeBinding

class PostureHomeFragment : Fragment(), View.OnClickListener {

    private var _binding: PostureFragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prevDirectory.setOnClickListener(this)
        binding.postureCapture.setOnClickListener(this)
        binding.postureLearn.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.postureCapture.id -> {
                requireActivity().supportFragmentManager.commit {
                    replace(
                        R.id.fragment_container_posture,
                        PostureCaptureFirstFragment::class.java,
                        Bundle()
                    )
                    addToBackStack(null)
                }
            }

            binding.postureLearn.id -> {
                requireActivity().supportFragmentManager.commit {
                    replace(
                        R.id.fragment_container_posture,
                        PostureLearnRootFragment::class.java,
                        Bundle()
                    )
                    addToBackStack(null)
                }
            }

            binding.prevDirectory.id -> {
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("HomeFragment", "MainHome")
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}