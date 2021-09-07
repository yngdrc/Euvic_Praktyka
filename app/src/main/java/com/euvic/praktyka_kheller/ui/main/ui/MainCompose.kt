package com.euvic.praktyka_kheller.ui.main.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.R
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

@Composable
fun SearchView(state: DataState<MainViewState>) {
    TextField(
        value = "",
        onValueChange = { value ->
            //state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.black),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
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
        Column {
            SearchView(dataState)
            SwipeRefresh(
                state = dataState.loading,
                onRefresh = { viewModel.setStateEvent(MainStateEvent.GetHeroesEvent) }) {
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
}

