package com.example.knou_cih.exercise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.knou_cih.R
import com.example.knou_cih.utils.ARModelController
import com.example.knou_cih.utils.MediaProjectionController
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.utils.setFullScreen

// 운동 동작을 AR로 보여주기 위한 액티비티
class ArActivity : AppCompatActivity() {

    lateinit var arSceneView: ArSceneView
    var arModelController: ARModelController = ARModelController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_activity_main)

        val fileName = intent.getStringExtra("EXERCISE")

        setFullScreen(
            findViewById(R.id.ConstraintLayout_root),
            fullScreen = true,
            hideSystemBars = true,
            fitsSystemWindows = false
        )

        arSceneView = findViewById<ArSceneView>(R.id.ar_scene_view)

        arModelController.run {
            setArSceneView(arSceneView)
            if (fileName != null) {
                loadArModel(arSceneView, fileName)
            }
            cursorOn(arSceneView)
        }

        //AR view 하단에 fragment를 띄움
        supportFragmentManager.commit {
            add(R.id.fragment_container_ar, ArAnchorFragment::class.java, Bundle())
        }
    }

    fun openDialogFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(R.anim.fab_open, R.anim.fragment_slide_down)
            replace(R.id.fragment_container_ar, ArDialogFragment::class.java, Bundle())
        }
        arModelController.cursorOff(arSceneView)
    }

    fun openAnchorFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(R.anim.fragment_slide_up, R.anim.fab_close)
            replace(R.id.fragment_container_ar, ArAnchorFragment::class.java, Bundle())
        }
        arModelController.cursorOn(arSceneView)
    }

    fun openCaptureFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(R.anim.fragment_slide_up, R.anim.alpha_close)
            replace(R.id.fragment_container_ar, ArCaptureFragment::class.java, Bundle())
            arModelController.cursorOff(arSceneView)

        }
    }

    //ArCaptureFragment에서  MediaProjectionController.recordScreen 메서드가 사용되면,
    //액티비티에서 요청을 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MediaProjectionController.mediaScreenCapture -> {
                MediaProjectionController.getMediaProjectionCapture(this, resultCode, data)
            }

            MediaProjectionController.mediaScreenRecord -> {
                MediaProjectionController.getMediaProjectionRecord(this, resultCode, data)
            }
        }
    }
}
