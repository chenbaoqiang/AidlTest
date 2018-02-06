package com.feinno.third.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

/**
 * 版权所有 新媒传信科技有限公司。保留所有权利。<br>
 * 作者：zhangfangmin on 2018/1/22
 * 项目名：通用工具类库 - Android客户端<br>
 * 描述：activity基类
 *
 * @author zhangfangmin
 * @version 1.0
 * @since JDK1.8.0_152
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    /**
     * 快速点击
     */
    public static final int MIN_CLICK_DELAY_TIME = 300;

    private long mLastClickTime = 0;

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置控件监听事件
     */
    public abstract void setWidgetListener();

    /**
     * 防止重复点击控制方法
     *
     * @param view 被点击的view
     */
    public abstract void onNoDoubleClick(View view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        initView();
        initData();
        setWidgetListener();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        initView();
        initData();
        setWidgetListener();
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > MIN_CLICK_DELAY_TIME) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param alpha 0.0f~1.0f
     */
    protected void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }
}
