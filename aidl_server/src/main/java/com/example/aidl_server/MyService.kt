package com.example.aidl_server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.frank.aidldemo.IMyAidlInterface

class MyService : Service() {

    private val mbind: IMyAidlInterface.Stub = object :IMyAidlInterface.Stub(){
        override fun sendMsg(param: String?): String {
            TODO("Not yet implemented")
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}