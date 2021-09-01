package com.euvic.praktyka_kheller.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "heroes2")
data class HeroDetails (

    @Expose
    @SerializedName("id")
    @PrimaryKey val id: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("localized_name")
    val localized_name: String,

    @Expose
    @SerializedName("primary_attr")
    val primary_attr: String,

    @Expose
    @SerializedName("attack_type")
    val attack_type: String,

//    @Expose
//    @SerializedName("roles")
//    val roles: List<String>,

    ) {

    override fun toString(): String {
        return "Hero(attack_type=$attack_type, id=$id, localized_name=$localized_name, name=$name, primary_attr=$primary_attr)"
    }
}