package pl.norb.marvelcomics.models

import com.google.gson.annotations.SerializedName

class MarvelModel {
    @SerializedName("code")
    var code : String? = ""

    @SerializedName("data")
    var data : MarvelDataModel? = null

}