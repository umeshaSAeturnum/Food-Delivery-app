package com.example.foodapp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.foodapp.db.models.User
import dagger.hilt.android.HiltAndroidApp


//use HiltAndroidApp to tell the application that we need to inject dependencies using dagger hilt
//when we set things in the BaseApplication we can use them through out the application lifetime
@HiltAndroidApp
class BaseApplication :Application() {



    override fun onCreate() {
        super.onCreate()

    }




}