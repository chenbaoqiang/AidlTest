// IBookManager.aidl
package com.feinno.aidl;

import com.feinno.aidl.INewBookListener;
import com.feinno.aidl.model.Book;

// Declare any non-default types here with import statements

interface IBookManager {

    List<Book> getBookList();

    boolean addBook(in Book book);

    void registerListener(INewBookListener listener);

    void unregisterListener(INewBookListener listener);

    Book addBookIn(in Book book);

    Book addBookOut(out Book book);

    Book addBookInOut(inout Book book);

}
