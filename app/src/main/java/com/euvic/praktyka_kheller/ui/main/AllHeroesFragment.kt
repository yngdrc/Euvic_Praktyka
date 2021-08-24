package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.details.DetailsFragment
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import java.lang.ClassCastException
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.euvic.praktyka_kheller.ui.main.ui.setHeroItem
import com.euvic.praktyka_kheller.ui.main.ui.setSwipeRefresh
import com.euvic.praktyka_kheller.util.Constants
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.reflect.KProperty

class AllHeroesFragment : Fragment() {

    lateinit var dataStateListener: DataStateListener
    lateinit var viewModel: MainViewModel

    private var heroes: List<HeroDetails>? = null

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
                AppCompatTheme() {
                    setSwipeRefresh(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        triggerGetHeroesEvent()
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

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

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
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
        viewModel.setStateEvent(MainStateEvent.GetHeroesEvent())
    }

    private fun triggerGetHeroDetails(heroID: Int) {
        viewModel.setStateEvent(MainStateEvent.GetDetailsEvent(heroID))
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