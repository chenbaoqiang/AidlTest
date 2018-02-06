package com.feinno.aidl.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.feinno.aidl.iinterface.IRect;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/26
 * 项目名：RCSNative - Android客户端<br>
 * 描述：
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public abstract class RectStub extends Binder implements IRect {
    private static final String TAG = AidlModuleConfig.AidlLogTest + RectStub.class.getSimpleName();

    private static final String DESCRIPTOR = "com.feinno.aidl.stub.RectStub";

    private static final int TRANSACT_area = IBinder.FIRST_CALL_TRANSACTION + 0;
    private static final int TRANSACT_perimeter = IBinder.FIRST_CALL_TRANSACTION + 1;

    public RectStub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public static IRect asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        Log.e(TAG, "asInterface iInterface = " + iInterface);
        if (iInterface != null && iInterface instanceof IRect) {
            return (IRect) iInterface;
        }
        return new Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == INTERFACE_TRANSACTION) {
            reply.writeString(DESCRIPTOR);
            return true;

        } else if (code == TRANSACT_area) {
            data.enforceInterface(DESCRIPTOR);
            int length = data.readInt();
            int width = data.readInt();
            int result = this.area(length, width);
            reply.writeNoException();
            reply.writeInt(result);
            return true;

        } else if (code == TRANSACT_perimeter) {
            data.enforceInterface(DESCRIPTOR);
            int length = data.readInt();
            int width = data.readInt();
            int result = this.perimeter(length, width);
            reply.writeNoException();
            reply.writeInt(result);
            return true;

        }
        return super.onTransact(code, data, reply, flags);
    }

    public static class Proxy implements IRect {

        private IBinder mRemote;

        public Proxy(IBinder remote) {
            this.mRemote = remote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public int area(int length, int width) throws RemoteException {
            int result;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeInt(length);
                data.writeInt(width);
                mRemote.transact(TRANSACT_area, data, reply, 0);
                reply.readException();
                result = reply.readInt();
            } finally {
                data.recycle();
                reply.recycle();
            }
            return result;
        }

        @Override
        public int perimeter(int length, int width) throws RemoteException {
            int result;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeInt(length);
                data.writeInt(width);
                mRemote.transact(TRANSACT_perimeter, data, reply, 0);
                reply.readException();
                result = reply.readInt();
            } finally {
                data.recycle();
                reply.recycle();
            }
            return result;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }

}
