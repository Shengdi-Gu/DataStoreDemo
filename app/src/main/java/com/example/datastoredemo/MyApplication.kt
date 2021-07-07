package com.example.datastoredemo

import android.app.Application

/**
 * @author GSD
 * @Date 2021/6/8
 */
class MyApplication :Application() {
    companion object{
       lateinit var context:Application
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}