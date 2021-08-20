package com.euvic.praktyka_kheller.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.Modifier
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.fillMaxSize
import androidx.ui.viewinterop.AndroidView
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.util.DataState
import com.google.accompanist.appcompattheme.AppCompatTheme

class MainActivity : FragmentActivity(), DataStateListener {

    lateinit var viewModel: MainViewModel
    lateinit var box: Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme() {
            }
        }
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        // clear details
        viewModel.setDetails(null)
        super.onBackPressed()
    }
}