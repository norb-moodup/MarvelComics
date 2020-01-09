package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

class MarvelResultsModel {
    @SerializedName("id")
    var comicId : String? = ""
    @SerializedName("title")
    var comicTitle : String? = ""
    @SerializedName("description")
    var comicDescription : String ? = ""
    @SerializedName("thumbnail")
    var comicThumbnailUrl : MarvelThumbnailModel? = null
}