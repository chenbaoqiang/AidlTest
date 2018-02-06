package com.feinno.aidltest.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.feinno.aidl.IBinderPool;
import com.feinno.aidl.impl.MultiAidlDivideImpl;
import com.feinno.aidl.impl.MultiAidlRideImpl;
import com.feinno.aidl.proxy.MultiAidlProxy;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：通过Binder连接池方式，实现一个服务使用多个aidl实现IPC
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class MultiAidlService extends Service {

    private final String TAG = AidlModuleConfig.AidlLogTest + "MultiAidlService";
    private MultiAidlProxy.BinderPoolImpl mBinder;

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "MultiAidlService onCreate");
        mBinder = new MultiAidlProxy.BinderPoolImpl();
    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        boolean bCheck = checkCallingOrSelfPermission(AidlConfig.SERVICE_PERMISSION_CHECK) != PackageManager.PERMISSION_DENIED;
        Log.e(TAG, "MultiAidlService onBind bCheck = " + bCheck);
        return bCheck ? mBinder : null;
    }

}
