package com.example.bio_wallet.model.auth

import android.util.Log
import com.example.bio_wallet.model.UserModel
import org.json.JSONObject

object UserAuthData{
    var currentUser : UserModel? = null
    var UID : String? = null


    fun transformToUserModel(response: String): UserModel {
        val cleanedResponse = response.trimStart('{').trimEnd('}').trim()

        val keyValuePairs = cleanedResponse.split(", (?=(?:[^&]*&[^&]*)*$)".toRegex()).map { it.trim() }

        val map = mutableMapOf<String, String?>()
        keyValuePairs.forEach {
            val keyValue = it.split("=", limit = 2)
            if (keyValue.size == 2) {
                val key = keyValue[0].trim()
                val value = keyValue[1].trim().removeSurrounding("\"")
                map[key] = value
            }
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