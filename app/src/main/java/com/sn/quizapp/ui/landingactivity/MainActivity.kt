package com.sn.quizapp.ui.landingactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sn.quizapp.R
import com.sn.quizapp.base.archcomponents.SharedVM
import com.sn.quizapp.base.archcomponents.ViewModelFactoryBase
import com.sn.quizapp.ui.gameresult.ResultFragment
import com.sn.quizapp.ui.landing.MainFragment

/**
 * Landing Activity. Holders two fragments.
 * One for game view. Another for details view.
 */
class MainActivity : AppCompatActivity() {

    //Shared View model between Fragments
    lateinit var mSharedVM: SharedVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureFragment()

        configureViewModel()
    }

    //Configures shared VM between two fragments.
    private fun configureViewModel() {
        val array = arrayOf<Any>()
        this.mSharedVM =
            ViewModelProviders.of(this, ViewModelFactoryBase(array)).get(SharedVM::class.java)
    }

    //Setups different fragments.
    private fun configureFragment() {
        when (supportFragmentManager.findFragmentById(R.id.main_frame_id)) {
            null -> replaceFragment(MainFragment.getFragment())
            is MainFragment -> replaceFragment(MainFragment.getFragment())
            is ResultFragment -> replaceFragment(ResultFragment.getFragment())
        }
    }

    //Replaces fragment in container.
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_id, fragment)
            .commit()
    }

    //Close app
    fun exitApp() {
        finishAffinity()
    }
}
