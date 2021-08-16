package com.euvic.praktyka_kheller.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.ui.main.ui.theme.HeroItemBg
import com.euvic.praktyka_kheller.ui.main.ui.theme.HeroNameColor
import com.euvic.praktyka_kheller.ui.main.ui.theme.Shapes

@Composable
fun setHeroItem(heroName: String, heroRoles: List<String>?, heroImage: String?) {
    Surface(
        shape = Shapes.medium,
    ) {
        Row(
            Modifier
                //.border(2.dp, Color.Black, RoundedCornerShape(8.dp)
                .background(HeroItemBg)
                .padding(16.dp)
                .clickable(onClick = {Log.d("Compose", "Clicked")})
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
            Text(
                text = heroName,
                color = HeroNameColor,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}