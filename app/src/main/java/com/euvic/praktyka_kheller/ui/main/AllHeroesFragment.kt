package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
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
//import com.euvic.praktyka_kheller.ui.main.ui.SetSwipeRefresh
import com.google.accompanist.appcompattheme.AppCompatTheme
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.IOException

@ExperimentalCoilApi
@ExperimentalAnimationApi
class AllHeroesFragment : Fragment() {

    private lateinit var dataStateListener: DataStateListener
    private lateinit var viewModel: MainViewModel
    val compositeDisposable = CompositeDisposable()

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
        //triggerGetHeroesEvent()
    }

    override fun onResume() {
        subscribeObservers()
        super.onResume()
    }

    private fun subscribeObservers() {
        viewModel.dataState.subscribeOn(Schedulers.io()).subscribe(
            {
                dataStateListener.onDataStateChange(it)
                it.data?.let { event ->
                    event.getContentIfNotHandled()?.let { mainViewState ->
                        mainViewState.heroes?.let { it ->
                            viewModel.setHeroesListData(it)
                        }

                        mainViewState.details?.let { it ->
                            viewModel.setDetails(it)
                        }
                    }
                }
            },
            {
               Log.d("Error", it.toString())
            }
        ).addTo(compositeDisposable)

        viewModel.viewState.subscribeOn(Schedulers.io()).subscribe(
            { viewState ->
                Log.d("VS", viewState.toString())
                viewState.details?.let {
                    Log.d("AHF", "details")
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
            },
            {
                Log.d("Error", it.toString())
            }
        ).addTo(compositeDisposable)
    }

    private fun triggerGetHeroesEvent() {
        viewModel.setStateEvent(MainStateEvent.GetHeroesEvent)
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
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