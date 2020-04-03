package com.rx.rxandroidnotes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val views = arrayOf(tvMissing, tvDrop, tvLatest, tvBuffer, tvError, button)


        checkBox.setOnClickListener {
            views.forEach {
                if (checkBox.isChecked) {
                    it.visibility = View.VISIBLE
                } else {
                    it.visibility = View.GONE
                }
            }
        }

        button.setOnClickListener {
            val missing = setBackpressureStrategy(BackpressureStrategy.MISSING, TEST_VALUE_MILLION)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvMissing.text = ("MISSING $it")
                },
                    {
                        tvMissing.text = it.message
                    })

            val drop = setBackpressureStrategy(BackpressureStrategy.DROP, TEST_VALUE_MILLION)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvDrop.text = ("DROP $it")
                },
                    {
                        tvDrop.text = it.message
                    })

            val latest = setBackpressureStrategy(BackpressureStrategy.LATEST, TEST_VALUE_MILLION)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvLatest.text = ("LATEST $it")
                },
                    {
                        tvLatest.text = it.message
                    })


            val buffer = setBackpressureStrategy(BackpressureStrategy.BUFFER, 100000)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvBuffer.text = ("BUFFER $it")
                },
                    {
                        tvBuffer.text = it.message
                    })

            val error = setBackpressureStrategy(BackpressureStrategy.ERROR, TEST_VALUE_MILLION)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvError.text = ("$it")
                },
                    {
                        tvError.text = it.message
                    })
        }

        val disposableExample = disposable(this, 500, "Result")

        android.os.Handler().postDelayed({
            disposableExample.dispose()
            if (disposableExample.isDisposed) {
                Toast.makeText(this, "isDisposed", Toast.LENGTH_SHORT).show()
                Timber.d("isDisposed")
            }
        }, 2000)

    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val TEST_VALUE_MILLION = 1000000
    }
}
