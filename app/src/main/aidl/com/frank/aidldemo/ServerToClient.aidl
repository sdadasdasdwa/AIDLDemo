// ServerToClient.aidl
package com.frank.aidldemo;

interface ServerToClient {
   void server2client(in ParcelFileDescriptor pfd);
}