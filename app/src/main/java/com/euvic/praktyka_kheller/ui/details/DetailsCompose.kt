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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainViewModel
import com.euvic.praktyka_kheller.util.Constants
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_PREFIX
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_RES_VERT

@Composable
fun showDetails(viewModel: MainViewModel) {
    val heroDetails: HeroDetails? = viewModel.dataState.value?.data?.peekContent()?.details
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
            Image(
                painter = rememberImagePainter(heroImage),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .scale(1.2f, 1.2f)
                    .align(Alignment.CenterHorizontally)
                    .border(13.5.dp, MaterialTheme.colors.secondary, CircleShape)
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