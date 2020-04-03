package com.rx.rxandroidnotes

import android.content.Context
import android.widget.Toast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


fun setBackpressureStrategy(backpressureStrategy: BackpressureStrategy, valueForTest: Int): Flowable<Int> =
    when (backpressureStrategy) {
        BackpressureStrategy.MISSING -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.DROP -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.LATEST -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.BUFFER -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.ERROR -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        else -> Flowable.just(1)
    }

fun disposable(context: Context, delayMillis: Long, message: String): Disposable =
    Observable
        .just(1,2,3)
        .delay(delayMillis, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Toast.makeText(context, "$message is $it", Toast.LENGTH_SHORT).show()
            Timber.d(MainActivity.TAG, "$message is $it")
        },
            {
                println(it)
            }
        )