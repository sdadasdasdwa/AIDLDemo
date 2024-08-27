// ClientToServer.aidl
package com.frank.aidldemo;

import com.frank.aidldemo.Book;

interface ClientToServer {

    void sendBook(in Book book);

    String client2server(String param);
}