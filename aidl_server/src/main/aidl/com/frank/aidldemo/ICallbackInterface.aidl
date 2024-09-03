// ICallbackInterface.aidl
package com.frank.aidldemo;
import com.framk.aidldemo.Book;

interface ICallbackInterface {
    void server2client(in ParcelFileDescriptor pfd);
}