package com.java.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.frank.aidldemo.Book;
import com.frank.aidldemo.ClientToServer;
import com.frank.aidldemo.R;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivClient;
    private Button btBind;
    private Button btUnBind;
    private Button btClientToServer;

    private ClientToServer mStub = null;
    private final String TAG = "Ms.CG";

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, binder = $binder");
            mStub = ClientToServer.Stub.asInterface(service);
            if (mStub != null) {
                try {
                    mStub.sendBook(new Book("I'm Ms.CG"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mStub = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {
        ivClient = findViewById(R.id.iv_pic);
        btBind = findViewById(R.id.btn_bind_service);
        btUnBind = findViewById(R.id.btn_unbind_service);
        btClientToServer = findViewById(R.id.btn_send_to_server);
    }

    private void initData() {
        ivClient.setOnClickListener(this);
        btBind.setOnClickListener(this);
        btUnBind.setOnClickListener(this);
        btClientToServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_pic) {
            Log.d(TAG, "you click Image");
        } else if (id == R.id.btn_bind_service) {
            Log.d(TAG, "you click btn_bind_service");
            bindService();
        } else if (id == R.id.btn_unbind_service) {
            Log.d(TAG, "you click btn_unbind_service");
            unbindService();
        } else if (id == R.id.btn_send_to_server) {
            Log.d(TAG, "you click btn_send_to_server");
            sendToServer();
        } else {
            Log.d(TAG, "Error click");
        }
    }

    private void sendToServer(){
        if (mStub == null) {
            return;
        }
        try {
            InputStream inputStream = getAssets().open("people.jpg");
            // bitmap -> inputStream -> byteArray  -> fileDescriptor -> ParcelFileDescriptor
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                byte[] bytes = inputStream.readAllBytes();

                MemoryFile memoryFile = new MemoryFile("client_image", bytes.length);
                // 向memoryFile中写入字节数组
                memoryFile.writeBytes(bytes, 0, 0, bytes.length);
                // 通过调用反射函数将memoryFile转换为FileDescriptor
                FileDescriptor fileDescriptor = MemoryFileUtils.getFileDescriptor(memoryFile);

                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.dup(fileDescriptor);
                // dup 方法：这是一个静态方法，用于复制一个文件描述符。它接收一个 FileDescriptor 对象作为参数，
                // 并返回一个新的 ParcelFileDescriptor 对象，该对象与原始文件描述符关联。
                mStub.client2server(parcelFileDescriptor);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindService() {
        Intent intent = new Intent("com.example.server.AidlService");
        intent.setClassName("com.example.server", "com.example.server.MyService");
        boolean bindSucc = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (bindSucc) {
            Log.d(TAG, "bind ok");
        } else {
            Log.d(TAG, "bind fail");
        }

    }

    private void unbindService() {
        if(mStub!=null){
            unbindService(mServiceConnection);
            Log.d(TAG, "unbind success");
            mStub = null;
        }
    }

    @Override
    protected void onDestroy() {
        if(mStub != null){
//            try {
//                mStub.asBinder().unlinkToDeath(mDeathRecipient, 0);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}