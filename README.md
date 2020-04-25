### Average library
## Average class
# Data Structure
* prestoredNumbers : MutableList<Double>
  this list is used to save prefetched data, which is got from server while initilaizing the instance
* userNumbers : MutableList<Double>
  this list is used to save user imputted data, this list will be initialized with stored data from system
# API
* AddNumber(number: Double)
    add a number to the userNumbers, and save this number to the SharedPreferences
* getAverage()
    calculete the average of all number in both lists, prestoredNumbers and userNumbers

## Inplementation
# user libraries / techniques
* Kotlin
* MVVM
* Retrofit
    Retrofit is used to fetch data from server
* Jackson
    Jackson is used to pass the fetched data with JSON format
* Spek / mockk
    Spek / mockk are used for unit testing
* LiveData
* Data Binding
