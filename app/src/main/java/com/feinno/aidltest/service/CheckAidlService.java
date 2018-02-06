package com.feinno.aidltest.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.feinno.aidl.ICheckSubtract;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：需要检查权限的服务
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class CheckAidlService extends Service {

    private final String TAG = AidlModuleConfig.AidlLogTest + "CheckAidlService";

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "CheckAidlService onCreate");
    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        boolean bCheck = checkCallingOrSelfPermission(AidlConfig.SERVICE_PERMISSION_CHECK) != PackageManager.PERMISSION_DENIED;
        Log.e(TAG, "CheckAidlService onBind bCheck = " + bCheck);
        return bCheck ? mBinder : null;
    }

    private ICheckSubtract.Stub mBinder = new ICheckSubtract.Stub() {

        @SuppressLint("LongLogTag")
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e(TAG, "onTransact packageName = " + packageName);
            if (TextUtils.isEmpty(packageName) || !packageName.startsWith(AidlConfig.PACKAGE_PREFIX)) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public synchronized int subtract(int a, int b) throws RemoteException {
            return a - b;
        }
    };

}
