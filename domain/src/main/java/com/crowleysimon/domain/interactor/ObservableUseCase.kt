package com.crowleysimon.domain.interactor

import com.crowleysimon.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class ObservableUseCase<T, in Params> protected constructor(private val postExecutionThread: PostExecutionThread) {
    private var disposables = CompositeDisposable()

    /**
     * Builds an [Observable] which will be used when the current [ObservableUseCase] is executed.
     */
    abstract fun buildUseCaseObservable(params: Params?): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}