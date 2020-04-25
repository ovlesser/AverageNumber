package com.example.averagenumber

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NumberViewModel : ViewModel() {
    val number = MutableLiveData("")
    var average = MutableLiveData("0.0")
}
