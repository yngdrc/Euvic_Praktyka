package com.euvic.praktyka_kheller.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.Constants
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_PREFIX
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_RES_VERT
import com.euvic.praktyka_kheller.util.DataState
import com.euvic.praktyka_kheller.util.DotaImageResourcesUrls

const val ICON_SIZE: Float = 1f
const val ATTR_ICON_SIZE: Float = 2f

@Composable
fun AddSpacer(height: Dp = 0.dp, width: Dp = 0.dp) {
    Spacer(
        Modifier
            .height(height)
            .width(width)
    )
}

@Composable
fun AttrContainer(heroDetails: HeroDataClass) {
    Row {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ATTRIBUTE_STRENGTH),
                contentDescription = null,
                modifier = Modifier
                    .scale(ATTR_ICON_SIZE, ATTR_ICON_SIZE)
            )
            AddSpacer(height = 10.dp, width = 0.dp)
            Text(
                text = heroDetails.base_str.toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "+ ${heroDetails.str_gain}",
                color = Color.LightGray,
                textAlign = TextAlign.Right
            )
        }
        AddSpacer(0.dp, 20.dp)
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ATTRIBUTE_AGILITY),
                contentDescription = null,
                modifier = Modifier
                    .scale(ATTR_ICON_SIZE, ATTR_ICON_SIZE)
            )
            AddSpacer(height = 10.dp, width = 0.dp)
            Text(
                text = heroDetails.base_agi.toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "+ ${heroDetails.agi_gain}",
                color = Color.LightGray,
                textAlign = TextAlign.Right
            )
        }
        AddSpacer(0.dp, 20.dp)
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ATTRIBUTE_INTELLIGENCE),
                contentDescription = null,
                modifier = Modifier
                    .scale(ATTR_ICON_SIZE, ATTR_ICON_SIZE)
            )
            AddSpacer(height = 10.dp, width = 0.dp)
            Text(
                text = heroDetails.base_int.toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "+ ${heroDetails.int_gain}",
                color = Color.LightGray,
                textAlign = TextAlign.Right
            )
        }
    }
}

@Composable
fun AttackStatsContainer(heroDetails: HeroDataClass) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "ATTACK", color = Color.LightGray)
            AddSpacer(5.dp, 0.dp)
            Row() {
                Image(
                    painter = rememberImagePainter(DotaImageResourcesUrls.ICON_DAMAGE),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(ICON_SIZE, ICON_SIZE)
                )
                AddSpacer(0.dp, 5.dp)
                Text(text = "${heroDetails.base_attack_min} - ${heroDetails.base_attack_max}", color = Color.White)
            }
            AddSpacer(5.dp, 0.dp)
            Row() {
                Image(
                    painter = rememberImagePainter(DotaImageResourcesUrls.ICON_ATTACK_TIME),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(ICON_SIZE, ICON_SIZE)
                )
                AddSpacer(0.dp, 5.dp)
                Text(text = "${heroDetails.attack_rate}", color = Color.White)
            }
            AddSpacer(5.dp, 0.dp)
            Row() {
                Image(
                    painter = rememberImagePainter(DotaImageResourcesUrls.ICON_ATTACK_RANGE),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(ICON_SIZE, ICON_SIZE)
                )
                AddSpacer(0.dp, 5.dp)
                Text(text = "${heroDetails.attack_range}", color = Color.White)
            }
        }
}

@Composable
fun DefenseStatsContainer(heroDetails: HeroDataClass) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "DEFENSE", color = Color.LightGray)
        AddSpacer(5.dp, 0.dp)
        Row {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ICON_ARMOR),
                contentDescription = null,
                modifier = Modifier
                    .scale(ICON_SIZE, ICON_SIZE)
            )
            AddSpacer(0.dp, 5.dp)
            Text(text = "${heroDetails.base_armor}", color = Color.White)
        }
        AddSpacer(5.dp, 0.dp)
        Row() {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ICON_MAGIC_RESIST),
                contentDescription = null,
                modifier = Modifier
                    .scale(ICON_SIZE, ICON_SIZE)
            )
            AddSpacer(0.dp, 5.dp)
            Text(text = "${heroDetails.base_mr}%", color = Color.White)
        }
    }
}

@Composable
fun MobilityStatsContainer(heroDetails: HeroDataClass) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "MOBILITY", color = Color.LightGray)
        AddSpacer(5.dp, 0.dp)
        Row() {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ICON_MOVEMENT_SPEED),
                contentDescription = null,
                modifier = Modifier
                    .scale(ICON_SIZE, ICON_SIZE)
            )
            AddSpacer(0.dp, 5.dp)
            Text(text = "${heroDetails.move_speed}", color = Color.White)
        }
        AddSpacer(5.dp, 0.dp)
        Row() {
            Image(
                painter = rememberImagePainter(DotaImageResourcesUrls.ICON_TURN_RATE),
                contentDescription = null,
                modifier = Modifier
                    .scale(ICON_SIZE, ICON_SIZE)
            )
            AddSpacer(0.dp, 5.dp)
            Text(text = "${heroDetails.turn_rate}", color = Color.White)
        }
    }
}

@Composable
fun HeroInfo(heroImage: String, heroDetails: HeroDataClass) {
    Image(
        painter = rememberImagePainter(heroImage),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .scale(1.2f, 1.2f)
            //.align(Alignment.CenterHorizontally)
            .border(13.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )
    AddSpacer(height = 10.dp, width = 0.dp)
    Text(
        text = heroDetails.localized_name,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}

@ExperimentalCoilApi
@Composable
fun ShowDetails(viewModel: MainViewModel) {
    val dataState: DataState<MainViewState> by viewModel.dataState.subscribeAsState(initial = DataState.data(
        null,
        MainViewState(
            null,
            null
        )
    ))

    val heroDetails: HeroDataClass? = dataState.data?.peekContent()?.details
    val heroImage: String = null?: Constants.STEAM_DOTA_IMAGES_URL.plus(
        heroDetails?.name?.replace(
            STEAM_DOTA_IMAGES_PREFIX,
            ""
        ).plus(STEAM_DOTA_IMAGES_RES_VERT))
    heroDetails?.localized_name?.let {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeroInfo(heroImage, heroDetails)
            AddSpacer(height = 20.dp, width = 0.dp)
            AttrContainer(heroDetails)
            AddSpacer(30.dp, 0.dp)
            Row {
                AttackStatsContainer(heroDetails = heroDetails)
                AddSpacer(0.dp, 30.dp)
                DefenseStatsContainer(heroDetails = heroDetails)
                AddSpacer(0.dp, 30.dp)
                MobilityStatsContainer(heroDetails = heroDetails)
            }
        }
    }
}