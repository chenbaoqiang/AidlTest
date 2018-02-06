// IBinderPool.aidl
package com.feinno.aidl;

import android.os.IBinder;

// Declare any non-default types here with import statements

interface IBinderPool {

    IBinder queryBinder(int binderCode);
}
