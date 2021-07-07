package com.example.datastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val testKey by myIntPreferencesKey(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_test.setOnClickListener {
            var value = getValueSync(testKey)
            Log.d(TAG, "currentValue: $value")
            putValue(testKey, ++value)
        }

        obValue(testKey).subscribe({ it ->
            Log.d(TAG, "onValueChange: $it")
        }, {
            Log.e(TAG, it.message ?: "")
        })
    }
}