package com.euvic.praktyka_kheller.ui.main.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.ui.theme.HeroItemBg
import com.euvic.praktyka_kheller.ui.theme.HeroNameColor
import com.euvic.praktyka_kheller.ui.theme.Shapes
import com.euvic.praktyka_kheller.util.Constants
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_RES_VERT
import com.euvic.praktyka_kheller.util.DataState
import com.google.accompanist.swiperefresh.SwipeRefresh
import androidx.compose.runtime.rxjava2.subscribeAsState

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun SetHeroItem(item: HeroDataClass, viewModel: MainViewModel, position: Int) {
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
            val imagePainter: ImagePainter = rememberImagePainter(Constants.getHeroImageSrc(item, STEAM_DOTA_IMAGES_RES_VERT))
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
            Text(
                text = item.localized_name,
                color = HeroNameColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp, 10.dp)
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun SetSwipeRefresh(viewModel: MainViewModel) {

    val dataState: DataState<MainViewState> by viewModel.dataState.subscribeAsState(initial = DataState.data(
        null,
        MainViewState(
            null,
            null
        )
    ))
        SwipeRefresh(state = dataState.loading, onRefresh = { viewModel.setStateEvent(MainStateEvent.GetHeroesEvent) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val heroesList: List<HeroDataClass>? = dataState.data?.peekContent()?.heroes
            if (heroesList != null) {
                items(
                    count = heroesList.size
                ) { index ->
                    SetHeroItem(
                        heroesList[index],
                        viewModel,
                        index
                    )
                }
            }
        }
    }
}

