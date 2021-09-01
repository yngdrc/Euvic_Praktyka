package com.euvic.praktyka_kheller.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.main.MainViewModel

@ExperimentalCoilApi
class DetailsFragment : Fragment() {

    lateinit var dataStateListener: DataStateListener
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid activity")
        return ComposeView(requireContext()).apply {
            setContent {
                showDetails(viewModel)
            }
        }
    }
}