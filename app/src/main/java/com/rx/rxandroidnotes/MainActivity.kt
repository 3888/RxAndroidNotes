package com.rx.rxandroidnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dispose = dataSource()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    textTextView.setText("text is $it")
                },
                        {
                            Timber.e(it)
                        })

    }

    fun dataSource(): Flowable<Int> {
        return Flowable.create(
                { subscriber ->
                    for (i in 0..20) {
                        Thread.sleep(1000)
                        subscriber.onNext(i)
                    }

                    subscriber.onComplete()
                }, BackpressureStrategy.DROP)


    }
}
