package com.sn.quizapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sn.quizapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashFragment = supportFragmentManager.findFragmentById(R.id.splash_frame_id) as SplashFragment?

        if(splashFragment == null){
            val newFragment = SplashFragment.getFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.splash_frame_id,newFragment)
                .commit()
        }
    }
}
