package com.example.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.MemoryFile
import android.os.ParcelFileDescriptor
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.frank.aidldemo.ICallbackInterface
import com.frank.aidldemo.IMyAidlInterface
import com.frank.aidldemo.R
import com.frank.aidldemo.databinding.ActivityMainBinding
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG: String = "Ms.CG"

    private lateinit var binding: ActivityMainBinding

    private var mStub: IMyAidlInterface? = null

    private val callback: ICallbackInterface = object :ICallbackInterface.Stub(){
        override fun server2client(pfd: ParcelFileDescriptor?) {

        }


    }

    // 到服务端的连接
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, binder = $binder")
            mStub = IMyAidlInterface.Stub.asInterface(binder)
            mStub?.registerCallback(callback)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
            mStub = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBindService.setOnClickListener(this)
        binding.btnUnbindService.setOnClickListener(this)
        binding.btnSendToServer.setOnClickListener(this)
    }

    private fun bindService() {
        // 1.不允许在mStub不为null时进行连接
        if (mStub != null) {
            return
        }
        // 2.找到需要跨进程连接的服务
        val intent = Intent("com.example.aidl.server.AidlService")
        intent.setClassName("com.frank.aidldemo_server", "com.example.aidl_server.MyService")

        // 3.进行连接
        try {
            val bindSucc = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (bindSucc) {
                Log.d(TAG, "Connect Success")
            } else {
                Log.d(TAG, "Connect Fail")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun unbindService() {
        if (mStub != null) {
            unbindService(serviceConnection)
            Log.d(TAG, "unbindService")
            mStub = null
        }

    }

    override fun onClick(v: View?) {
        val id: Int? = v?.id
        when (id) {
            R.id.btn_bind_service -> {
                Log.d(TAG, "btn_bind_service")
                bindService()
            }

            R.id.btn_unbind_service -> {
                Log.d(TAG, "btn_unbind_service")
                unbindService()
            }

            R.id.btn_send_to_server -> {
                Log.d(TAG, "btn_send_to_server")
                sendLargeData()
            }

            else -> Log.d(TAG, "Click else")
        }
    }

    override fun onDestroy() {
        if (mStub != null) {
            try {
                mStub!!.unregisterCallback(callback)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            unbindService(serviceConnection)
        }
        super.onDestroy()
    }

    private fun sendSmallData() {
        if (mStub == null) {
            return
        }
        try {
            val byteArray = AssetUtils.openAssets(this, "client.png")
            if (byteArray != null) {
                mStub?.sendImage(byteArray)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "error:${e.message}")
        } catch (e: RemoteException) {
            e.printStackTrace()
            Log.e(TAG, "error:${e.message}")
        }
    }

    private fun sendLargeData() {
        if (mStub == null) {
            return
        }
        try {
            /**
             * 读取assets目录下文件
             */
            val inputStream = assets.open("client.png")

            /**
             * 将inputStream转换成字节数组
             */
            val byteArray = inputStream.readBytes()

            /**
             * 创建MemoryFile
             */
            val memoryFile = MemoryFile("client_image", byteArray.size)

            /**
             * 向MemoryFile中写入字节数组
             */
            memoryFile.writeBytes(byteArray, 0, 0, byteArray.size)

            /**
             * 获取MemoryFile对应的FileDescriptor
             */
            val fd = MemoryFileUtils.getFileDescriptor(memoryFile)

            /**
             * 根据FileDescriptor创建ParcelFileDescriptor
             */
            val pfd = ParcelFileDescriptor.dup(fd)

            /**
             * 发送数据
             */
            mStub?.client2server(pfd)

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }
}