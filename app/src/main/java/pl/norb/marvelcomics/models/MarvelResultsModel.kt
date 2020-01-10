package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

data class MarvelResultsModel(
    @SerializedName("id")
    val comicId: String = "",
    @SerializedName("title")
    val comicTitle: String = "",
    @SerializedName("description")
    val comicDescription: String = "",
    @SerializedName("thumbnail")
    val comicThumbnailUrl: MarvelThumbnailModel
)