package pl.norb.marvelcomics.backend

import androidx.annotation.Nullable
import io.reactivex.Single
import pl.norb.marvelcomics.models.MarvelModel
import retrofit2.http.GET
import retrofit2.http.Query

open interface ApiService {
    @GET("comics")
    fun getComics(
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("orderBy") orderBy: String
    ): Single<MarvelModel>

    @GET("comics")
    fun getComics(
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Nullable @Query("title") title: String,
        @Query("orderBy") orderBy: String
    ): Single<MarvelModel>
}