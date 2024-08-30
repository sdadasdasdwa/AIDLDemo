package com.example.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View

        .OnClickListener, MyApplication.OnGetClientDataCallback {

    private ImageView ivPic;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPic = findViewById(R.id.iv_pic);
        bt = findViewById(R.id.btn_send_to_client);
        bt.setOnClickListener(this);

        // 设置回调监听接口
        MyApplication.getMyApplication().setOnGetClientDataCallback(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_send_to_client) {
            client2server();
        }
    }

    private void client2server() {
        Log.d("pjjj", "client2server");
    }

    @Override
    public void onGetClientData(byte[] bytes) {
        if (bytes != null && bytes.length > 0)  {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            if(bitmap!=null){
                ivPic.setImageBitmap(bitmap);
            }
        }
    }
}