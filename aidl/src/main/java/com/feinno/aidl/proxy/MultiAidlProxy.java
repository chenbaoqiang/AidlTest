package com.feinno.aidl.proxy;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.feinno.aidl.IBinderPool;
import com.feinno.aidl.base.BaseProxy;
import com.feinno.aidl.callback.IProxyBindListener;
import com.feinno.aidl.impl.MultiAidlDivideImpl;
import com.feinno.aidl.impl.MultiAidlRideImpl;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

import java.util.concurrent.CountDownLatch;

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
public class MultiAidlProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + "MultiAidlProxy";

    public final static int BINDER_CODE_RIDE = 0;
    public final static int BINDER_CODE_DIVIDE = 1;

    private IBinderPool mService;

    private static MultiAidlProxy mInstance;

    private MultiAidlProxy(Context context) {
        super(context);
    }

    public static MultiAidlProxy createInstance(Context context) {
        mInstance = new MultiAidlProxy(context);
        return mInstance;
    }

    public static MultiAidlProxy getInstance() {
        return mInstance;
    }

    @Override
    public void bindService() {
        bindService(null);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void bindService(final IProxyBindListener listener) {
        try {
            if (mService == null) {
                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_MULTI_AIDL);
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = mContext.startService(intent);
                intent.setComponent(co);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {

                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected");
                        mService = IBinderPool.Stub.asInterface(service);
                        bindServiceSuccess();
                        if (listener != null) {
                            listener.onServiceConnected();
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
                Log.e(TAG, "bindService co : " + co);
                Log.e(TAG, "bindService ret : " + ret);
            }
        } catch (Exception e) {
            Log.e(TAG, "bindService Exception : ", e);
        }
    }

    @Override
    public boolean serviceAlive() {
        return false;
    }

    @SuppressLint("LongLogTag")
    public IBinder queryBinder(int binderCode) {
        Log.i(TAG, "queryBinder");
        IBinder iBinder = null;
        if (mService != null) {
            try {
                iBinder = mService.queryBinder(binderCode);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "queryBinder RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "queryBinder Exception: ", e);
            }
        } else {
            Log.e(TAG, "queryBinder mService == null");
            ensureBindService();
        }
        return iBinder;
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {

        private final String TAG = AidlModuleConfig.AidlLogThird + "BinderPoolImpl";

        public BinderPoolImpl() {
            super();
        }

        @SuppressLint("LongLogTag")
        @Override
        public synchronized IBinder queryBinder(int binderCode) throws RemoteException {
            Log.e(TAG, "MultiAidlService queryBinder, binderCode = " + binderCode);
            IBinder iBinder = null;
            if (binderCode == BINDER_CODE_RIDE) {
                iBinder = new MultiAidlRideImpl();
            } else if (binderCode == BINDER_CODE_DIVIDE) {
                iBinder = new MultiAidlDivideImpl();
            }
            return iBinder;
        }
    }

}
