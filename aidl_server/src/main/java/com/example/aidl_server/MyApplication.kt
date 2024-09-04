package com.example.aidl_server

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.ParcelFileDescriptor

class MyApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var application: MyApplication

        fun getMyApplication(): MyApplication {
            return application
        }


    }

    val mhandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 在这里处理来自子线程的消息
            // 这里的代码会在主线程中执行
            when (msg.what) {
                1 -> {
                    val bytes = msg.obj as ByteArray
                    // 处理bytes数组
                        getDataCallback?.onGetClientData(bytes)
                }

                2 -> {
                    val parcelFileDescriptor = msg.obj as ParcelFileDescriptor
                    // 处理ParcelFileDescriptor
                        sendDataCallback?.onSendClientData(parcelFileDescriptor)
                }

                else -> {
                    // 处理其他情况
                }
            }
        }
    }

    protected var getDataCallback: OnGetClientDataCallback? = null
    protected var sendDataCallback: OnSendClientDataCallback? = null


    override fun onCreate() {
        super.onCreate()
        application = this;
    }

    interface OnGetClientDataCallback {
        fun onGetClientData(bytes: ByteArray?)
    }

    fun setOnGetClientDataCallback(onGetClientDataCallback: OnGetClientDataCallback) {
        this.getDataCallback = onGetClientDataCallback
    }

    interface OnSendClientDataCallback {
        fun onSendClientData(parcelFileDescriptor: ParcelFileDescriptor?)
    }

    fun setOnSendClientDataCallback(onSendClientDataCallback: OnSendClientDataCallback) {
        this.sendDataCallback = onSendClientDataCallback
    }


}