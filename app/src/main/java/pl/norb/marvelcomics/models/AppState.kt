package pl.norb.marvelcomics.models

sealed class AppState {
    object Initialized : AppState()
    object InProgress : AppState()
    data class OnResults(val marvelList: ArrayList<MarvelResultsModel>): AppState()
    object NoResults: AppState()
    data class Error(val errorMessage: String?): AppState()
}