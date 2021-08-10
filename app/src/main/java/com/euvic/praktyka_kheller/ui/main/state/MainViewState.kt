package com.euvic.praktyka_kheller.ui.main.state

import com.euvic.praktyka_kheller.model.HeroDetails

data class MainViewState (
    var heroes: List<HeroDetails>? = null
) {
}