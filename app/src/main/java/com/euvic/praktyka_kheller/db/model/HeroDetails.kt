package com.euvic.praktyka_kheller.db.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroDetails (

    @Expose
    @SerializedName("id")
    val id: Int? = null,

    @Expose
    @SerializedName("name")
    val name: String? = null,

    @Expose
    @SerializedName("localized_name")
    val localized_name: String? = null,

    @Expose
    @SerializedName("primary_attr")
    val primary_attr: String? = null,

    @Expose
    @SerializedName("attack_type")
    val attack_type: String? = null,

    @Expose
    @SerializedName("roles")
    val roles: List<String>? = null,

) {

    override fun equals(other: Any?): Boolean {

        if(javaClass != other?.javaClass){
            return false
        }

        other as HeroDetails

        if(id != other.id){
            return false
        }
        if(name != other.name){
            return false
        }
        if(localized_name != other.localized_name){
            return false
        }
        if(primary_attr != other.primary_attr){
            return false
        }
        if(attack_type != other.attack_type){
            return false
        }
        if(roles != other.roles){
            return false
        }
//        if(imageUrl != other.imageUrl){
//            return false
//        }

        return true
    }

    override fun toString(): String {
        return "Hero(attack_type=$attack_type, id=$id, localized_name=$localized_name, name=$name, primary_attr=$primary_attr, roles=$roles)"
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (localized_name?.hashCode() ?: 0)
        result = 31 * result + (primary_attr?.hashCode() ?: 0)
        result = 31 * result + (attack_type?.hashCode() ?: 0)
        result = 31 * result + (roles?.hashCode() ?: 0)
//        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        return result
    }
}