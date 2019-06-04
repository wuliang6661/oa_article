package com.article.oa_article.view.splash.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/414:19
 * desc   :
 * version: 1.0
 */
public class GuideAct6 extends BaseActivity {
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.back)
    LinearLayout back;

    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_guide6;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        back.setOnClickListener(view -> finish());
        next.setOnClickListener(view -> gotoActivity(GuideAct7.class, false));
    }
}
