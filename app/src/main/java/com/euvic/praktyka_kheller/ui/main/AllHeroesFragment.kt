package com.euvic.praktyka_kheller.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.details.DetailsFragment
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.util.Constants
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.lang.ClassCastException

class AllHeroesFragment : Fragment(), MainRecyclerAdapter.Interaction {

    lateinit var dataStateListener: DataStateListener
    lateinit var viewModel: MainViewModel
    lateinit var heroesListAdapter: MainRecyclerAdapter

    lateinit var lazyColumn: Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                setSwipeRefresh()
//            }
//        }
//    }
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @Composable
    fun setSwipeRefresh(heroesList: List<HeroDetails>) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(true),
            onRefresh = { viewModel.setStateEvent(MainStateEvent.GetHeroesEvent()) },
        ) {
            lazyColumn = LazyColumn {
                    items(10) { index ->
                        setHeroItem(
                            interaction, absoluteAdapterPosition, item,null?: Constants.STEAM_DOTA_IMAGES_URL.plus(
                                item.name?.replace(
                                    Constants.STEAM_DOTA_IMAGES_PREFIX,
                                    ""
                                ).plus(Constants.STEAM_DOTA_IMAGES_RES)
                            ))
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.swipe_refresh.setOnRefreshListener {
            triggerGetHeroesEvent()
        }

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
            heroesListAdapter = MainRecyclerAdapter(this@AllHeroesFragment)
            adapter = heroesListAdapter
            //triggerGetHeroesEvent()
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

                    mainViewState.details?.let {
                        viewModel.setDetails(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.heroes?.let {
                println("DEBUG: Setting heroes to RecyclerView")
                heroesListAdapter.submitList(it)
            }

            viewState.details?.let {
                // println("DEBUG: Setting details to RecyclerView")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailsFragment(), tag)
                    .addToBackStack(tag)
                    .commit()
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

    override fun onItemSelected(position: Int, item: HeroDetails) {
        println("DEBUG: $position, $item")
        item.id?.let { triggerGetHeroDetails(it) }
    }
}
