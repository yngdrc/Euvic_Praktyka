package com.euvic.praktyka_kheller.ui.main

import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.ui.setHeroItem
import com.euvic.praktyka_kheller.util.Constants
import com.google.accompanist.appcompattheme.AppCompatTheme

class HeroViewHolder
constructor(
    itemView: View,
    private val interaction: MainRecyclerAdapter.Interaction?
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: HeroDetails) = with(itemView) {
        val hero = findViewById<ComposeView>(R.id.hero_name)
        hero.setContent {
            AppCompatTheme() {
//                    interaction?.let { interaction ->
//                        setHeroItem(
//                            interaction, absoluteAdapterPosition, item,null?: Constants.STEAM_DOTA_IMAGES_URL.plus(
//                                item.name?.replace(
//                                    Constants.STEAM_DOTA_IMAGES_PREFIX,
//                                    ""
//                                ).plus(Constants.STEAM_DOTA_IMAGES_RES)
//                            ))
//                    }
                }
        }
    }
}