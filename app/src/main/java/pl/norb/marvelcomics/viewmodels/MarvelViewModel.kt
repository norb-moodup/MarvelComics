package pl.norb.marvelcomics.viewmodels

import androidx.lifecycle.MutableLiveData
import pl.norb.marvelcomics.backend.ApiClientInterface
import pl.norb.marvelcomics.models.AppState
import pl.norb.marvelcomics.models.MarvelResultsModel

class MarvelViewModel(api: ApiClientInterface) : BaseViewModel(api) {

    var appState: MutableLiveData<AppState> = MutableLiveData()
    lateinit var comicsList: ArrayList<MarvelResultsModel>

    fun getComics(offset: Int, title: String?) {
        val ts = 1
        val apikey = "3d3ce5daa8ec0f7c17afc52bb68f15f7"
        val hash = "a45bdb0bf57b06e72ad4c2c5854e2843"
        val limit = 25
        val orderBy = "-onsaleDate"

        appState.value = AppState.InProgress
        launch {
            api.getComics(ts, apikey, hash, limit, offset, title, orderBy).retry().subscribe({
                comicsList = it.data.results
                if (comicsList.isNullOrEmpty()) {
                    appState.value = AppState.NoResults
                } else {
                    appState.value = AppState.OnResults(comicsList)
                }
            }, {
                appState.value = AppState.Error("something went wrong!")
            })

        }
    }

    override fun onViewReady() {
        appState.value = AppState.Initialized
    }
}