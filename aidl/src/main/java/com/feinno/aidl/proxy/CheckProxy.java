package com.feinno.aidl.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.feinno.aidl.ICheckSubtract;
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
public class CheckProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + "CheckProxy";

    private ICheckSubtract mService;

    private static CheckProxy mInstance;

    private CheckProxy(Context context) {
        super(context);
    }

    public static CheckProxy createInstance(Context context) {
        mInstance = new CheckProxy(context);
        return mInstance;
    }

    public static CheckProxy getInstance() {
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
                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_CHECK);
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = mContext.startService(intent);
                intent.setComponent(co);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected");
                        mService = ICheckSubtract.Stub.asInterface(service);
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
        return mService != null;
    }

    public int subtract(int a, int b) {
        if (mService != null) {
            try {
                Log.i(TAG, "subtract a : " + a + " ,b : " + b);
                return mService.subtract(a, b);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "subtract RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "subtract Exception: ", e);
            }
        } else {
            Log.e(TAG, "subtract mService == null");
            ensureBindService();
        }
        return 0;
    }

}
