package pl.norb.marvelcomics.modules

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import pl.norb.marvelcomics.BuildConfig
import pl.norb.marvelcomics.backend.ApiClient
import pl.norb.marvelcomics.backend.ApiClientInterface
import pl.norb.marvelcomics.backend.ApiService
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

val okHttpClientModule = module {

    single { createOkHttpClient() }
}

val apiServiceModule = module {

    single { createWebService<ApiService>(get(), BuildConfig.BASE_URL) }
}

val apiClientModule = module {

    single { ApiClient(get(), get()) as ApiClientInterface }
}

val marvelAppApi = listOf(okHttpClientModule, apiServiceModule, apiClientModule)

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

internal class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}