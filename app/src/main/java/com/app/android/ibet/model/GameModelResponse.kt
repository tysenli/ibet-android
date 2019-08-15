package com.app.android.ibet.model

import com.google.gson.annotations.SerializedName


data class GameModelResponse(
    val model: String,
    val pk: Int,
    @SerializedName("fields")

    val fields: GameFieldsResponse
)

data class GameFieldsResponse(
    @SerializedName("name")
    val name: String,
    val name_zh: String,
    val name_fr: String,
    val category_id: String,
    val start_time: String,
    val end_time: String,
    val opponent1: String,
    val opponent2: String,
    val description: String,
    val description_zh: String,
    val description_fr: String,
    val status_id: String,
    @SerializedName("image")
    val image: String,
    val imageURL: String,
    val attribute: String,
    val provider: Int,
    val popularity: String,
    val jackpot_size: Int,
    val created_time: String,
    val modifited_time: String

)