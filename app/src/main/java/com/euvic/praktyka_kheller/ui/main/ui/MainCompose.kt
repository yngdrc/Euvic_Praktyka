package com.euvic.praktyka_kheller.ui.main.ui

import android.util.Log
import androidx.animation.MutableTransitionState
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.theme.HeroItemBg
import com.euvic.praktyka_kheller.ui.theme.HeroNameColor
import com.euvic.praktyka_kheller.ui.theme.Shapes
import com.euvic.praktyka_kheller.util.Constants
import com.google.accompanist.swiperefresh.SwipeRefresh

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun setHeroItem(item: HeroDetails, viewModel: MainViewModel, position: Int) {
    Surface(
        shape = Shapes.medium,
        modifier = Modifier
            .clickable(onClick = {
                viewModel.setStateEvent(MainStateEvent.GetDetailsEvent(position))
            })
            .padding(16.dp, 4.dp, 16.dp, 4.dp)
            .fillMaxWidth()
            .clipToBounds()
    ) {
        Row(
            Modifier
                //.border(2.dp, Color.Black, RoundedCornerShape(8.dp)
                .background(HeroItemBg)
                .padding(10.dp, 10.dp, 20.dp, 10.dp)
        ) {
            var imagePainter: ImagePainter = rememberImagePainter(Constants.getHeroImageSrc(item, "_vert.jpg"))
            if (imagePainter.state.painter == null) {
                imagePainter = rememberImagePainter(Constants.getHeroImageSrc(item, "_lg.png"))
            }
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .scale(1.2f, 1.2f)
                    //.padding(5.dp, 0.dp, 0.dp, 0.dp)
                    //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
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

@ExperimentalCoilApi
@ExperimentalAnimationApi
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
                                        viewModel,
                                        index
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