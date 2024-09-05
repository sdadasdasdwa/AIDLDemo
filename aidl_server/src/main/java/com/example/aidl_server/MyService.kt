package com.example.aidl_server

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Message
import android.os.ParcelFileDescriptor
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import com.frank.aidldemo.ICallbackInterface
import com.frank.aidldemo.IMyAidlInterface
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException


class MyService : Service() {
    private val TAG:String = "Ms.CG"

    private var callbackList: RemoteCallbackList<ICallbackInterface>? = RemoteCallbackList()

    private val mbind: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub(){
        override fun sendMsg(param: String?): String? {
            Log.d(TAG,"Server has received")
            return null
        }

        override fun sendImage(data: ByteArray?) {
            Log.d(TAG,data.toString());
        }

        override fun client2server(pfd: ParcelFileDescriptor?) {
            Log.d(TAG, "客户端想服务端上传图片 $pfd")
            val fileDescriptor = pfd!!.fileDescriptor
            val fileInputStream = FileInputStream(fileDescriptor)

            // 从fileInputStream那里读取字节数组
            var bytes: ByteArray? = null
            bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                fileInputStream.readAllBytes()
            } else {
                // 低于 API 33 时的兼容方法
                val buffer = ByteArrayOutputStream()
                val data = ByteArray(1024)
                var nRead: Int
                while (fileInputStream.read(data, 0, data.size).also { nRead = it } != -1) {
                    buffer.write(data, 0, nRead)
                }
                buffer.flush()
                buffer.toByteArray()
            }
            Log.d(TAG, "byteArray size " + bytes!!.size)
            val message: Message = Message()
            message.what = 1
            message.obj = bytes

            // Message用于应用内进行通信，这里是用于改变什么
            MyApplication.getMyApplication().mhandler.sendMessage(message)
        }

        override fun registerCallback(callback: ICallbackInterface?) {
            callbackList?.register(callback);
        }

        override fun unregisterCallback(callback: ICallbackInterface?) {
            callbackList?.unregister(callback);
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return mbind
    }

    override fun onCreate() {
        super.onCreate()
        MyApplication.getMyApplication()
            .setOnSendClientDataCallback(object : MyApplication.OnSendClientDataCallback {
                override fun onSendClientData(parcelFileDescriptor: ParcelFileDescriptor?) {
                    if (parcelFileDescriptor != null) {
                        Server2Client(parcelFileDescriptor)
                    }
                }
            })
    }

    @Synchronized
    private fun Server2Client(parcelFileDescriptor: ParcelFileDescriptor) {
        val n = callbackList!!.beginBroadcast()
        for (i in 0 until n) {
            val broadcastItem: ICallbackInterface? = callbackList!!.getBroadcastItem(i)
            if (broadcastItem != null) {
                try {
                    broadcastItem.server2client(parcelFileDescriptor)
                } catch (e: RemoteException) {
                    throw java.lang.RuntimeException(e)
                }
            }
        }
        callbackList!!.finishBroadcast()
    }

}