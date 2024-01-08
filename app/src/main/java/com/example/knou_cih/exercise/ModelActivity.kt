package com.example.knou_cih.exercise

import android.os.Bundle
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.example.knou_cih.R
import com.example.knou_cih.utils.ModelController

// 운동 동작을 3D로 보여주기 위한 액티비티
class ModelActivity : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView
    private var modelController: ModelController = ModelController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.model_activity_main)

        val fileName = intent.getStringExtra("EXERCISE")

        surfaceView = findViewById(R.id.surfaceView)

        modelController.run {
            loadEntity()
            setSurfaceView(surfaceView)
            loadModel(this@ModelActivity, fileName!!)
            loadIndirectLight(this@ModelActivity, "warm_2k")
        }
    }

    override fun onResume() {
        super.onResume()
        modelController.onResume()
    }

    override fun onPause() {
        super.onPause()
        modelController.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        modelController.onDestroy()
    }
}
