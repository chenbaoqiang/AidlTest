package com.feinno.aidl.callback;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2017/12/27
 * 项目名：RCSNative - Android客户端<br>
 * 描述：服务绑定监听
 *
 * @version 1.0
 * @since JDK1.8.0_152
 */
public interface IProxyBindListener {

    /**
     * 绑定成功
     */
    void onServiceConnected();

    /**
     * 绑定失败
     */
    void onServiceDisconnected();

}
