package com.rx.rxandroidnotes.rxjava1;

import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorThrowable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

class RxJavaExamples {

//    Использовать метод create напрямую разработчикам не рекомендуется.
//    Чем же неудобен метод create? Во-первых, вам нужно корректно обрабатывать все потенциальные
//    ошибки и передавать их в подписчик самостоятельно. Во-вторых, нужно всегда следить за тем,
//    что подписчик еще подписан на поток данных.

    @NonNull
    static Observable<Integer> observableWithCreate() {
        return Observable.create(subscriber -> {
            subscriber.onNext(5);
            subscriber.onNext(10);
            try {
                //stub long-running operation
                Thread.sleep(300);
            } catch (InterruptedException e) {
                subscriber.onError(e);
                return;
            }

//    код в методе create нужно модифицировать, добавив проверку на то, что подписчику еще нужны
//    данные из потока. При этом такую проверку нужно добавлять перед каждым вызовом onNext,
//    onError и onCompleted (здесь это опущено для удобства), что неудобно и часто приводит
//    к ошибкам. Поэтому метод create использовать не нужно.

//            subscriber.onNext(15);

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(15);
            }
            subscriber.onCompleted();
        });
    }



    static void onRunSchedulerExample() {
        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        Looper backgroundLooper = backgroundThread.getLooper();

        SchedulerBackgroundThreadObservable()

// Метод subscribeOn служит для указания потока, в котором выполняется код для создания
// данных в Observable.
// Код в методе call в интерфейсе onSubscribe будет выполнен в том потоке, который был передан
// в метод subscribeOn

// Schedulers.io() – выполнение задач, которые не сильно нагружают процессор, но являются долгими.
// Это, к примеру, обращения к серверу или к базе данных. Размер пула потоков не ограничен.
// Schedulers.computation() – выполнение вычислительных задач. Размер этого пула эквивалентен
// количеству ядер процессора.
// Schedulers.newThread() – создает новый поток для каждой задачи.
// Scheduler.immediate() – выполнение задачи в том же потоке, откуда вызывается Observable.
// Чаще всего его используют для тестирования.

// Run on a background thread
                .subscribeOn(AndroidSchedulers.from(backgroundLooper))

// Be notified on the main thread
// Метод observeOn указывает поток, в котором данные должны обрабатываться подписчиком

// AndroidSchedulers.mainThread() переносит выполнение кода подписчика в главный поток
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("onError() %s", e);
                    }

                    @Override
                    public void onNext(String string) {
                        Timber.d("onNext(" + string + ")");
                    }
                });
    }

    private static Observable<String> SchedulerBackgroundThreadObservable() {
        return Observable.defer(() -> {
            try {
                // Do some long running operation
                Thread.sleep(TimeUnit.SECONDS.toMillis(3));
            } catch (InterruptedException e) {
                throw OnErrorThrowable.from(e);
            }
            return Observable.just("one", "two", "three", "four", "five");
        });
    }

    private static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("Scheduler-BackgroundThread Sample", THREAD_PRIORITY_BACKGROUND);
        }
    }

    void editTextObservable() {
//    editText.observeChanges()
//            .debounce(500, TimeUnit.MILLISECONDS)
//    .map(String::toLowerCase)
//    .flatMap(this::findPerson)
//    .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(person -> {}, throwable -> {});


    }

}
