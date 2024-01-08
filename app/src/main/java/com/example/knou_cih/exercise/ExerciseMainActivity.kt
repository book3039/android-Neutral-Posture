package com.example.knou_cih.exercise

import android.os.Bundle
import androidx.fragment.app.commit
import com.example.knou_cih.BaseActivity
import com.example.knou_cih.R


class ExerciseMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.activity_exercise_open, R.anim.none)

        setContentView(R.layout.exercise_activity_main)

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer_exercise, ExerciseHomeFragment::class.java, Bundle())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.none, R.anim.activity_exercise_close)
    }
}