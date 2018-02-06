package com.feinno.aidl.helper;

import android.os.Handler;
import android.util.Log;

import com.feinno.aidl.callback.IRetryBindEvent;
import com.feinno.common.AidlModuleConfig;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/7
 * 项目名：RCSNative - Android客户端<br>
 * 描述：绑定服务控制，防止绑定服务进入死循环
 *
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class BindServiceHelper {

    private final String TAG = AidlModuleConfig.AidlLogDefault + "BindServiceHelper";
    private final int gMaxLoginCount = 10;
    private Handler mHandler;
    private int mRetryCount;
    private IRetryBindEvent mRetryBindEvent;

    public BindServiceHelper(IRetryBindEvent retryBindEvent) {
        this.mRetryBindEvent = retryBindEvent;
        this.mHandler = new Handler();
    }

    /**
     * 确认绑定服务
     */
    public void ensureBindService() {
        Log.i(TAG, "ensureBindService,mRetryCount = " + mRetryCount);
        mRetryCount = 1;
        bind();
    }

    /**
     * 绑定服务成功，重置重试次数
     */
    public void bindServiceSuccess() {
        mRetryCount = 1;
    }

    /**
     * 服务绑定断开，重新绑定
     */
    public void retryBindService() {
        if (mRetryCount <= 1) {
            mRetryCount = 1;
        }
        if (mRetryCount > gMaxLoginCount) {
            mRetryCount = gMaxLoginCount;
        }
        int nextTime = (int) Math.pow(2, mRetryCount) * 1000;
        Log.i(TAG, "retryBinder,mRetryCount = " + mRetryCount + " ;nextTime = " + nextTime);

        mHandler.postDelayed(runnable, nextTime);
        mRetryCount++;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            bind();
        }
    };

    private void bind() {
        if (mRetryBindEvent != null) {
            mRetryBindEvent.onBind();
        }
    }

}
