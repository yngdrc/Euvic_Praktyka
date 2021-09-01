package com.euvic.praktyka_kheller.util

import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.model.HeroDetails

class Constants {
    companion object {
        val TESTING_NETWORK_DELAY = 1000L
        const val STEAM_DOTA_IMAGES_URL: String = "https://cdn.dota2.com/apps/dota2/images/heroes/"
        const val OPENDOTA_HEROES_URL: String = "https://api.opendota.com/api/"
        const val OPENDOTA_HEROES_URL_2: String = "https://api.opendota.com/api/constants/"
        const val STEAM_DOTA_IMAGES_PREFIX: String = "npc_dota_hero_"
        const val STEAM_DOTA_IMAGES_RES_LG: String = "_lg.png"
        const val STEAM_DOTA_IMAGES_RES_VERT: String = "_vert.jpg"

        fun getHeroImageSrc(heroDetails: HeroDataClass, resolution: String = STEAM_DOTA_IMAGES_RES_VERT): String {
            return STEAM_DOTA_IMAGES_URL.plus(
                heroDetails.name.replace(
                    STEAM_DOTA_IMAGES_PREFIX,
                    ""
                ).plus(resolution)
            )
        }
    }
}