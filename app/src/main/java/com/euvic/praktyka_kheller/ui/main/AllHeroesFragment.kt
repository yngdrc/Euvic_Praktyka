package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import java.lang.ClassCastException
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import coil.annotation.ExperimentalCoilApi
import com.euvic.praktyka_kheller.ui.main.ui.SetSwipeRefresh
import com.google.accompanist.appcompattheme.AppCompatTheme

@ExperimentalCoilApi
@ExperimentalAnimationApi
class AllHeroesFragment : Fragment() {

    private lateinit var dataStateListener: DataStateListener
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
                AppCompatTheme {
                    SetSwipeRefresh(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        triggerGetHeroesEvent()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->

            dataStateListener.onDataStateChange(dataState)
            dataState.data?.let {  event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    println("DEBUG: DataState: $dataState")
                    mainViewState.heroes?.let {
                        viewModel.setHeroesListData(it)
                    }

                    mainViewState.details?.let {
                        viewModel.setDetails(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.heroes?.let {
                //println("DEBUG: Setting heroes to RecyclerView")
                //heroesListAdapter.submitList(it)
            }

            viewState.details?.let {
                // println("DEBUG: Setting details to RecyclerView")
                NavHostFragment.findNavController(this).navigate(
                    R.id.action_allHeroesFragment_to_detailsFragment
                    ,null,
                    navOptions { // Use the Kotlin DSL for building NavOptions
                        anim {
                            enter = android.R.animator.fade_in
                            exit = android.R.animator.fade_out
                        }
                    })
            }
        })
    }

    private fun triggerGetHeroesEvent() {
        viewModel.setStateEvent(MainStateEvent.GetHeroesEvent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}