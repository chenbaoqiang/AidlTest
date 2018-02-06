package com.feinno.aidl.proxy;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.feinno.aidl.IBookManager;
import com.feinno.aidl.INewBookListener;
import com.feinno.aidl.base.BaseProxy;
import com.feinno.aidl.callback.IProxyBindListener;
import com.feinno.aidl.model.Book;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

import java.util.List;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/26
 * 项目名：RCSNative - Android客户端<br>
 * 描述：
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class BookManagerProxy extends BaseProxy {

    private final String TAG = AidlModuleConfig.AidlLogThird + BookManagerProxy.class.getSimpleName();

    private IBookManager mService;

    private static BookManagerProxy mInstance;

    private BookManagerProxy(Context context) {
        super(context);
    }

    public static BookManagerProxy createInstance(Context context) {
        mInstance = new BookManagerProxy(context);
        return mInstance;
    }

    public static BookManagerProxy getInstance() {
        return mInstance;
    }

    @Override
    public void bindService() {
        bindService(null);
    }

    @Override
    public void bindService(final IProxyBindListener listener) {
        try {
            if (mService == null) {
                Intent intent = new Intent(AidlConfig.SERVICE_ACTION_BOOK_MANAGER);
                intent.setPackage(AidlConfig.PACKAGE_AIDL);
                ComponentName co = mContext.startService(intent);
                intent.setComponent(co);
                boolean ret = mContext.bindService(intent, new ServiceConnection() {

                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected");
                        mService = IBookManager.Stub.asInterface(service);
                        bindServiceSuccess();
                        if (listener != null) {
                            listener.onServiceConnected();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected");
                        mService = null;
                        if (listener != null) {
                            listener.onServiceDisconnected();
                        }
                        retryBindService();
                    }
                }, Context.BIND_AUTO_CREATE);
                Log.e(TAG, "bindService co : " + co);
                Log.e(TAG, "bindService ret : " + ret);
            }
        } catch (Exception e) {
            Log.e(TAG, "bindService Exception : ", e);
        }
    }

    @Override
    public boolean serviceAlive() {
        return mService != null;
    }

    public List<Book> getBookList() {
        Log.i(TAG, "getBookList ");
        List<Book> bookList = null;
        if (mService != null) {
            try {
                bookList = mService.getBookList();
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "getBookList RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "getBookList Exception: ", e);
            }
        } else {
            Log.e(TAG, "getBookList mService == null");
            ensureBindService();
        }
        return bookList;
    }

    public boolean addBook(Book book) {
        Log.i(TAG, "getBookList book = " + book);
        boolean bAdded = false;
        if (mService != null) {
            try {
                bAdded = mService.addBook(book);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "getBookList RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "getBookList Exception: ", e);
            }
        } else {
            Log.e(TAG, "getBookList mService == null");
            ensureBindService();
        }
        return bAdded;
    }

    public void registerListener(INewBookListener listener) {
        Log.i(TAG, "registerListener");
        if (mService != null) {
            try {
                mService.registerListener(listener);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "registerListener RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "registerListener Exception: ", e);
            }
        } else {
            Log.e(TAG, "registerListener mService == null");
            ensureBindService();
        }
    }

    public void unregisterListener(INewBookListener listener) {
        Log.i(TAG, "unregisterListener");
        if (mService != null) {
            try {
                mService.unregisterListener(listener);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "unregisterListener RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "unregisterListener Exception: ", e);
            }
        } else {
            Log.e(TAG, "unregisterListener mService == null");
            ensureBindService();
        }
    }

    public Book addBookIn(Book book) {
        if (mService != null) {
            try {
                return mService.addBookIn(book);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "addBookIn RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "addBookIn Exception: ", e);
            }
        } else {
            Log.e(TAG, "addBookIn mService == null");
            ensureBindService();
        }
        return null;
    }

    public Book addBookOut(Book book) {
        if (mService != null) {
            try {
                return mService.addBookOut(book);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "addBookOut RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "addBookOut Exception: ", e);
            }
        } else {
            Log.e(TAG, "addBookOut mService == null");
            ensureBindService();
        }
        return null;
    }

    public Book addBookInOut(Book book) {
        if (mService != null) {
            try {
                return mService.addBookInOut(book);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    mService = null;
                    ensureBindService();
                }
                Log.e(TAG, "addBookInOut RemoteException: ", e);
            } catch (Exception e) {
                Log.e(TAG, "addBookInOut Exception: ", e);
            }
        } else {
            Log.e(TAG, "addBookInOut mService == null");
            ensureBindService();
        }
        return null;
    }

}
