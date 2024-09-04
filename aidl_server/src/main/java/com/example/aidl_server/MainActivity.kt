package com.example.aidl_server

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.MemoryFile
import android.os.Message
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.aidl_server.databinding.ActivityMainBinding
import java.io.FileDescriptor
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener,MyApplication.OnGetClientDataCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendToClient.setOnClickListener(this)

        MyApplication.getMyApplication().setOnGetClientDataCallback(this)
    }

    override fun onClick(v: View?) {
        val id : Int  = v!!.id
        if (id.equals(R.id.iv_pic)){
            Log.d("pjjj","Click iv_Pic")
        }else if(id.equals(R.id.btn_send_to_client)){
            Log.d("pjjj","Click Btn_Send_To_Client")
            ServerToClient()
        }

    }

    override fun onGetClientData(bytes: ByteArray?) {
        if (bytes != null && bytes.size > 0) {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            if (bitmap != null) {
                binding.ivPic.setImageBitmap(bitmap)
            }
        }
    }

    private fun ServerToClient() {
        try {
            // 图片bitmap ->inputStream -> byteArray  ->  MemoryFile ->  FileDescriptor -> ParcelFileDescriptor
            val inputStream = assets.open("server.png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val bytes = inputStream.readAllBytes()
                val memoryFile = MemoryFile("server_image", bytes.size)
                memoryFile.writeBytes(bytes, 0, 0, bytes.size)
                val fileDescriptor: FileDescriptor = MemoryFileUtils.getFileDescriptor(memoryFile)
                // 将fd转换为pfd
                val parcelFileDescriptor = ParcelFileDescriptor.dup(fileDescriptor)
                // 接下来应该去调用客户端的接口了
                val message: Message = Message()
                message.what = 2
                message.obj = parcelFileDescriptor
                //
                MyApplication.getMyApplication().mhandler.sendMessage(message)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}