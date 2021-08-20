package com.euvic.praktyka_kheller.ui.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.theme.HeroItemBg
import com.euvic.praktyka_kheller.ui.theme.HeroNameColor
import com.euvic.praktyka_kheller.ui.theme.Shapes
import com.euvic.praktyka_kheller.util.Constants
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
fun setHeroItem(item: HeroDetails, heroImage: String?, viewModel: MainViewModel) {
    Surface(
        shape = Shapes.medium,
        modifier = Modifier
            .clickable(onClick = {
                item.id?.let {
                    MainStateEvent.GetDetailsEvent(
                        it-1
                    )
                }?.let { viewModel.setStateEvent(it) }
            })
            .padding(16.dp, 4.dp, 16.dp, 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                //.border(2.dp, Color.Black, RoundedCornerShape(8.dp)
                .background(HeroItemBg)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(heroImage),
                contentDescription = null,
                modifier = Modifier
                    .size(58.dp)
                    //.clip(RoundedCornerShape(8.dp))
                    .scale(1.8f, 1.8f)
                    //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    .align(Alignment.Bottom)
            )
            Spacer(
                Modifier
                    .width(32.dp)
            )
            item.localized_name?.let {
                Text(
                    text = it,
                    color = HeroNameColor,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun setSwipeRefresh(viewModel: MainViewModel) {
    val dataExample = viewModel.dataState.observeAsState()
    dataExample.value?.let {
        viewModel.dataState.value?.let { it1 ->
            SwipeRefresh(state = it1.loading, onRefresh = { viewModel.setStateEvent(MainStateEvent.GetHeroesEvent()) }) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    dataExample.value!!.data?.peekContent()?.heroes?.let { it2 ->
                        items(
                            count = it2.size
                        ) { index ->
                            dataExample.value?.let {
                                val heroesList = dataExample.value?.data?.peekContent()?.heroes
                                heroesList?.get(index)?.let { it1 ->
                                    setHeroItem(
                                        it1,
                                        null ?: Constants.STEAM_DOTA_IMAGES_URL.plus(
                                            it1.name?.replace(
                                                Constants.STEAM_DOTA_IMAGES_PREFIX,
                                                ""
                                            ).plus(Constants.STEAM_DOTA_IMAGES_RES)
                                        ), viewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}