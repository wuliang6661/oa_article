package com.article.oa_article.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.blankj.utilcode.util.SizeUtils;
import com.example.xlhratingbar_lib.IRatingView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1616:52
 * desc   :
 * version: 1.0
 */
public class SimpleRatingView implements IRatingView {

    @Override
    public int getStateRes(int posi, int state) {
        int id = R.drawable.rating_off;
        switch (state) {
            case STATE_NONE:
                id = R.drawable.rating_off;
                break;
            case STATE_HALF:
                id = R.drawable.rating_off;
                break;
            case STATE_FULL:
                id = R.drawable.rating_on;
                break;
            default:
                break;
        }
        return id;
    }

    @Override
    public int getCurrentState(float rating, int numStars, int position) {
        position++;
        float dis = position - rating;
        if (dis <= 0) {
            return STATE_FULL;
        }
        if (dis == 0.5) {
            return STATE_HALF;
        }
        if (dis > 0.5) {
            return STATE_NONE;
        }
        return 0;
    }


    @Override
    public ImageView getRatingView(Context context, int numStars, int posi) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizeUtils.dp2px(20),
                SizeUtils.dp2px(20));
        params.setMarginStart(SizeUtils.dp2px(12));
        imageView.setLayoutParams(params);
        return imageView;
    }
}