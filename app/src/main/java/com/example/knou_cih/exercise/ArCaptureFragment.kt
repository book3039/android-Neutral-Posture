package com.example.knou_cih.exercise

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.knou_cih.R
import com.example.knou_cih.databinding.ArFragmentCaptureBinding
import com.example.knou_cih.utils.MediaProjectionController
import java.util.Timer
import kotlin.concurrent.timer

//AR 모델을 녹화하기 위한 BottomFragment
class ArCaptureFragment : Fragment() {

    private var _binding: ArFragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private lateinit var arActivity: ArActivity
    private var defaultHeight: Int = 0

    var timer: Timer? = null
    var time = 0

    private var isRunning: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arActivity = context as ArActivity

        defaultHeight = arActivity.arSceneView.height //화면 하단을 자르기 전, default 값 저장

        //화면 하단을 자르기 위해 arSceneView의 크기를 조정
        val height =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 695f, resources.displayMetrics)
                .toInt()
        val params = ConstraintLayout.LayoutParams(arActivity.arSceneView.width, height)

        arActivity.arSceneView.layoutParams = params
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArFragmentCaptureBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mHandler = Handler(Looper.getMainLooper())
        val buttonClose = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_close)

        MediaProjectionController.getScreenCapturePermission(requireActivity())

        binding.recordButton.apply {
            setOnClickListener {
                //MediaProjection을 사용한 스크린 녹화 실행
                //수정된 화면 사이즈 정보를 전달하기 위한 매개변수 (arActivity.arSceneView)
                MediaProjectionController.recordScreen(arActivity, arActivity.arSceneView)
                binding.prevButton.isVisible = false
                binding.textPrev.isVisible = false
                binding.timeMinute.isVisible = true
                binding.timeSecond.isVisible = true
                binding.textRecord.text = "녹화종료"
                binding.stopButton.isVisible = true
                binding.recordButton.isVisible = false
                timerStart()
            }
        }
        binding.stopButton.apply {
            setOnClickListener {
                MediaProjectionController.stopRecording(arActivity)
                binding.prevButton.isVisible = true
                binding.textPrev.isVisible = true
                binding.timeMinute.isVisible = false
                binding.timeSecond.isVisible = false
                binding.textRecord.text = "녹화하기"
                binding.stopButton.isVisible = false
                binding.recordButton.isVisible = true
                timerStop()
            }
        }

        binding.prevButton.apply {
            setOnClickListener {
                arActivity.arSceneView.layoutParams.height = defaultHeight
                binding.recordButton.startAnimation(buttonClose)
                binding.textRecord.startAnimation(buttonClose)
                binding.prevButton.startAnimation(buttonClose)
                binding.textPrev.startAnimation(buttonClose)
                binding.timeMinute.isVisible = false
                binding.timeSecond.isVisible = false

                mHandler.postDelayed({
                    arActivity.openDialogFragment()
                }, 500)
            }
        }
    }

    //녹화 시, 녹화 시간을 보여주는 타이머 실행 및 종료를 위한 메서드
    private fun timerStart() {
        isRunning = true
        timer = timer(period = 10) {
            time++

            val second = (time % 6000) / 100
            val minute = time / 6000
            val tenminute = time / 60000

            requireActivity().runOnUiThread {
                if (isRunning) {
                    binding.timeSecond.text = if (second < 10) ":0${second}" else ":${second}"
                    binding.timeMinute.text = "${tenminute}${minute}"
                }
            }
        }
    }

    private fun timerStop() {
        isRunning = false
        timer?.cancel()

        time = 0
        binding.timeSecond.text = ":00"
        binding.timeMinute.text = "00"
    }
}






