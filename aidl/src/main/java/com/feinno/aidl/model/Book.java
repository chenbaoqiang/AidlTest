package com.feinno.aidl.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/26
 * 项目名：RCSNative - Android客户端<br>
 * 描述：书籍实体类
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public class Book implements Parcelable {

    private float price;

    private String name;

    protected Book(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.price = in.readFloat();
        this.name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(price);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }

    };

    public Book() {
    }

    public Book(float price, String name) {
        this.price = price;
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }

}
