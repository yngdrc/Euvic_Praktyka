package com.euvic.praktyka_kheller.ui.main

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.graphics.Color.red
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.remember
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.ui.core.Modifier
import androidx.ui.core.setViewContent
import androidx.ui.graphics.Color
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.fillMaxSize
import androidx.ui.viewinterop.AndroidView
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.details.DetailsFragment
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.ui.setSwipeRefresh
import com.euvic.praktyka_kheller.util.DataState
import com.google.accompanist.appcompattheme.AppCompatTheme
import showDetails

class MainActivity : FragmentActivity(), DataStateListener {

    lateinit var viewModel: MainViewModel
    lateinit var finalHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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

    private fun triggerGetHeroesEvent() {
        viewModel.setStateEvent(MainStateEvent.GetHeroesEvent())
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


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        // clear details
        viewModel.setDetails(null)
        super.onBackPressed()
    }
}