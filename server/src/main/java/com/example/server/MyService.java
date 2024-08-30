package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.frank.aidldemo.Book;
import com.frank.aidldemo.ClientToServer;
import com.frank.aidldemo.ServerToClient;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class MyService extends Service {

    private final String TAG = "Ms.CG";

    private RemoteCallbackList<ServerToClient> callbackList = new RemoteCallbackList<>();

    private final ClientToServer.Stub mbinder =new ClientToServer.Stub(){

        @Override
        public void sendBook(Book book) throws RemoteException {
            Log.d(TAG,"sendBook: " + book.toString());
        }


        @Override
        public void client2server(ParcelFileDescriptor pfd) throws RemoteException {
            Log.d(TAG, "客户端想服务端上传图片 " + pfd);
            FileDescriptor fileDescriptor = pfd.getFileDescriptor();
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            // 从fileInputStream那里读取字节数组
            byte[] bytes = null;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bytes = fileInputStream.readAllBytes();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, "byteArray size " + bytes.length);
            Message message = new Message();
            message.what = 1;
            message.obj = bytes;
            // Message用于应用内进行通信，这里是用于改变什么
            MyApplication.getMyApplication().mhandler.sendMessage(message);
        }

        @Override
        public void registerCallback(ServerToClient callback) throws RemoteException {
            callbackList.register(callback);
        }

        @Override
        public void unregisterCallback(ServerToClient callback) throws RemoteException {
            callbackList.unregister(callback);
        }


    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.getMyApplication().setOnSendClientDataCallback(new MyApplication.OnSendClientDataCallback() {
            @Override
            public void onSendClientData(ParcelFileDescriptor parcelFileDescriptor) {
                Server2Client(parcelFileDescriptor);
            }
        });
    }

    private synchronized  void Server2Client(ParcelFileDescriptor parcelFileDescriptor) {
        int n = callbackList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            ServerToClient broadcastItem = callbackList.getBroadcastItem(i);
            if (broadcastItem != null) {
                try {
                    broadcastItem.server2client(parcelFileDescriptor);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        callbackList.finishBroadcast();
    }

}
