package com.example.datastoredemo

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.properties.ReadOnlyProperty

/**
 * @author GSD
 * @Date 2021/6/7
 */
val Context.dataStore: RxDataStore<Preferences> by lazy {
    RxPreferenceDataStoreBuilder(MyApplication.context, "settings").build()
}

val defaultValueMap = HashMap<String, Any>()

var <T> Preferences.Key<T>.defaultValue: T
    get() {
        return defaultValueMap[this.name] as T
    }
    set(value: T) {
        defaultValueMap[this.name] = value as Any
    }

fun myStringPreferencesKey(defValue: String) =
    ReadOnlyProperty<Any, Preferences.Key<String>> { _, property ->
        return@ReadOnlyProperty stringPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

fun myIntPreferencesKey(defValue: Int) =
    ReadOnlyProperty<Any, Preferences.Key<Int>> { _, property ->
        return@ReadOnlyProperty intPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

fun myFloatPreferencesKey(defValue: Float) =
    ReadOnlyProperty<Any, Preferences.Key<Float>> { _, property ->
        return@ReadOnlyProperty floatPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

fun myDoublePreferencesKey(defValue: Double) =
    ReadOnlyProperty<Any, Preferences.Key<Double>> { _, property ->
        return@ReadOnlyProperty doublePreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }


fun myBooleanPreferencesKey(defValue: Boolean) =
    ReadOnlyProperty<Any, Preferences.Key<Boolean>> { _, property ->
        return@ReadOnlyProperty booleanPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

fun myLongPreferencesKey(defValue: Long) =
    ReadOnlyProperty<Any, Preferences.Key<Long>> { _, property ->
        return@ReadOnlyProperty longPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

fun myStringSetPreferencesKey(defValue: Set<String>) =
    ReadOnlyProperty<Any, Preferences.Key<Set<String>>> { _, property ->
        return@ReadOnlyProperty stringSetPreferencesKey(property.name).apply {
            defaultValue = defValue
        }
    }

@ExperimentalCoroutinesApi
fun <T> Context.putValue(key: Preferences.Key<T>, value: T) {
    dataStore.updateDataAsync {
        return@updateDataAsync Single
            .just(it.toMutablePreferences()
                .apply {
                    this[key] = value
                })
    }.subscribe()
}

@ExperimentalCoroutinesApi
fun <T> Context.getValueSync(key: Preferences.Key<T>): T {
    return dataStore.data().map {
        return@map it[key] ?: key.defaultValue
    }.blockingFirst()
}

@ExperimentalCoroutinesApi
fun <T> Context.getValue(key: Preferences.Key<T>):Maybe<T>{
    return dataStore.data().map {
        return@map it[key]?:key.defaultValue
    }.toObservable().firstElement()
}

@ExperimentalCoroutinesApi
fun <T> Context.obValue(key: Preferences.Key<T>): Observable<T> {
    return dataStore.data().map {
        return@map it[key] ?: key.defaultValue
    }.distinctUntilChanged().toObservable()
}