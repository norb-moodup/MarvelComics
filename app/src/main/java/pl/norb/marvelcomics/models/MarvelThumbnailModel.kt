package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

data class MarvelThumbnailModel(
    @SerializedName("path")
    val path: String = "",
    @SerializedName("extension")
    val extension: String = ""
)