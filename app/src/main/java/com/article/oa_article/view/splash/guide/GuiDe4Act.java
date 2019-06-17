package com.article.oa_article.view.splash.guide;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/414:01
 * desc   :
 * version: 1.0
 */
public class GuiDe4Act extends BaseActivity {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.next1)
    ImageView next1;
    @BindView(R.id.next2)
    ImageView next2;
    @BindView(R.id.next3)
    ImageView next3;
    @BindView(R.id.hint1)
    ImageView hint1;
    @BindView(R.id.hint2)
    ImageView hint2;
    @BindView(R.id.hint3)
    ImageView hint3;

    private int shunxu = 0; //默认是第一步

    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_guide4;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        next1.setOnClickListener(view -> {
            if (shunxu == 0) {
                gotoActivity(GuideAct5.class, false);
                shunxu = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hint1.setVisibility(View.GONE);
                        hint2.setVisibility(View.VISIBLE);
                        hint3.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });
        next2.setOnClickListener(view -> {
            if (shunxu == 1) {
                gotoActivity(GuideAct9.class, false);
                shunxu = 2;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hint1.setVisibility(View.GONE);
                        hint2.setVisibility(View.GONE);
                        hint3.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        });
        next3.setOnClickListener(view -> {
            if (shunxu == 2) {
                gotoActivity(GuideAct6.class, false);
            }
        });
        back.setOnClickListener(view -> finish());
    }
}
