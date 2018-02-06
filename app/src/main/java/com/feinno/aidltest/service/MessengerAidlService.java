package com.feinno.aidltest.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.feinno.aidl.proxy.MessengerProxy;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：通过messenger实现ipc的服务
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class MessengerAidlService extends Service {

    private final String TAG = AidlModuleConfig.AidlLogTest + "MessengerAidlService";

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "MessengerAidlService onCreate");
    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        boolean bCheck = checkCallingOrSelfPermission(AidlConfig.SERVICE_PERMISSION_CHECK) != PackageManager.PERMISSION_DENIED;
        Log.e(TAG, "MessengerAidlService onBind bCheck = " + bCheck);
        return bCheck ? mMessenger.getBinder() : null;
    }

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler {

        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MessengerProxy.WHAT_0) {
                Bundle bundle = msg.getData();
                String info = bundle.getString("msg");
                Log.e(TAG, "Server receive data: " + info);

                Messenger client = msg.replyTo;
                replyMessage(client, MessengerProxy.WHAT_0, "Server receive data success!");
            }
            super.handleMessage(msg);
        }
    }

    @SuppressLint("LongLogTag")
    private void replyMessage(Messenger client, int what, String info) {
        Message replyMsg = Message.obtain(null, what);
        Bundle replyBundle = new Bundle();
        replyBundle.putString("msg", info);
        replyMsg.setData(replyBundle);
        try {
            client.send(replyMsg);
        } catch (RemoteException e) {
            Log.e(TAG, "handleMessage RemoteException :", e);
        }
    }

}
