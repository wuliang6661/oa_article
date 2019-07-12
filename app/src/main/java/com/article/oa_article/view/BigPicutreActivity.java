package com.article.oa_article.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.module.create_order.ImageBO;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查看大图
 */
public class BigPicutreActivity extends BaseActivity {

    @BindView(R.id.image_pager)
    ViewPager imagePager;

    List<ImageBO> imageBOS;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.back)
    LinearLayout back;

    private int selectPosition;

    List<ImageView> imageViews;

    @Override
    protected int getLayout() {
        return R.layout.act_big_picture;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        back.setOnClickListener(view -> {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                finishAfterTransition();
//            } else {
//                finish();
//            }
//        });
        goBack();
        imageBOS = (List<ImageBO>) getIntent().getSerializableExtra("imageBos");
        selectPosition = getIntent().getIntExtra("selectPosition", 0);

        setTitleText(imageBOS.get(selectPosition).name.length() > 9 ? imageBOS.get(selectPosition).name.substring(0, 9)
                + "..." : imageBOS.get(selectPosition).name);
        imageViews = new ArrayList<>();
        initView();
        imagePager.setAdapter(new MyPagerAdapter());

        imagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitleText(imageBOS.get(position).name.length() > 9 ? imageBOS.get(position).name.substring(0, 9)
                        + "..." : imageBOS.get(position).name);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imagePager.setCurrentItem(selectPosition);
    }


    private void initView() {
        for (int i = 0; i < imageBOS.size(); i++) {
            ImageView imageView = new ImageView(this);
//            ViewGroup.LayoutParams params = imageView.getLayoutParams();
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            imageView.setLayoutParams(params);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(imageBOS.get(i).url).into(imageView);
            imageViews.add(imageView);
        }
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageBOS.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
//            ImageView imageView = new ImageView(BigPicutreActivity.this);
////            ViewGroup.LayoutParams params = imageView.getLayoutParams();
////            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
////            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
////            imageView.setLayoutParams(params);
//
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            Glide.with(BigPicutreActivity.this).load(imageBOS.get(position).url).into(imageView);
//            view.addView(imageView);
            View groupView = LayoutInflater.from(BigPicutreActivity.this).inflate(R.layout.act_big_img, null);
            ImageView imageView = groupView.findViewById(R.id.iv_big_image);
            Glide.with(BigPicutreActivity.this).load(imageBOS.get(position).url).into(imageView);
            view.addView(groupView);
            return groupView;
        }

    }

}
