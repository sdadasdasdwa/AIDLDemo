/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.frank.aidldemo;
public interface ClientToServer extends android.os.IInterface
{
  /** Default implementation for ClientToServer. */
  public static class Default implements com.frank.aidldemo.ClientToServer
  {
    @Override public void sendBook(com.frank.aidldemo.Book book) throws android.os.RemoteException
    {
    }
    @Override public void client2server(android.os.ParcelFileDescriptor pfd) throws android.os.RemoteException
    {
    }
    @Override public void registerCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException
    {
    }
    @Override public void unregisterCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.frank.aidldemo.ClientToServer
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.frank.aidldemo.ClientToServer interface,
     * generating a proxy if needed.
     */
    public static com.frank.aidldemo.ClientToServer asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.frank.aidldemo.ClientToServer))) {
        return ((com.frank.aidldemo.ClientToServer)iin);
      }
      return new com.frank.aidldemo.ClientToServer.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_sendBook:
        {
          com.frank.aidldemo.Book _arg0;
          _arg0 = _Parcel.readTypedObject(data, com.frank.aidldemo.Book.CREATOR);
          this.sendBook(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_client2server:
        {
          android.os.ParcelFileDescriptor _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.os.ParcelFileDescriptor.CREATOR);
          this.client2server(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_registerCallback:
        {
          com.frank.aidldemo.ServerToClient _arg0;
          _arg0 = com.frank.aidldemo.ServerToClient.Stub.asInterface(data.readStrongBinder());
          this.registerCallback(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_unregisterCallback:
        {
          com.frank.aidldemo.ServerToClient _arg0;
          _arg0 = com.frank.aidldemo.ServerToClient.Stub.asInterface(data.readStrongBinder());
          this.unregisterCallback(_arg0);
          reply.writeNoException();
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.frank.aidldemo.ClientToServer
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void sendBook(com.frank.aidldemo.Book book) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, book, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendBook, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void client2server(android.os.ParcelFileDescriptor pfd) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, pfd, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_client2server, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void registerCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void unregisterCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_sendBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_client2server = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
  }
  public static final java.lang.String DESCRIPTOR = "com.frank.aidldemo.ClientToServer";
  public void sendBook(com.frank.aidldemo.Book book) throws android.os.RemoteException;
  public void client2server(android.os.ParcelFileDescriptor pfd) throws android.os.RemoteException;
  public void registerCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException;
  public void unregisterCallback(com.frank.aidldemo.ServerToClient callback) throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
