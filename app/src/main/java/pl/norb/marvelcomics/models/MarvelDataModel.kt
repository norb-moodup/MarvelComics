package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

data class MarvelDataModel(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: ArrayList<MarvelResultsModel>
)