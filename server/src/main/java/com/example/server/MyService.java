package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.frank.aidldemo.ClientToServer;

public class MyService extends Service {

    private final String TAG = "Ms.CG";


    private final ClientToServer.Stub mbinder =new ClientToServer.Stub(){

        @Override
        public String client2server(String param) throws RemoteException {
            Log.d(TAG, "client2server param: " + param);
            return "server has received";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }
}
