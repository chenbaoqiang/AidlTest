package com.feinno.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.feinno.aidl.IBookManager;
import com.feinno.aidl.INewBookListener;
import com.feinno.aidl.model.Book;
import com.feinno.common.AidlConfig;
import com.feinno.common.AidlModuleConfig;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/25
 * 项目名：RCSNative - Android客户端<br>
 * 描述：不需要检查权限的服务
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class BookManagerService extends Service {

    private final String TAG = AidlModuleConfig.AidlLogTest + BookManagerService.class.getSimpleName();

    private AtomicBoolean bDestroy;
    private CopyOnWriteArrayList<Book> mBookList;
    private CopyOnWriteArrayList<INewBookListener> mNewBookListeners;
    private RemoteCallbackList<INewBookListener> mListenerList;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "BookManagerService onCreate");
        bDestroy = new AtomicBoolean(false);
        mBookList = new CopyOnWriteArrayList<>();
//        mNewBookListeners = new CopyOnWriteArrayList<>();
        mListenerList = new RemoteCallbackList<>();

        mBookList.add(new Book(21, "书籍1"));
        mBookList.add(new Book(22, "书籍2"));
        mBookList.add(new Book(23, "书籍3"));
        mBookList.add(new Book(24, "书籍4"));

//        new Thread(new ServiceWork()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        boolean bCheck = checkCallingOrSelfPermission(AidlConfig.SERVICE_PERMISSION_CHECK) != PackageManager.PERMISSION_DENIED;
        Log.e(TAG, "BookManagerService onBind bCheck = " + bCheck);
        return bCheck ? mBinder : null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "BookManagerService onDestroy");
        bDestroy.set(true);
    }

    private IBookManager.Stub mBinder = new IBookManager.Stub() {

        @Override
        public synchronized List<Book> getBookList() throws RemoteException {
            Thread currentThread = Thread.currentThread();
            Log.e(TAG, "getBookList currentThread, id = " + currentThread.getId() + " ,name = " + currentThread.getName());
            return mBookList;
        }

        @Override
        public synchronized boolean addBook(Book book) throws RemoteException {
            Log.e(TAG, "addBook book = " + book);
            mBookList.add(book);
            return true;
        }

        @Override
        public synchronized void registerListener(INewBookListener listener) throws RemoteException {
//            if (mNewBookListeners.contains(listener)) {
//                Log.i(TAG, "registerListener listener already  exist");
//            } else {
//                mNewBookListeners.add(listener);
//                Log.i(TAG, "registerListener listener success");
//            }
//            Log.i(TAG, "registerListener mNewBookListeners.size() = " + mNewBookListeners.size());

            boolean bReg = mListenerList.register(listener);
            Log.i(TAG, "registerListener bReg = " + bReg);
        }

        @Override
        public synchronized void unregisterListener(INewBookListener listener) throws RemoteException {
//            if (!mNewBookListeners.contains(listener)) {
//                Log.i(TAG, "unregisterListener listener not found");
//            } else {
//                mNewBookListeners.remove(listener);
//                Log.i(TAG, "unregisterListener success");
//            }
//            Log.i(TAG, "unregisterListener mNewBookListeners.size() = " + mNewBookListeners.size());

            boolean bUnReg = mListenerList.unregister(listener);
            Log.i(TAG, "unregisterListener bUnReg = " + bUnReg);
        }

        @Override
        public Book addBookIn(Book book) throws RemoteException {
            Log.e(TAG, "addBookIn before book = " + book);
            book.setPrice(666);
            mBookList.add(book);
            Log.e(TAG, "addBookIn after book = " + book);
            return book;
        }

        @Override
        public Book addBookOut(Book book) throws RemoteException {
            Log.e(TAG, "addBookOut before book = " + book);
            book.setPrice(666);
            mBookList.add(book);
            Log.e(TAG, "addBookOut after book = " + book);
            return book;
        }

        @Override
        public Book addBookInOut(Book book) throws RemoteException {
            Log.e(TAG, "addBookInOut before book = " + book);
            book.setPrice(666);
            mBookList.add(book);
            Log.e(TAG, "addBookInOut after book = " + book);
            return book;
        }
    };

    private class ServiceWork implements Runnable {

        @Override
        public void run() {
            while (!bDestroy.get()) {
                try {
                    Thread.sleep(5000);

                    int size = mBookList.size();
                    float price = mBookList.get(size - 1).getPrice() + 1;
                    Book book = new Book(price, "new book " + (size + 1));
                    onNewBookArrived(book);

                } catch (InterruptedException e) {
                    Log.e(TAG, "ServiceWork InterruptedException: ", e);
                } catch (RemoteException e) {
                    Log.e(TAG, "ServiceWork RemoteException: ", e);

                }
            }

        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        Log.i(TAG, "onNewBookArrived book = " + book);
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            INewBookListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mListenerList.finishBroadcast();
    }

}
