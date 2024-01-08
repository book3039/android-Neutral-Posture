package com.example.knou_cih.posture

import android.content.Intent
import android.os.Bundle
import com.example.knou_cih.BaseActivity
import com.example.knou_cih.R
import com.example.knou_cih.utils.MediaProjectionController

class PostureMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.activity_posture_estimation_open, R.anim.none)
        setContentView(R.layout.posture_activity_main)
    }

    //PostureCaptureSecondFragment에서  MediaProjectionController.captureScreen 메서드가 사용되면,
    //액티비티에서 요청을 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MediaProjectionController.getMediaProjectionCapture(this, resultCode, data)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.none, R.anim.activity_posture_estimation_close)
    }
}