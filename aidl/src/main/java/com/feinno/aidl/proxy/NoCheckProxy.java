package com.feinno.aidl.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.feinno.aidl.INoCheckAdd;
import com.feinno.aidl.base.BaseProxy;
import com.feinno.aidl.callback.IProxyBindListener;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class NoCheckProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + "NoCheckProxy";

    private INoCheckAdd mService;

    private static NoCheckProxy mInstance;

    private NoCheckProxy(Context context) {
        super(context);
    }

    public static NoCheckProxy createInstance(Context context) {
        mInstance = new NoCheckProxy(context);
        return mInstance;
    }

    public static NoCheckProxy getInstance() {
        return mInstance;
    }

    @Override
    public void bindService() {
        bindService(null);
    }

    @Override
    public void bindService(final IProxyBindListener listener) {
        try {
            if (mService == null) {
//                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_NO_CHECK);
//                intent.setPackage(AidlConfig.PACKAGE_AIDL);
//                ComponentName co = mContext.startService(intent);
//                intent.setComponent(co);

                Intent intent = new Intent();
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = new ComponentName("com.feinno.aidltest", "com.feinno.aidltest.service.NoCheckAidlService");
                intent.setComponent(co);
                mContext.startService(intent);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Thread currentThread = Thread.currentThread();
                        Log.e(TAG, "onServiceConnected currentThread, id = " + currentThread.getId() +
                                " ,name = " + currentThread.getName());
                        mService = INoCheckAdd.Stub.asInterface(service);
                        bindServiceSuccess();
                        if (listener != null) {
                            listener.onServiceConnected();
                        }
                        try {
                            service.linkToDeath(mDeathRecipient, 0);
                        } catch (RemoteException e) {
                            Log.e(TAG, "bindService linkToDeath RemoteException : ", e);
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected");
                        mService = null;
                        if (listener != null) {
                            listener.onServiceDisconnected();
                        }
                        retryBindService();
                    }
                }, Context.BIND_AUTO_CREATE);
                Log.e(TAG, "bindService co: " + co);
                Log.e(TAG, "bindService ret: " + ret);
            }
        } catch (Exception e) {
            Log.e(TAG, "bindService Exception : ", e);
        }
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Thread currentThread = Thread.currentThread();
            Log.i(TAG, "binderDied currentThread, id = " + currentThread.getId() +
                    " ,name = " + currentThread.getName());
            mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
        }
    };

    @Override
    public boolean serviceAlive() {
        return mService != null;
    }

    public int add(int a, int b) {
        if (mService != null) {
            try {
                Log.i(TAG, "add a : " + a + " ,b : " + b);
                return mService.add(a, b);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "add RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "add Exception: ", e);
            }
        } else {
            Log.e(TAG, "add mService == null");
            ensureBindService();
        }
        return 0;
    }

}
