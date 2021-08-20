package com.euvic.praktyka_kheller.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.DataStateListener
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.util.Constants

class DetailsFragment : Fragment() {

    lateinit var dataStateListener: DataStateListener
    lateinit var viewModel: MainViewModel

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
                showDetails()
            }
        }
    }

    @Composable
    fun showDetails() {
        val heroDetails: HeroDetails? = viewModel.dataState.value?.data?.peekContent()?.details
        val heroImage: String = null?: Constants.STEAM_DOTA_IMAGES_URL.plus(
        heroDetails?.name?.replace(
            "npc_dota_hero_",
            ""
        ).plus("_vert.jpg"))
        heroDetails?.localized_name?.let {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(heroImage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        //.scale(1.8f, 1.8f)
                        .align(Alignment.CenterHorizontally)
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )

                Spacer(
                    androidx.compose.ui.Modifier
                        .height(10.dp)
                )

                Text(
                    text = heroDetails.localized_name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )

                Spacer(
                    androidx.compose.ui.Modifier
                        .height(20.dp)
                )

                Row() {
                    Image(
                        painter = rememberImagePainter("https://cdn.cloudflare.steamstatic.com/apps/dota2/images/dota_react/herogrid/filter-str-active.png"),
                        contentDescription = null
                    )

                    Spacer(
                        androidx.compose.ui.Modifier
                            .width(10.dp)
                    )

                    heroDetails.primary_attr?.let { it1 ->
                        Text(
                            text = it1,
                            color = Color.White
                        )
                    }
                }

            }
        }
    }

//    fun subscribeObservers() {
//        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
//
//            dataStateListener.onDataStateChange(dataState)
//            dataState.data?.let {  event ->
//                event.getContentIfNotHandled()?.let { mainViewState ->
//                    println("DEBUG: DataState: $dataState")
//                    mainViewState.heroes?.let {
//                        viewModel.setHeroesListData(it)
//                    }
//
//                    mainViewState.details?.let {
//                        viewModel.setDetails(it)
//                    }
//                }
//            }
//        })
//
//        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
//
//            viewState.details?.let {
//                println("DEBUG: Setting details to RecyclerView")
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, DetailsFragment())
//                    .addToBackStack("DetailsFragment")
//                    .commit()
//            }
//        })
//    }
}