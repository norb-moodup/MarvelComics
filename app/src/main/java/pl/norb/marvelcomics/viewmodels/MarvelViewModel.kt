package pl.norb.marvelcomics.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import pl.norb.marvelcomics.backend.ApiClientInterface
import pl.norb.marvelcomics.models.MarvelModel

class MarvelViewModel(private val api : ApiClientInterface): ViewModel() {

    fun getComics(offset : Int) : Single<MarvelModel> {
        val ts = 1
        val apikey = "3d3ce5daa8ec0f7c17afc52bb68f15f7"
        val hash = "a45bdb0bf57b06e72ad4c2c5854e2843"
        val limit = 25
        val orderBy = "-onsaleDate"
        return api.getComics(ts, apikey, hash, limit, offset, orderBy)
    }

    fun getComicsWithTitle(offset : Int, title : String) : Single<MarvelModel> {
        val ts = 1
        val apikey = "3d3ce5daa8ec0f7c17afc52bb68f15f7"
        val hash = "a45bdb0bf57b06e72ad4c2c5854e2843"
        val limit = 25
        val orderBy = "-onsaleDate"
        return api.getComics(ts, apikey, hash, limit, offset, title, orderBy)
    }
}