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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.util.Constants

@Composable
fun showDetails(viewModel: MainViewModel) {
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
                Modifier
                    .height(10.dp)
            )

            Text(
                text = heroDetails.localized_name,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Spacer(
                Modifier
                    .height(20.dp)
            )

            Row() {
                Image(
                    painter = rememberImagePainter("https://cdn.cloudflare.steamstatic.com/apps/dota2/images/dota_react/herogrid/filter-str-active.png"),
                    contentDescription = null
                )

                Spacer(
                    Modifier
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