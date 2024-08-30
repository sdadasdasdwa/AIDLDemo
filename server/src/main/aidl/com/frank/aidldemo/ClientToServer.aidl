// ClientToServer.aidl
package com.frank.aidldemo;

import com.frank.aidldemo.Book;
import com.frank.aidldemo.ServerToClient;

interface ClientToServer {

    void sendBook(in Book book);

    void  client2server(in ParcelFileDescriptor pfd);

      void registerCallback(in ServerToClient callback);

        void unregisterCallback(in ServerToClient callback);
}