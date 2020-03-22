package com.rx.rxandroidnotes.rxjava1;


public class OldMainActivity{


//        RxOperators.just();
//        RxOperators.justLambda();
//        RxOperators.map();
//        RxOperators.mapStringToHashcode();
//        RxOperators.mapHashcodeTo\String();
//        RxOperators.filter();
//        RxOperators.first();
//        RxOperators.from();

//        TODO
//        RxOperators.mapSimple(?);
//        RxOperators.mapMany(?);

//        RxOperators.async();
//        RxOperators.asyncTransformer();
//

//TODO get Timber.i("concat  " + RxOperators.concat());

//        RxOperators observableSample = new RxOperators();
//        observableSample.concat()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> integer.toString());
//                .subscribe(new RxLogger());


//        String merge = RxOperators.merge().toString();
//        Timber.i("merge " + merge);
//        String zip = RxOperators.zip().toString();
//        Timber.i("zip " + zip);

//        RxJavaVsFor.rxJavaObserver();
//        RxJavaVsFor.rxObserverLambdas();
//        RxJavaVsFor.rxJavaVersusFor();
//        RxJavaVsFor.rxJavaVersusForExecutor();

//        RxJavaExamples.onRunSchedulerExample();

//  В этом коде подписчик подписывается на получение данных и сразу от них отписывается.
//  А код Observable ждет 300мс перед тем, как передать подписчику последний элемент,
//  и в момент передачи подписчик уже отписался от получения данных. Поэтому происходит ошибка
//        Subscription subscription = RxJavaExamples.observableWithCreate()
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(System.out::println);
//        subscription.unsubscribe();


//        Habrahabr.cities();

}
