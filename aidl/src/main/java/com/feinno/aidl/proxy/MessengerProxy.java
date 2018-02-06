package com.feinno.aidl.proxy;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

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
public class MessengerProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + "MessengerProxy";
    public static final int WHAT_0 = 0;
    public static final int WHAT_1 = 1;

    private Messenger mService;
    private Messenger mReplyMessenger;

    private static MessengerProxy mInstance;

    private MessengerProxy(Context context) {
        super(context);
        mReplyMessenger = new Messenger(new MessengerHandler());
    }

    public static MessengerProxy createInstance(Context context) {
        mInstance = new MessengerProxy(context);
        return mInstance;
    }

    public static MessengerProxy getInstance() {
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
                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_MESSENGER);
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = mContext.startService(intent);
                intent.setComponent(co);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {


                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected");
                        mService = new Messenger(service);
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


    @SuppressLint("LongLogTag")
    public void sendMessage(int what, String content) {
        Message msg = Message.obtain();
        msg.what = what;
        Bundle bundle = new Bundle();
        bundle.putString("msg", content);
        msg.setData(bundle);
        msg.replyTo = mReplyMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            if (e instanceof DeadObjectException) {
                mService = null;
                ensureBindService();
            }
            Log.e(TAG, "sendMessage RemoteException : ", e);
        } catch (Exception e) {
            Log.e(TAG, "sendMessage Exception : ", e);
        }
    }


    private class MessengerHandler extends Handler {

        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_0) {
                Bundle bundle = msg.getData();
                String info = bundle.getString("msg");
                Log.e(TAG, "reply info: " + info);
            } else if (msg.what == WHAT_1) {

            } else {
                super.handleMessage(msg);
            }
        }

    }

}
