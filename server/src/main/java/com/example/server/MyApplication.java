package com.example.server;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MyApplication extends Application {


    public static MyApplication application;


    protected final Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 在这里处理来自子线程的消息
            // 这里的代码会在主线程中执行
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    // 处理bytes数组
                    getDataCallback.onGetClientData(bytes);
                    break;

                default:
                    break;
            }
        }
    };
    private OnGetClientDataCallback getDataCallback = null;
    private OnSendClientDataCallback sendDataCallback = null;

    /**
     * 从客户端接收到数据后，回调
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getMyApplication(){
        return application;
    }

    interface OnGetClientDataCallback{
        void onGetClientData(byte[] bytes);
    }

    public void setOnGetClientDataCallback(OnGetClientDataCallback onGetClientDataCallback){
        this.getDataCallback = onGetClientDataCallback;
    }

    interface OnSendClientDataCallback{
        void onSendClientData(byte[] bytes);
    }

    public void setOnSendClientDataCallback(OnSendClientDataCallback onSendClientDataCallback){
        this.sendDataCallback = onSendClientDataCallback;
    }
    




}
