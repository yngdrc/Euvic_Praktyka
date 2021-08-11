package com.euvic.praktyka_kheller.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroImage (

    @Expose
    @SerializedName("image")
    val image: String? = null
) {
    override fun toString(): String {
        return "User(image=$image)"
    }
}