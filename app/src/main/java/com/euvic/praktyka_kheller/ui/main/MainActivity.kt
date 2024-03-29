package com.euvic.praktyka_kheller.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.util.DataState

class MainActivity : FragmentActivity(), DataStateListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var finalHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // creates MainViewModel
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // sets up the fragment container
        val fragmentContainerView = FragmentContainerView(this)
        fragmentContainerView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        fragmentContainerView.layoutParams = ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        fragmentContainerView.id = View.generateViewId()
        setContentView(fragmentContainerView)

        // sets the navigation host fragment to fragment container
        finalHost = NavHostFragment.create(R.navigation.main_graph)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerView.id, finalHost)
            .setPrimaryNavigationFragment(finalHost)
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