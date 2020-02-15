package pl.norb.marvelcomics.backend

import io.reactivex.Single
import org.koin.sampleapp.util.rx.SchedulerProvider
import pl.norb.marvelcomics.models.MarvelModel

class ApiClient(
    private val apiService: ApiService,
    private val schedulersProvider: SchedulerProvider
) : ApiClientInterface {
    override fun getComics(
        ts: Int,
        apikey: String,
        hash: String,
        limit: Int,
        offset: Int,
        title: String?,
        orderBy: String
    ): Single<MarvelModel> {
        return apiService.getComics(ts, apikey, hash, limit, offset, title, orderBy)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())    }
}