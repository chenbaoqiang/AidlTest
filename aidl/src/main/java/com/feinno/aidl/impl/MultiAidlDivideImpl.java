package com.feinno.aidl.impl;

import android.os.RemoteException;

import com.feinno.aidl.IMultiAidlDivide;

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
public class MultiAidlDivideImpl extends IMultiAidlDivide.Stub {

    @Override
    public int divide(int a, int b) throws RemoteException {
        return a / b;
    }
}
