package com.example.aidl_server

import android.app.Application

class MyApplication:Application(){

    companion object {
        lateinit var application: MyApplication
    }



    override fun onCreate() {
        super.onCreate()
        application = this;
    }


}