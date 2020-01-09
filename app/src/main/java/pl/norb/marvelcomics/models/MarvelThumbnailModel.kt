package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

class MarvelThumbnailModel {
    @SerializedName("path")
    var path : String? = ""
    @SerializedName("extension")
    var extension : String? = ""
}