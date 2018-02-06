package com.feinno.aidl.callback;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/7
 * 项目名：RCSNative - Android客户端<br>
 * 描述：定时绑定service监听
 *
 * @version 1.0
 * @since JDK1.8.0_152
 */
public interface IRetryBindEvent {

    /**
     * 绑定service
     */
    void onBind();

}
