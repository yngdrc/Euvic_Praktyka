package com.euvic.praktyka_kheller.ui.main.state

import com.euvic.praktyka_kheller.db.model.HeroDataClass

/*
Contains whatever is inside the view state (every object, data model inside the view)
 */

data class MainViewState (
    var heroes: List<HeroDataClass>? = null,
    var details: HeroDataClass? = null
) {
}