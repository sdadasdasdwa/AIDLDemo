// ClientToServer.aidl
package com.frank.aidldemo;


interface ClientToServer {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}