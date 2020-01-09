package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

class MarvelDataModel {
    @SerializedName("results")
    var results = ArrayList<MarvelResultsModel>()
}