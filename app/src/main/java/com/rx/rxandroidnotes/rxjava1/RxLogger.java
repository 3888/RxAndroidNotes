package com.rx.rxandroidnotes.rxjava1;


import rx.Subscriber;

//@RxLogSubscriber
public class RxLogger extends Subscriber<Integer> {

    @Override
    public void onStart() {
        request(20);
    }

    @Override
    public void onNext(Integer value) {
//        Timber.i("" + value);
    }

    @Override
    public void onError(Throwable throwable) {
        //empty
    }

    @Override
    public void onCompleted() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }
}
