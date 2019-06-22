package com.article.oa_article.view.splash.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.util.AppManager;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/413:23
 * desc   :
 * version: 1.0
 */
public class GuiDeAct2 extends BaseActivity {

    @BindView(R.id.hint_img)
    ImageView hintImg;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.close)
    ImageView close;

    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_guide2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hintImg.setOnClickListener(view -> gotoActivity(GuiDeAct3.class, false));
        back.setOnClickListener(view -> finish());
        close.setOnClickListener(view -> AppManager.getAppManager().goHome());
    }
}
