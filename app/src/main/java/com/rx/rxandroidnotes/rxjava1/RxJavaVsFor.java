package com.rx.rxandroidnotes.rxjava1;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

class RxJavaVsFor {

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void rxJavaObserver() {
        Observable<Integer> observable = Observable.just(1, 2, 4);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                // do nothing
            }

            @Override
            public void onNext(Integer integer) {
                Timber.i(String.valueOf(integer));
            }
        });
    }

    static void rxObserverLambdas() {

        Observable<Integer> observable = Observable.just(1, 2, 0);

        observable.subscribe(
                integer -> Timber.i(String.valueOf(integer)));

        observable.subscribe(
                integer -> Timber.i(String.valueOf(integer)),
                throwable -> {/*handle error*/
                    Timber.e("onError");
                });

        observable.subscribe(
                integer -> Timber.i(String.valueOf(integer)),
                throwable -> {/*handle error*/},
                () -> {/*handle onCompleted*/
                    Timber.d("onCompleted");
                });
    }

    static void rxJavaVersusFor() {
        //RxJava
        Observable.just(1, 2, 4, 8, 16, 32, 64)
                .filter(integer -> integer >= 13)
                .map(String::valueOf)
                .subscribe(value -> Timber.i(value));

        //For
        int[] items = new int[]{1, 2, 4, 8, 16, 32, 64};
        for (int value : items) {
            if (value >= 13) {
                String s = String.valueOf(value);
                Timber.i(s);
            }
        }
    }

    public static void rxJavaVersusForExecutor() {
        //RxJava
        Observable.just(1, 2, 4, 8, 16, 32, 64)
                .filter(integer -> integer >= 13)
                .map(String::valueOf)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> Timber.i(value));

        //For
        EXECUTOR.execute(() -> {
            int[] items = new int[]{1, 2, 4, 8, 16, 32, 64};
            for (int value : items) {
                if (value >= 13) {
                    String s = String.valueOf(value);
                    HANDLER.post(() -> Timber.i(s));
                }
            }
        });
    }

}
