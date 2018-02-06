package com.feinno.aidl.iinterface;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/26
 * 项目名：RCSNative - Android客户端<br>
 * 描述：求矩形的面积和周长
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public interface IRect extends IInterface {

    int area(int length, int width) throws RemoteException;

    int perimeter(int length, int width) throws RemoteException;

}
