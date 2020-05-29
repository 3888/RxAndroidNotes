package com.rx.rxandroidnotes.rxjava2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rx.rxandroidnotes.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_main.checkBox
import kotlinx.android.synthetic.main.activity_main.tvBuffer
import kotlinx.android.synthetic.main.activity_main.tvDrop
import kotlinx.android.synthetic.main.activity_main.tvError
import kotlinx.android.synthetic.main.activity_main.tvLatest
import kotlinx.android.synthetic.main.activity_main.tvMissing
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

//        disposeBag.add(disposableDelayExample(this, 1000, "Hey"))
//        disposeBag.add(flatMapDoesNotGuaranteeTheOrderOfTheItems())
//        disposeBag.add(concatMapGuaranteeTheOrderOfTheItems())
//        disposeBag.add(zipWith())

        val test = userList()
            .timeInterval()
            .subscribe({
                Timber.tag("Rx").d("${it.value()}  ${it.time()}")
            },
                {

                })
    }

    override fun onDestroy() {
        disposeBag.clear()
        super.onDestroy()
    }

    private fun userList(): Observable<String> {
        return Observable.just("A", "B", "C", "D")
            .zipWith(
                Observable.interval(300, TimeUnit.MILLISECONDS),
                BiFunction<String, Long, String> { t1, t2 -> "$t1, $t2 " })
    }

    private fun initViews() {
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
            val missing =
                flowableSetBackpreassureStrategy(BackpressureStrategy.MISSING, TEST_VALUE_MILLION)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        tvMissing.text = ("MISSING $it")
                    },
                        {
                            tvMissing.text = it.message
                        })

            val drop =
                flowableSetBackpreassureStrategy(BackpressureStrategy.DROP, TEST_VALUE_MILLION)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        tvDrop.text = ("DROP $it")
                    },
                        {
                            tvDrop.text = it.message
                        })

            val latest =
                flowableSetBackpreassureStrategy(BackpressureStrategy.LATEST, TEST_VALUE_MILLION)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        tvLatest.text = ("LATEST $it")
                    },
                        {
                            tvLatest.text = it.message
                        })

            val buffer = flowableSetBackpreassureStrategy(BackpressureStrategy.BUFFER, 100000)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvBuffer.text = ("BUFFER $it")
                },
                    {
                        tvBuffer.text = it.message
                    })

            val error =
                flowableSetBackpreassureStrategy(BackpressureStrategy.ERROR, TEST_VALUE_MILLION)
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

    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val TEST_VALUE_MILLION = 1000000
    }
}
