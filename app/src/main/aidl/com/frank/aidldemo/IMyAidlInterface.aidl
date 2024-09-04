// IMyAidlInterface.aidl
package com.frank.aidldemo;
import com.frank.aidldemo.ICallbackInterface;

interface IMyAidlInterface {

    String sendMsg(String param);

    void sendImage(in byte[]data);

     void client2server(in ParcelFileDescriptor pfd);

     void registerCallback(ICallbackInterface callback);

     void unregisterCallback(ICallbackInterface callback);


}