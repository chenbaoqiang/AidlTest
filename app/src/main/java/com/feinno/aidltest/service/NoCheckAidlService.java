package com.feinno.aidltest.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.feinno.aidl.INoCheckAdd;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：不需要检查权限的服务
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class NoCheckAidlService extends Service {

    private final String TAG = AidlModuleConfig.AidlLogTest + "NoCheckAidlService";

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "NoCheckAidlService onCreate");
    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "NoCheckAidlService onBind");
        return binder;
    }

    private INoCheckAdd.Stub binder = new INoCheckAdd.Stub() {
        @Override
        public synchronized int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };

}
