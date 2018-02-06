package com.feinno.common;

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
public class AidlConfig {

    /**
     * 应用包名前缀
     */
    public static final String PACKAGE_PREFIX = "com.feinno";
    /**
     * 应用包名
     */
    public static final String PACKAGE_AIDL = PACKAGE_PREFIX + ".aidltest";
    /**
     * 第三方绑定服务的应用包名
     */
    public static final String PACKAGE_THIRD = PACKAGE_PREFIX + ".third";

    public static final String SERVICE_ACTION_NO_CHECK = "com.feinno.aidltest.service.NO_CHECK";
    public static final String SERVICE_ACTION_CHECK = "com.feinno.aidltest.service.CHECK";
    public static final String SERVICE_ACTION_CUSTOM = "com.feinno.aidltest.service.CUSTOM";
    public static final String SERVICE_ACTION_MULTI_AIDL = "com.feinno.aidltest.service.MULTI_AIDL";
    public static final String SERVICE_ACTION_MESSENGER = "com.feinno.aidltest.service.MESSENGER";
    public static final String SERVICE_ACTION_BOOK_MANAGER = "com.feinno.aidltest.service.BOOK_MANAGER";

    public static final String SERVICE_PERMISSION_CHECK = "com.feinno.aidltest.service.PERMISSION";

}
