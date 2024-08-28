// ClientToServer.aidl
package com.frank.aidldemo;

import com.frank.aidldemo.Book;

interface ClientToServer {

    void sendBook(in Book book);

    void  client2server(in ParcelFileDescriptor pfd);
}