package com.rx.rxandroidnotes.rxjava1.rx.tasks;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

//https://habrahabr.ru/post/324106/

public class Habrahabr {

    public static void cities() {
//    У вас есть список городов, уложенный в контейнер List. Необходимо вывести его на печать.
//    Что в результате должны увидеть в консоли:
//
//    город Париж
//    город Лондон
//    город Рим
//    город Москва
        List<String> cities = Arrays.asList("Париж", "Лондон", "Рим", "Москва");

// Мое решение
        Observable.from(cities)
                .subscribe(s -> Timber.i("город %s", s));

// Решение автора
        Observable.from(cities)
                .map(s -> "город " + s)
                .subscribe(System.out::println);
    }

    public static void citiesTwo() {
// У вас есть список городов и стран, уложенных в контейнеры List.
// Необходимо вывести список, упорядоченный по алфавиту, в формате
// <название города> — столица <название страны> на печать.

//        Что в результате ожидаем увидеть в консоли:
//
//        Лондон — столица Англии
//        Москва — столица России
//        Париж — столица Франции
//        Рим — столица Италии

        List<String> cities = Arrays.asList("Париж", "Лондон", "Рим", "Москва");
        List<String> countries = Arrays.asList("Франция", "Англия", "Италия", "Россия");
    }

}
