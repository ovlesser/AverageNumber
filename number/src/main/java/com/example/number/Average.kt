package com.example.number

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Average(val context: Context, val contentApiService: ContentApiService = ContentApiService.create()) {
    private val TAG = Average::class.simpleName
    val prestoredNumbers = mutableListOf<Double>()
    val userNumbers = mutableListOf<Double>()

    init {
        fetchPrestored()
        fetchUserNumbers()
    }

    private fun fetchPrestored() {
        contentApiService.prestored().enqueue(object : Callback<List<Double>> {
            override fun onFailure(call: Call<List<Double>>?, t: Throwable?) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<List<Double>>?, response: Response<List<Double>>?) {
                if (response?.code() == 200 ) {
                    response.body()?.let {
                        prestoredNumbers.addAll(it)
                        Log.d(TAG, prestoredNumbers.toString())
                    }
                }
            }
        })
    }

    private fun fetchUserNumbers() {
        val pref: SharedPreferences = context.applicationContext
            .getSharedPreferences("MyPref", 0) // 0 - for private mode
        var set: MutableSet<String> = HashSet()
        set = pref.getStringSet("userNumbers", mutableSetOf()) as MutableSet<String>
        set.map { userNumbers.add(it.toDouble()) }
    }

    fun addNumber( number: Double) {
        userNumbers.add(number)
        val set: MutableSet<String> = HashSet()
        set.addAll(userNumbers.map { it.toString() })
        val pref: SharedPreferences = context.applicationContext
                .getSharedPreferences("MyPref", 0) // 0 - for private mode
        pref.edit()
            .clear()
            .putStringSet("userNumbers", set)
            .apply()
    }

    fun getAverage(): Double = (this.prestoredNumbers + this.userNumbers).average()
}