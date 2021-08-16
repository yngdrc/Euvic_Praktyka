package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException

class MainFragment : Fragment(), HeroesListAdapter.Interaction {
    lateinit var dataStateListener: DataStateListener
    lateinit var viewModel: MainViewModel
    lateinit var heroesListAdapter: HeroesListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid activity")

        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
//            val topSpacingItemDecoration = TopSpacingItemDecoration(15)
//            addItemDecoration(topSpacingItemDecoration)
            heroesListAdapter = HeroesListAdapter(this@MainFragment)
            adapter = heroesListAdapter
            triggerGetHeroesEvent()
        }
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
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.heroes?.let {
                println("DEBUG: Setting heroes to RecyclerView")
                heroesListAdapter.submitList(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.main_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.action_get -> triggerGetHeroesEvent()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun triggerGetHeroesEvent() {
        viewModel.setStateEvent(MainStateEvent.GetHeroesEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: HeroDetails) {
        println("DEBUG: $position, $item")
    }
}
