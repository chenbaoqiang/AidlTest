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
import com.feinno.aidl.iinterface.IRect;
import com.feinno.aidl.stub.RectStub;
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
public class CustomProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + "CheckProxy";

    private IRect mService;

    private static CustomProxy mInstance;

    private CustomProxy(Context context) {
        super(context);
    }

    public static CustomProxy createInstance(Context context) {
        mInstance = new CustomProxy(context);
        return mInstance;
    }

    public static CustomProxy getInstance() {
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
                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_CUSTOM);
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = mContext.startService(intent);
                intent.setComponent(co);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected");
                        mService = RectStub.asInterface(service);
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

    public int area(int length, int width) {
        if (mService != null) {
            try {
                Log.i(TAG, "area length : " + length + " ,width : " + width);
                return mService.area(length, width);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "area RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "area Exception: ", e);
            }
        } else {
            Log.e(TAG, "area mService == null");
            ensureBindService();
        }
        return 0;
    }

    public int perimeter(int length, int width) {
        if (mService != null) {
            try {
                Log.i(TAG, "perimeter length : " + length + " ,width : " + width);
                return mService.perimeter(length, width);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "perimeter RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "perimeter Exception: ", e);
            }
        } else {
            Log.e(TAG, "perimeter mService == null");
            ensureBindService();
        }
        return 0;
    }

}
