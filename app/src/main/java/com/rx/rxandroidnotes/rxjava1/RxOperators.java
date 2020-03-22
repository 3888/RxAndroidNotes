package com.rx.rxandroidnotes.rxjava1;



import android.annotation.SuppressLint;

import androidx.annotation.NonNull;


import com.rx.rxandroidnotes.rxjava1.model.Person;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

//  http://reactivex.io/documentation/operators.html
class RxOperators<T> {

//TODO check Transformer
    @SuppressLint("TimberArgCount")
    public static <T> Observable.Transformer<T, T> log(String tag) {
        return o -> o
                .doOnNext(it -> Timber.i("{}, next: {}", tag, it))
                .doOnError(it -> Timber.i(it, "{}, error: {}", tag))
                .doOnCompleted(() -> Timber.i("{}, complete", tag))
                .doOnSubscribe(() -> Timber.i("{}, subscribe", tag))
                .doOnUnsubscribe(() -> Timber.i("{}, unsubscribe", tag));
    }

    static void just() {
        Observable<Integer> observable = Observable.just(1, 2, 4);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Timber.i(String.valueOf(integer));
            }
        });
    }

    static void justLambda() {
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

    static void map() {
// оператор map(), преобразовывает один элемент данных в потоке в любой другой элемент
        Observable.just("Hello, world!")

//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return s + " - Dude!";
//                    }
//                })
//                .subscribe(s -> Timber.i(s));

                .map(s -> s + " - Dude!")
                .subscribe(s -> Timber.i(s));
    }

// если для преобразования одного объекта в другой требуется несколько операций, лучше использовать
// несколько операторов map, чем писать несколько операций в одном операторе. Это сильно улучшит
// читаемость вашего кода. То есть вместо такого кода:

    @NonNull
    public static Observable<Integer> mapSimple(@NonNull Observable<Integer> observable) {
        return observable
                .map(integer -> {
                    int value = integer * 2;
                    String text = String.valueOf(value);
                    return text.hashCode();
                });
    }

    //        Лучше использовать такой:
    @NonNull
    public static Observable<Integer> mapMany(@NonNull Observable<Integer> observable) {
        return observable
                .map(integer -> integer * 2)
                .map(String::valueOf)
                .map(String::hashCode);
    }

    static void mapStringToHashcode() {
//        map() не обязан порождать данные того же самого типа, что и исходный Observable.
//       Допустим, что наш Subscriber должен выводить не порождаемый текст, а его хэш:
        Observable.just("Hello, world!")

//                .map(new Func1<String, Integer>() {
//                    @Override
//                    public Integer call(String s) {
//                        return s.hashCode();
//                    }
//                })
//                .subscribe(s -> Timber.i(TAG, s.toString(s)));

                .map(String::hashCode)
                .subscribe(s -> Timber.i(Integer.toString(s)));

        String s = "Hello, world!";
        Timber.d("CHECKING: hashCode = " + String.valueOf(s.hashCode()));
    }

    static void mapHashcodeToString() {
        Observable.just("Hello, world!")
                .map(String::hashCode)
                .map(i -> Integer.toString(i) + " - this is a String")
                .subscribe(s -> Timber.i(s));
    }

    static void filter() {
//  http://reactivex.io/documentation/operators/filter.html
//  оставляет в потоке только данные, удовлетворяющие переданному в качестве параметра условию
        Observable.just(1, 2, 3, 4, 5)
                .filter(item -> (item < 4)).subscribe(
                integer -> Timber.i(String.valueOf(integer)));
    }

    static void first() {
        Observable.just(1, 2, 3, 4, 5)
                .first(item -> (item > 2)).subscribe(
                integer -> Timber.i(String.valueOf(integer)));
    }

    static void from() {
//        from позволяет создать поток данных из списка элементов
        List<Integer> values = new ArrayList<>();
        values.add(5);
        values.add(10);
        values.add(15);
        values.add(20);

        Observable.from(values)
                .subscribe(s -> Timber.i(String.valueOf(s)));

        //        return Observable.from(values);
    }

    static void skip() {

    }

    static void take() {

    }

//    К примеру, для каждого сетевого запроса вы наверняка будете писать следующий код
//    для управления потоками:

    @NonNull
    public static Observable<Integer> async() {
        return Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    Было бы логично, если бы могли использовать свой оператор для этой задачи. Для таких целей
//    служит интерфейс Transformer. Создадим свой трансформер для потока данных:

    @NonNull
    public static Observable<Integer> asyncTransformer() {
        return Observable.just(1)
                .compose(new AsyncTransformer<>());
    }

//    Чтобы выполнить последовательно несколько Observable, можно использовать метод concat

    @NonNull
    static Observable<Integer> concat() {
        Observable<Integer> first = Observable.just(1, 4, 8);
        Observable<Integer> second = Observable.just(2, 6, 9);
        Observable<Integer> third = Observable.just("Red", "Hello").map(String::length);
        return Observable.concat(first, second, third);
    }

//  если вам нужно выполнить несколько Observable не последовательно, а параллельно
//  (что часто бывает нужно, чтобы ускорить загрузку при выполнении нескольких запросов)
//  При этом порядок поступления элементов не определен

    @NonNull
    public static Observable<Integer> merge() {
        Observable<Integer> first = Observable.just(1, 4, 8);
        Observable<Integer> second = Observable.just(2, 6, 9);
        Observable<Integer> third = Observable.just("Red", "Hello").map(String::length);
        return Observable.merge(first, second, third);
    }

// метод concat, и метод merge требуют, чтобы данные в Observable были одного типа, что не всегда удобно
// метод zip, который принимает на вход список Observable, которые будут выполняться параллельно,
// а также функцию для преобразования данных из всех запросов:

    @NonNull
    public static Observable<Person> zip() {
        Observable<String> names = Observable.just("John", "Jack");
        Observable<Integer> ages = Observable.just(28, 17);
        return Observable.zip(names, ages, Person::new);
    }
}

