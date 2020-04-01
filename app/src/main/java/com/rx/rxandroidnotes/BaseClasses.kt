package com.rx.rxandroidnotes

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable


fun setBackpressureStrategy(backpressureStrategy: BackpressureStrategy): Flowable<Int> =
    when (backpressureStrategy) {
        BackpressureStrategy.MISSING -> Flowable.create(
            { subscriber ->
                for (i in 0..10000000) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.DROP -> Flowable.create(
            { subscriber ->
                for (i in 0..10000000) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.LATEST -> Flowable.create(
            { subscriber ->
                for (i in 0..10000000) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.BUFFER -> Flowable.create(
            { subscriber ->
                for (i in 0..10000000) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        BackpressureStrategy.ERROR -> Flowable.create(
            { subscriber ->
                for (i in 0..10000000) {
                    subscriber.onNext(i)
                }

                subscriber.onComplete()
            }, backpressureStrategy
        )
        else -> Flowable.just(1, 2, 3)
    }