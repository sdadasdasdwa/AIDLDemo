package com.example.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.MemoryFile;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

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
            ServerToClient();
        }
    }

    private void ServerToClient() {
        try {
            // 图片bitmap ->inputStream -> byteArray  ->  MemoryFile ->  FileDescriptor -> ParcelFileDescriptor
            InputStream inputStream = getAssets().open("2.jpg");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                byte[] bytes = inputStream.readAllBytes();
                MemoryFile memoryFile = new MemoryFile("server_image",bytes.length);
                memoryFile.writeBytes(bytes,0,0,bytes.length);
                FileDescriptor fileDescriptor = MemoryFileUtils.getFileDescriptor(memoryFile);
                // 将fd转换为pfd
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.dup(fileDescriptor);
                // 接下来应该去调用客户端的接口了
                Message message = new Message();
                message.what = 2;
                message.obj = parcelFileDescriptor;
                //
                MyApplication.getMyApplication().mhandler.sendMessage(message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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