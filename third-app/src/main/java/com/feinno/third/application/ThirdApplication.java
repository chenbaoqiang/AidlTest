package com.feinno.third.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.feinno.aidl.callback.IProxyBindListener;
import com.feinno.aidl.proxy.BookManagerProxy;
import com.feinno.aidl.proxy.CheckProxy;
import com.feinno.aidl.proxy.CustomProxy;
import com.feinno.aidl.proxy.MessengerProxy;
import com.feinno.aidl.proxy.MultiAidlProxy;
import com.feinno.aidl.proxy.NoCheckProxy;
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
public class ThirdApplication extends Application {

    private final String TAG = AidlModuleConfig.AidlLogThird + "ThirdApplication";

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        NoCheckProxy.createInstance(this).bindService(new IProxyBindListener() {

            @Override
            public void onServiceConnected() {
                Log.e(TAG, "NoCheckProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "NoCheckProxy onServiceDisconnected");
            }
        });
        CheckProxy.createInstance(this).bindService(new IProxyBindListener() {
            @Override
            public void onServiceConnected() {
                Log.e(TAG, "CheckProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "CheckProxy onServiceDisconnected");
            }
        });
        CustomProxy.createInstance(this).bindService(new IProxyBindListener() {
            @Override
            public void onServiceConnected() {
                Log.e(TAG, "CustomProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "CustomProxy onServiceDisconnected");
            }
        });

        MultiAidlProxy.createInstance(this).bindService(new IProxyBindListener() {
            @Override
            public void onServiceConnected() {
                Log.e(TAG, "MultiAidlProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "MultiAidlProxy onServiceDisconnected");
            }
        });
        MessengerProxy.createInstance(this).bindService(new IProxyBindListener() {
            @Override
            public void onServiceConnected() {
                Log.e(TAG, "MessengerProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "MessengerProxy onServiceDisconnected");
            }
        });
        BookManagerProxy.createInstance(this).bindService(new IProxyBindListener() {
            @Override
            public void onServiceConnected() {
                Log.e(TAG, "BookManagerProxy onServiceConnected");
            }

            @Override
            public void onServiceDisconnected() {
                Log.e(TAG, "BookManagerProxy onServiceDisconnected");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
