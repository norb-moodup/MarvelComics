package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

data class MarvelModel(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("data")
    val data: MarvelDataModel
)