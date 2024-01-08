package com.example.knou_cih

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.knou_cih.databinding.CommonFragmentHomeBinding
import com.example.knou_cih.exercise.ExerciseMainActivity
import com.example.knou_cih.posture.PostureMainActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: CommonFragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommonFragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iconPosture.setOnClickListener(this)
        binding.iconExercise.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.iconPosture.id -> {
                val intent = Intent(activity, PostureMainActivity::class.java)
                startActivity(intent)
            }

            binding.iconExercise.id -> {
                val intent = Intent(activity, ExerciseMainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}