package com.rx.rxandroidnotes.rxjava2

import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun flatMapDoesNotGuaranteeTheOrderOfTheItems(): Disposable {
    return Observable
        .just("First", "Second", "Third", "Four", "Fifth")
        .flatMap {
            val delay = java.util.Random().nextInt(5)
            Timber.tag("Rx").d("flatMap delay for $it is $delay")
            Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.io())
        .subscribe {
            Timber.tag("Rx").d("after flatMap $it")
        }
}

fun concatMapGuaranteeTheOrderOfTheItems(): Disposable {
    return Observable
        .just("First", "Second", "Third", "Four", "Fifth")
        .concatMap {
            val delay = java.util.Random().nextInt(5)
            Timber.tag("Rx").d("concatMap delay for $it is $delay")
            Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.io())
        .subscribe {
            Timber.tag("Rx").d("after concatMap $it")
        }
}

fun disposableDelayExample(context: Context, delayMillis: Long, message: String): Disposable =
    Observable
        .just(1, 2, 3)
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

fun zipWith(): Disposable {
        val chars = Observable.just("A", "B", "C", "D", "NO PAIR FOR THIS VALUE")
        val numbers = Observable.just(1, 2, 3, 4)

    return  numbers.zipWith(chars,
        BiFunction<Int, String, String> { t1, t2 ->
                "$t1 $t2"
        })
        .subscribe({
            Timber.tag("Rx").d(it)
        }, {

        })
}