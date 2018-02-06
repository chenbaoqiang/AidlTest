// INewBookListener.aidl
package com.feinno.aidl;

import com.feinno.aidl.model.Book;

interface INewBookListener {

    void onNewBookArrived(in Book newBook);
}
