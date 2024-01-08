package com.example.knou_cih.posture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentSixthBinding

// 자세 설명을 위한 프래그먼트6
class PostureLearnSixthFragment : Fragment() {

    private var _binding: PostureFragmentSixthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentSixthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.textPrev.setOnClickListener {
            viewPager?.currentItem = 4
        }
        binding.textNext.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(
                    R.id.fragment_container_posture,
                    PostureRecyclerViewFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PostureLearnSixthFragment()
    }
}