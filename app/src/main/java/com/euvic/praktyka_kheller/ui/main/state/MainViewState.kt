package com.euvic.praktyka_kheller.ui.main.state

import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable

/*
Contains whatever is inside the view state (every object, data model inside the view)
 */

data class MainViewState (
    var heroes: List<HeroDataClass>? = null,
    var details: HeroDataClass? = null
) {
}