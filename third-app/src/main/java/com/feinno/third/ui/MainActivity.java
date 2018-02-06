package com.feinno.third.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.feinno.aidl.IMultiAidlDivide;
import com.feinno.aidl.IMultiAidlRide;
import com.feinno.aidl.impl.MultiAidlDivideImpl;
import com.feinno.aidl.impl.MultiAidlRideImpl;
import com.feinno.aidl.proxy.CheckProxy;
import com.feinno.aidl.proxy.CustomProxy;
import com.feinno.aidl.proxy.MessengerProxy;
import com.feinno.aidl.proxy.MultiAidlProxy;
import com.feinno.aidl.proxy.NoCheckProxy;
import com.feinno.common.AidlModuleConfig;
import com.feinno.third.R;
import com.feinno.third.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private final String TAG = AidlModuleConfig.AidlLogThird + MainActivity.class.getSimpleName();

    private Button mBtnNoCheck, mBtnCheck, mBtnCustom, mBtnMultiAidl, mBtnMessengerAidl, mBtnBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void initView() {
        mBtnNoCheck = findViewById(R.id.id_NoCheckAidlService);
        mBtnCheck = findViewById(R.id.id_CheckAidlService);
        mBtnCustom = findViewById(R.id.id_CustomService);
        mBtnMultiAidl = findViewById(R.id.id_MultiAidlService);
        mBtnMessengerAidl = findViewById(R.id.id_MessengerAidlService);
        mBtnBookManager = findViewById(R.id.id_BookManagerService);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setWidgetListener() {
        mBtnNoCheck.setOnClickListener(this);
        mBtnCheck.setOnClickListener(this);
        mBtnCustom.setOnClickListener(this);
        mBtnMultiAidl.setOnClickListener(this);
        mBtnMessengerAidl.setOnClickListener(this);
        mBtnBookManager.setOnClickListener(this);
    }

    @Override
    public void onNoDoubleClick(View view) {
        int id = view.getId();
        if (id == R.id.id_NoCheckAidlService) {
            add();

        } else if (id == R.id.id_CheckAidlService) {
            subtract();

        } else if (id == R.id.id_CustomService) {
            custom();

        } else if (id == R.id.id_MultiAidlService) {
            multi();

        } else if (id == R.id.id_MessengerAidlService) {
            sendMessage();

        } else if (id == R.id.id_BookManagerService) {
            gotoBookManagerActivity();

        }
    }

    private void add() {
        int addResult = NoCheckProxy.getInstance().add(10, 20);
        Log.e(TAG, "addResult = " + addResult);
    }

    private void subtract() {
        int subResult = CheckProxy.getInstance().subtract(50, 10);
        Log.e(TAG, "subResult = " + subResult);
    }

    private void custom() {
        int length = 5;
        int width = 4;
        Log.e(TAG, "custom length = " + length + " ,width = " + width);
        int resultArea = CustomProxy.getInstance().area(length, width);
        Log.e(TAG, "custom resultArea = " + resultArea);

        int resultPerimeter = CustomProxy.getInstance().perimeter(length, width);
        Log.e(TAG, "custom resultPerimeter = " + resultPerimeter);
    }

    private void multi() {
        IBinder binder = MultiAidlProxy.getInstance().queryBinder(MultiAidlProxy.BINDER_CODE_RIDE);
        IMultiAidlRide iMultiAidlRide = MultiAidlRideImpl.asInterface(binder);
        try {
            int rideResult = iMultiAidlRide.ride(10, 5);
            Log.e(TAG, "rideResult = " + rideResult);
        } catch (RemoteException e) {
            Log.e(TAG, "multi RemoteException: ", e);
        }

        IBinder binder1 = MultiAidlProxy.getInstance().queryBinder(MultiAidlProxy.BINDER_CODE_DIVIDE);
        IMultiAidlDivide iMultiAidlDivide = MultiAidlDivideImpl.asInterface(binder1);
        try {
            int divideResult = iMultiAidlDivide.divide(10, 5);
            Log.e(TAG, "divideResult = " + divideResult);
        } catch (RemoteException e) {
            Log.e(TAG, "multi RemoteException: ", e);
        }
    }

    private void sendMessage() {
        MessengerProxy.getInstance().sendMessage(MessengerProxy.WHAT_0, "This is client!");
    }

    private void gotoBookManagerActivity() {
        Intent intent = new Intent(this, BookManagerActivity.class);
        startActivity(intent);
    }

}
