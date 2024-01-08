package com.example.knou_cih.exercise

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.knou_cih.databinding.ArFragmentAnchorBinding
import me.tankery.lib.circularseekbar.CircularSeekBar

//AR 모델의 크기, 회전, 위치를 조정하기 위한 BottomFragment
class ArAnchorFragment : Fragment() {

    private var _binding: ArFragmentAnchorBinding? = null
    private val binding get() = _binding!!

    private lateinit var arActivity: ArActivity

    var isCursorOn = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arActivity = context as ArActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArFragmentAnchorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //AR 모델을 커서 위에 내려놓기 위한(anchor) 버튼
        binding.anchorButton.apply {
            setOnClickListener { arActivity.arModelController.placeArModel(arActivity.arSceneView) }
        }

        //현재 프래그먼트를 종료하고 DialogFragment를 띄움
        binding.closeButton.apply {
            setOnClickListener {
                arActivity.openDialogFragment()
            }
        }

        binding.sizeUpButton.apply {
            setOnClickListener { arActivity.arModelController.modelSizeUp() }
            var mHandler: Handler? = null
            val mAction = object : Runnable {
                override fun run() {
                    arActivity.arModelController.modelSizeUp()
                    mHandler?.postDelayed(this, 5)
                }
            }
            // 사용자가 버튼을 계속 누르고 있을 때 연속적으로 modelSizeUp 액션 실행
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return@setOnTouchListener true
                        mHandler = Handler(Looper.getMainLooper())
                        mHandler?.postDelayed(mAction, 5)
                    }

                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return@setOnTouchListener true
                        mHandler?.removeCallbacks(mAction)
                        mHandler = null
                    }
                }
                false
            }
        }

        binding.sizeDownButton.apply {
            setOnClickListener { arActivity.arModelController.modelSizeDown() }
            var mHandler: Handler? = null
            val mAction = object : Runnable {
                override fun run() {
                    arActivity.arModelController.modelSizeDown()
                    mHandler?.postDelayed(this, 10)
                }
            }
            // 사용자가 버튼을 계속 누르고 있을 때 연속적으로 modelSizeDown 액션 실행
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return@setOnTouchListener true
                        mHandler = Handler(Looper.getMainLooper())
                        mHandler?.postDelayed(mAction, 10)
                    }

                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return@setOnTouchListener true
                        mHandler?.removeCallbacks(mAction)
                        mHandler = null
                    }
                }
                false
            }
        }

        //AR 모델의 회전을 위한 원형 SeekBar
        binding.rotateBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                arActivity.arModelController.rotateModel(progress)
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
            }
        })

        // AR 모델의 위치를 조정하기 위한 Cursor
        binding.cursorButton.apply {
            setOnClickListener {
                if (isCursorOn) {
                    arActivity.arModelController.cursorOff(arActivity.arSceneView)
                    binding.textCursor.text = "커서OFF"
                    binding.cursorButton.isSelected = true
                    isCursorOn = false
                } else {
                    arActivity.arModelController.cursorOn(arActivity.arSceneView)
                    binding.textCursor.text = "커서ON"
                    binding.cursorButton.isSelected = false
                    isCursorOn = true
                }
            }
        }

    }
}




