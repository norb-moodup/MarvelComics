package pl.norb.marvelcomics.viewmodels

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.norb.marvelcomics.backend.ApiClientInterface

abstract class BaseViewModel(val api: ApiClientInterface) : ViewModel() {

    abstract fun onViewReady()

    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}