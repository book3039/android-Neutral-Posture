package com.example.knou_cih.exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.knou_cih.R
import com.example.knou_cih.databinding.ArFragmentDialogBinding

//ArAnchorFragment와 ArCaptureFragment 기능을 담고 있는 Dialog
class ArDialogFragment : Fragment(), View.OnClickListener {

    private var _binding: ArFragmentDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var arActivity: ArActivity

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateClockwise: Animation
    private lateinit var rotateCounterClockwise: Animation

    private var isFabOpen = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arActivity = context as ArActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArFragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        rotateClockwise =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fab_rotate_clockwise)
        rotateCounterClockwise = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fab_rotate_countclockwise
        )

        binding.fabRoot.setOnClickListener(this)
        binding.fabAnchor.setOnClickListener(this)
        binding.fabCamera.setOnClickListener(this)
        binding.fabMotion.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.fabRoot.id -> {
                animateFab()
                binding.fabRoot.isSelected = true
            }

            binding.fabAnchor.id -> {
                arActivity.openAnchorFragment()
            }

            binding.fabCamera.id -> {
                arActivity.openCaptureFragment()
            }

            binding.fabMotion.id -> {
                val intent = Intent(activity, ExerciseMainActivity::class.java)
                startActivity(intent)
                arActivity.finish()
            }
        }
    }

    //버튼들의 animation효과를 위한 메서드
    private fun animateFab() {
        if (isFabOpen) {
            binding.fabRoot.startAnimation(rotateCounterClockwise)
            binding.ConstraintLayoutAnchor.startAnimation(fabClose)
            binding.ConstraintLayoutCamera.startAnimation(fabClose)
            binding.ConstraintLayoutMotion.startAnimation(fabClose)
            binding.ConstraintLayoutAnchor.isClickable = false
            binding.ConstraintLayoutCamera.isClickable = false
            binding.ConstraintLayoutMotion.isClickable = false
            isFabOpen = false
        } else {
            binding.fabRoot.startAnimation(rotateClockwise)
            binding.ConstraintLayoutAnchor.startAnimation(fabOpen)
            binding.ConstraintLayoutCamera.startAnimation(fabOpen)
            binding.ConstraintLayoutMotion.startAnimation(fabOpen)
            binding.ConstraintLayoutAnchor.isClickable = true
            binding.ConstraintLayoutCamera.isClickable = true
            binding.ConstraintLayoutMotion.isClickable = true
            isFabOpen = true
        }
    }
}
