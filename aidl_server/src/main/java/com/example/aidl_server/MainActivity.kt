package com.example.aidl_server

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aidl_server.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnSendToClient.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id : Int  = v!!.id
        if (id.equals(R.id.iv_pic)){
            Log.d("pjjj","Click iv_Pic")
        }else if(id.equals(R.id.btn_send_to_client)){
            Log.d("pjjj","Click Btn_Send_To_Client")

        }

    }
}