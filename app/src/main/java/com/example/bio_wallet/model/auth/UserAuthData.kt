package com.example.bio_wallet.model.auth

import android.util.Log
import com.example.bio_wallet.model.UserModel

object UserAuthData{
    var currentUser : UserModel? = null
    var UID : String? = null


    fun transformToUserModel(response:String) : UserModel{
        val keyValuePairs = response.trimStart('{').trimEnd('}').split(", ")
        val map = keyValuePairs.associate {
            val (key, value) = it.split("=")
            key to value
        }
        return UserModel(
            name = map["name"],
            surname = map["surname"],
            phone = map["phone"],
            money = map["money"],
            faceId = map["faceId"]
        )
    }
}