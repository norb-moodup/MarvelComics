package pl.norb.marvelcomics.viewmodels

import android.content.Context
import pl.norb.marvelcomics.models.MarvelResultsModel

class MarvelViewModel(
    private val context : Context,
    private val comic : MarvelResultsModel
) {
    val title = comic.comicTitle
    val description = comic.comicDescription
    val pictureUrl = comic.comicThumbnailUrl.path + "." + comic.comicThumbnailUrl.extension
}