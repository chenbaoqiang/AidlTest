package com.feinno.aidl.base;

import android.content.Context;

import com.feinno.aidl.callback.IProxyBindListener;
import com.feinno.aidl.callback.IRetryBindEvent;
import com.feinno.aidl.helper.BindServiceHelper;

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
public abstract class BaseProxy implements IRetryBindEvent {

    protected Context mContext;
    protected BindServiceHelper mBindServiceHelper;

    /**
     * 绑定服务
     */
    public abstract void bindService();

    /**
     * 绑定服务
     *
     * @param listener 绑定服务监听
     */
    public abstract void bindService(final IProxyBindListener listener);

    /**
     * 服务是否活着或者服务所在进程是否活着
     *
     * @return 服务是否活着或者服务所在进程是否活着
     */
    public abstract boolean serviceAlive();

    public BaseProxy(Context context) {
        this.mContext = context.getApplicationContext();
        this.mBindServiceHelper = new BindServiceHelper(this);
    }

    /**
     * 绑定服务成功
     */
    public void bindServiceSuccess() {
        mBindServiceHelper.bindServiceSuccess();
    }

    /**
     * 带时间间隔带绑定服务
     */
    public void retryBindService() {
        mBindServiceHelper.retryBindService();
    }

    /**
     * 立即绑定服务
     */
    public void ensureBindService() {
        mBindServiceHelper.ensureBindService();
    }

    @Override
    public void onBind() {
        bindService();
    }
}
