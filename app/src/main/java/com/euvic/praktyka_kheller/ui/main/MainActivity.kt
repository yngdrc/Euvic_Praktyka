package com.euvic.praktyka_kheller.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.util.DataState

class MainActivity : FragmentActivity(), DataStateListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var finalHost: NavHostFragment
    //public lateinit var heroesDatabase: HeroesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //createHeroesDatabase()
        //triggerGetHeroesEvent()

        val fragmentContainerView = FragmentContainerView(this)
        fragmentContainerView.setBackgroundColor(resources.getColor(R.color.black))
        fragmentContainerView.layoutParams = ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        fragmentContainerView.id = View.generateViewId()
        setContentView(fragmentContainerView)

        finalHost = NavHostFragment.create(R.navigation.main_graph)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerView.id, finalHost)
            .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChanged(dataState)
    }

    private fun handleDataStateChanged(dataState: DataState<*>?) {
        dataState?.let {
            dataState.message?.let { event ->
                event.getContentIfNotHandled()?. let { message ->
                    showToast(message)
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        // clear details
        viewModel.setDetails(null)
        super.onBackPressed()
    }
}