package com.article.oa_article.view.splash.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.util.AppManager;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/413:53
 * desc   :
 * version: 1.0
 */
public class GuiDeAct3 extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.close)
    ImageView close;

    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_guide3;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.guide3_bg);
//        Bitmap b = Bitmap.createScaledBitmap(image, 1500, 2000, false);
//        bgImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bgImg.setImageBitmap(b);
        back.setOnClickListener(view -> finish());
        next.setOnClickListener(view -> gotoActivity(GuiDe4Act.class, false));
        close.setOnClickListener(view -> AppManager.getAppManager().goHome());
    }
}
