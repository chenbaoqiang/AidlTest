package com.feinno.third.ui;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.feinno.aidl.INewBookListener;
import com.feinno.aidl.model.Book;
import com.feinno.aidl.proxy.BookManagerProxy;
import com.feinno.common.AidlModuleConfig;
import com.feinno.third.R;
import com.feinno.third.ui.base.BaseActivity;

import java.util.List;

public class BookManagerActivity extends BaseActivity {

    private final String TAG = AidlModuleConfig.AidlLogThird + BookManagerActivity.class.getSimpleName();

    private Button mBtnGetBookList, mBtnAddBook, mBtnRegister, mBtnUnregister, mBtnAddBookIn, mBtnAddBookOut, mBtnAddBookInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
    }


    @Override
    public void initView() {
        mBtnGetBookList = findViewById(R.id.id_getBookList);
        mBtnAddBook = findViewById(R.id.id_addBook);
        mBtnRegister = findViewById(R.id.id_registerListener);
        mBtnUnregister = findViewById(R.id.id_unregisterListener);
        mBtnAddBookIn = findViewById(R.id.id_addBookIn);
        mBtnAddBookOut = findViewById(R.id.id_addBookOut);
        mBtnAddBookInOut = findViewById(R.id.id_addBookInOut);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setWidgetListener() {
        mBtnGetBookList.setOnClickListener(this);
        mBtnAddBook.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnUnregister.setOnClickListener(this);
        mBtnAddBookIn.setOnClickListener(this);
        mBtnAddBookOut.setOnClickListener(this);
        mBtnAddBookInOut.setOnClickListener(this);
    }

    @Override
    public void onNoDoubleClick(View view) {
        int id = view.getId();
        if (id == R.id.id_getBookList) {
            getBookList();

        } else if (id == R.id.id_addBook) {
            addBook();

        } else if (id == R.id.id_registerListener) {
            registerListener();

        } else if (id == R.id.id_unregisterListener) {
            unregisterListener();

        } else if (id == R.id.id_addBookIn) {
            addBookIn();

        } else if (id == R.id.id_addBookOut) {
            addBookOut();

        } else if (id == R.id.id_addBookInOut) {
            addBookInOut();

        }
    }

    private INewBookListener.Stub mListener = new INewBookListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.e(TAG, "onNewBookArrived newBook = " + newBook);
        }
    };

    private void getBookList() {
        List<Book> bookArrayList = BookManagerProxy.getInstance().getBookList();
        Log.e(TAG, "getBookList bookArrayList.size() = " + bookArrayList.size());
        Log.e(TAG, "getBookList bookArrayList = " + bookArrayList);
    }

    private void addBook() {
        Book book = new Book(18, "测试书籍1");
        boolean bAdded = BookManagerProxy.getInstance().addBook(book);
        Log.e(TAG, "addBook bAdded = " + bAdded);
    }

    private void registerListener() {
        BookManagerProxy.getInstance().registerListener(mListener);
    }

    private void unregisterListener() {
        BookManagerProxy.getInstance().unregisterListener(mListener);
    }

    private void addBookIn() {
        Book book = new Book(55, "Android开发书籍");
        Log.e(TAG, "addBookIn before book = " + book);
        Book bookIn = BookManagerProxy.getInstance().addBookIn(book);
        Log.e(TAG, "addBookIn after book = " + book);
        Log.e(TAG, "addBookIn after bookIn = " + bookIn);
    }

    private void addBookOut() {
        Book book = new Book(55, "Android开发书籍");
        Log.e(TAG, "addBookOut before book = " + book);
        Book bookOut = BookManagerProxy.getInstance().addBookOut(book);
        Log.e(TAG, "addBookOut after book = " + book);
        Log.e(TAG, "addBookOut after bookOut = " + bookOut);
    }

    private void addBookInOut() {
        Book book = new Book(55, "Android开发书籍");
        Log.e(TAG, "addBookInOut before book = " + book);
        Book bookInOut = BookManagerProxy.getInstance().addBookInOut(book);
        Log.e(TAG, "addBookInOut after book = " + book);
        Log.e(TAG, "addBookInOut after bookInOut = " + bookInOut);
    }


}
