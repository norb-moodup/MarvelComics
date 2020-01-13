package pl.norb.marvelcomics

import android.app.Application
import org.koin.android.ext.android.startKoin
import pl.norb.marvelcomics.modules.marvelApp
import pl.norb.marvelcomics.modules.marvelAppApi

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, marvelAppApi + marvelApp)
    }
}