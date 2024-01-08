package com.example.knou_cih

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import com.example.knou_cih.utils.PermissionController

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_activity_main)

        // 다른 액티비티에서 메인 액티비티로 돌아올 때, HomeFragment를 실행
        val str = intent.getStringExtra("HomeFragment")
        if (str != null) {
            if (str == "MainHome") {
                supportFragmentManager.commit {
                    add(R.id.fragment_container_main, HomeFragment::class.java, Bundle())
                }
            }
        }

        supportFragmentManager.commit {
            add(R.id.fragment_container_main, SplashFragment::class.java, Bundle())
        }

        //MediaProjection 사용 시 필요한 ForegroundService
        val serviceIntent = Intent(this, MainService::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionController.REQUEST_CODE_PERMISSIONS) {
            if (!PermissionController.allPermissionsGranted(this)) {
                Toast.makeText(
                    this,
                    "서비스 이용을 위해서 모든 권한을 승인해 주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(this, MainService::class.java)
        stopService(serviceIntent)
    }
}

