package com.euvic.praktyka_kheller.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import coil.annotation.ExperimentalCoilApi
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo

@ExperimentalCoilApi
class DetailsFragment : Fragment() {

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        viewModel = activity?.run {
//            ViewModelProvider(this).get(MainViewModel::class.java)
//        }?: throw Exception("Invalid activity")
        return ComposeView(requireContext()).apply {
            setContent {
                ShowDetails(viewModel)
            }
        }
    }
}