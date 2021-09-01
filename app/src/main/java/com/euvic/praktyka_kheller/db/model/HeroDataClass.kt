package com.euvic.praktyka_kheller.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "heroes")
data class HeroDataClass(
    @Expose
    @SerializedName("agi_gain")
    val agi_gain: Double,

    @Expose
    @SerializedName("attack_range")
    val attack_range: Int,

    @Expose
    @SerializedName("attack_rate")
    val attack_rate: Double,

    @Expose
    @SerializedName("attack_type")
    val attack_type: String,

    @Expose
    @SerializedName("base_agi")
    val base_agi: Int,

    @Expose
    @SerializedName("base_armor")
    val base_armor: Int,

    @Expose
    @SerializedName("base_attack_max")
    val base_attack_max: Int,

    @Expose
    @SerializedName("base_attack_min")
    val base_attack_min: Int,

    @Expose
    @SerializedName("base_health")
    val base_health: Int,

    @Expose
    @SerializedName("base_health_regen")
    val base_health_regen: Double,

    @Expose
    @SerializedName("base_int")
    val base_int: Int,

    @Expose
    @SerializedName("base_mana")
    val base_mana: Int,

    @Expose
    @SerializedName("base_mana_regen")
    val base_mana_regen: Int,

    @Expose
    @SerializedName("base_mr")
    val base_mr: Int,

    @Expose
    @SerializedName("base_str")
    val base_str: Int,

    @Expose
    @SerializedName("cm_enabled")
    val cm_enabled: Boolean,

    @Expose
    @SerializedName("icon")
    val icon: String,

    @Expose
    @SerializedName("id")
    @PrimaryKey val id: Int,

    @Expose
    @SerializedName("img")
    val img: String,

    @Expose
    @SerializedName("int_gain")
    val int_gain: Double,

    @Expose
    @SerializedName("legs")
    val legs: Int,

    @Expose
    @SerializedName("localized_name")
    val localized_name: String,

    @Expose
    @SerializedName("move_speed")
    val move_speed: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("primary_attr")
    val primary_attr: String,

    @Expose
    @SerializedName("projectile_speed")
    val projectile_speed: Int,

//    @Expose
//    @SerializedName("roles")
//    val roles: List<String>,

    @Expose
    @SerializedName("str_gain")
    val str_gain: Double,

    @Expose
    @SerializedName("turn_rate")
    val turn_rate: Double
)