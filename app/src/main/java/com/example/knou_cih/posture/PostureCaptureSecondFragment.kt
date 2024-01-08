package com.example.knou_cih.posture

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.knou_cih.R
import com.example.knou_cih.databinding.PostureFragmentCaptureSecondBinding
import com.example.knou_cih.utils.MediaProjectionController
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//자세 사진찍기를 위한 프래그먼트2
class PostureCaptureSecondFragment : Fragment() {

    private var _binding: PostureFragmentCaptureSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService //후면 카메라 기능을 사용하기 위해 선언
    private var imageCapture: ImageCapture? = null //후면 카메라로 촬용한 사진을 저장하기 위해 선언

    private lateinit var adapter: PosturePictureAdapter //리사이클러뷰 어댑터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostureFragmentCaptureSecondBinding.inflate(inflater, container, false)

        //카메라 기능 사용 시, 화면 상단의 status bar와 하단의 navigation bar를 숨김
        requireActivity().window.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insetsController?.hide(WindowInsets.Type.statusBars())
            } else {
                setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if (Build.VERSION.SDK_INT >= 30) {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //MediaProjection 기능을 사용하기 위한 권한 승인
        MediaProjectionController.getScreenCapturePermission(requireActivity())

        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera() // xml의 카메라 프리뷰와 후면 카메라를 연결

        val mHandler = Handler(Looper.getMainLooper())
        val capturebuttonClose =
            AnimationUtils.loadAnimation(requireContext(), R.anim.capturebutton_close)
        val capturebuttonOpen =
            AnimationUtils.loadAnimation(requireContext(), R.anim.capturebutton_open)
        val textShow = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show)

        binding.buttonCapture.setOnClickListener {
            binding.buttonCapture.startAnimation(capturebuttonClose)
            binding.textCapture.startAnimation(capturebuttonClose)

            mHandler.postDelayed({
                takePhoto() // 후면 카메라로 사진을 찍음

                // 사용자 화면을 캡쳐서 bitmap을 ImageView에 띄움
                MediaProjectionController.captureScreen(requireActivity()) { bitmap ->
                    run {
                        mHandler.postDelayed({
                            binding.imageSelfCaptured.setImageBitmap(bitmap)
                        }, 100)
                    }
                }
            }, 110)

            //사진이 촬영되면, gone 또는 invisible로 되어있던 view들을 띄우고, 캡쳐를 위한 view를 숨김
            mHandler.postDelayed({
                binding.cameraPreview.isVisible = false
                binding.buttonCapture.isVisible = false
                binding.buttonCapture.isClickable = false
                binding.buttonCapture.isFocusable = false
                binding.textCapture.isVisible = false
                binding.captureGuideline.isVisible = false

                binding.imageSelfCaptured.isVisible = true
                binding.textBox.isVisible = true
                binding.recyclerviewPictures.isVisible = true
                binding.includeToolbar.toolBar.isVisible = true

                binding.textRepeat.startAnimation(textShow)
                binding.textHome.startAnimation(textShow)
            }, 300)
        }

        //다시 사진 찍을 때, 다시 카메라 프리뷰를 위한 view를 띄움
        binding.textRepeat.setOnClickListener {
            binding.cameraPreview.isVisible = true
            binding.buttonCapture.isVisible = true
            binding.buttonCapture.isClickable = true
            binding.buttonCapture.isFocusable = true
            binding.textCapture.isVisible = true
            binding.captureGuideline.isVisible = true

            binding.imageSelfCaptured.isVisible = false
            binding.textBox.isVisible = false
            binding.recyclerviewPictures.isVisible = false

            binding.includeToolbar.toolBar.isVisible = false

            binding.buttonCapture.startAnimation(capturebuttonOpen)
            binding.textCapture.startAnimation(capturebuttonOpen)
        }

        binding.textHome.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container_posture, PostureHomeFragment::class.java, Bundle())
            }
            requireActivity().supportFragmentManager.popBackStack()
        }

        val posturePictureList = arrayListOf(
            PosturePicture(R.drawable.image_ideal_posture, "이상적인 자세", "", ">"),
            PosturePicture(R.drawable.image_kyphoticlordotic_posture, "굽은등 자세", "<", ">"),
            PosturePicture(R.drawable.image_military_posture, "군인 자세", "<", ">"),
            PosturePicture(R.drawable.image_swayback_posture, "스웨이 백", "<", ">"),
            PosturePicture(R.drawable.image_flatback_posture, "플랫 백", "<", "")
        )
        binding.recyclerviewPictures.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = PosturePictureAdapter(posturePictureList)
        binding.recyclerviewPictures.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewPictures)
        binding.recyclerviewPictures.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cameraExecutor.shutdown()
        // 숨겼던 status bar와 navigation bar를 다시 보여줌
        requireActivity().window.apply {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insetsController?.show(WindowInsets.Type.statusBars())
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        if (Build.VERSION.SDK_INT >= 30) {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        }
    }

    //후면 카메라 실행을 위한 메서드
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    //후면 카메라를 사용하여 사진을 촬영 및 저장하는 메서드
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREAN)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "MyPosture-${name}")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyPosture-")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    Log.e("MakeYourPOSTUREneutral", "촬영 실패: ${e.message}", e)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(requireContext(), "촬영 완료! 사진이 저장되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("MakeYourPOSTUREneutral", "촬영 완료! 사진이 저장되었습니다.")
                }
            }
        )
    }
}
