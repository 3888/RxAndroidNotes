package com.rx.rxandroidnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val missing = setBackpressureStrategy(BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvMissing.text = ("MISSING $it")
                },
                    {
                        tvMissing.text = it.message
                    })

            val drop = setBackpressureStrategy(BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvDrop.text = ("DROP $it")
                },
                    {
                        tvDrop.text = it.message
                    })

            val latest = setBackpressureStrategy(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvLatest.text = ("LATEST $it")
                },
                    {
                        tvLatest.text = it.message
                    })


            val buffer = setBackpressureStrategy(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvBuffer.text = ("BUFFER $it")
                },
                    {
                        tvBuffer.text = it.message
                    })

            val error = setBackpressureStrategy(BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvError.text = ("$it")
                },
                    {
                        tvError.text = it.message
                    })
        }
    }
}
