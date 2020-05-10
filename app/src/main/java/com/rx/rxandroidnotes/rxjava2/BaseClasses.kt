package com.rx.rxandroidnotes.rxjava2

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

fun flowableSetBackpreassureStrategy(backpreassureStrategy: BackpressureStrategy, valueForTest: Int): Flowable<Int> =
    when (backpreassureStrategy) {
        BackpressureStrategy.MISSING -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpreassureStrategy
        )
        BackpressureStrategy.DROP -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpreassureStrategy
        )
        BackpressureStrategy.LATEST -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpreassureStrategy
        )
        BackpressureStrategy.BUFFER -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpreassureStrategy
        )
        BackpressureStrategy.ERROR -> Flowable.create(
            { subscriber ->
                for (i in 0..valueForTest) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpreassureStrategy
        )
        else -> Flowable.just(1)
    }

