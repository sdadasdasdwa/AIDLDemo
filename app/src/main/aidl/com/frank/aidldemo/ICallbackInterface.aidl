// ICallbackInterface.aidl
package com.frank.aidldemo;

interface ICallbackInterface {
    void server2client(in ParcelFileDescriptor pfd);
}