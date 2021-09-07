package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import java.lang.ClassCastException
import androidx.navigation.fragment.NavHostFragment
import coil.annotation.ExperimentalCoilApi
import com.euvic.praktyka_kheller.ui.main.ui.SetSwipeRefresh
import com.google.accompanist.appcompattheme.AppCompatTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

@ExperimentalCoilApi
@ExperimentalAnimationApi
class AllHeroesFragment : Fragment() {

    private lateinit var dataStateListener: DataStateListener
    //private lateinit var viewModel: MainViewModel
    private val compositeDisposable = CompositeDisposable()
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
                AppCompatTheme {
                    SetSwipeRefresh(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        triggerGetHeroesEvent()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        subscribeObservers()
        super.onResume()
    }

    private fun subscribeObservers() {
        viewModel.dataState.subscribe(
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

        viewModel.viewState.observeOn(AndroidSchedulers.mainThread()).subscribe(
            { viewState ->
                viewState.details?.let {
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_allHeroesFragment_to_detailsFragment)
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
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