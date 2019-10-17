package com.sn.quizapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.sn.quizapp.R
import com.sn.quizapp.base.uihelpers.BaseFragment
import com.sn.quizapp.ui.landingactivity.MainActivity

/**
 * Fragment for Splash screen.
 *
 */
class SplashFragment : BaseFragment() {

    companion object {
        fun getFragment() = SplashFragment()
    }

    val mSplashTimeOut = 2000L

    // OVERRIDE ---
    override fun getLayoutId(): Int = R.layout.splash_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler().postDelayed({
            startHomeActivity()
        }, mSplashTimeOut)
    }

    //Start landing activity
    private fun startHomeActivity() {
        val splashActivity = activity as SplashActivity
        splashActivity.startActivity(
            Intent(
                activity,
                MainActivity::class.java
            )
        )
        splashActivity.finish()
    }
}