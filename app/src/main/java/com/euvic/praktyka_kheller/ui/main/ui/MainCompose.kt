package com.euvic.praktyka_kheller.ui.main.ui

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
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainRecyclerAdapter
import com.euvic.praktyka_kheller.ui.theme.HeroItemBg
import com.euvic.praktyka_kheller.ui.theme.HeroNameColor
import com.euvic.praktyka_kheller.ui.theme.Shapes

@Composable
fun setHeroItem(item: HeroDetails, heroImage: String?) {
    Surface(
        shape = Shapes.medium,
        modifier = Modifier
            //.clickable(onClick = { interaction.onItemSelected(adapterPosition, item) })
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
fun setDetailsView(heroName: String) {
    Text(
        text = heroName
    )
}