package com.article.oa_article.view.splash.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.article.oa_article.base.BaseActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/413:53
 * desc   :
 * version: 1.0
 */
public class GuiDeAct3 extends BaseActivity {
    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
