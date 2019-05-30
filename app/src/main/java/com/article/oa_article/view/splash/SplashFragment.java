package com.article.oa_article.view.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.article.oa_article.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3014:33
 * desc   :
 * version: 1.0
 */
public class SplashFragment extends Fragment {


    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    ImageView title;
    Unbinder unbinder;

    public static SplashFragment getInstance(int type) {
        SplashFragment fragment = new SplashFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_splash, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int type = getArguments().getInt("type");
        switch (type) {
            case 0:
                image.setImageResource(R.drawable.splash_img1);
                title.setImageResource(R.drawable.splash_text1);
                break;
            case 1:
                image.setImageResource(R.drawable.splash_image2);
                title.setImageResource(R.drawable.splash_text2);
                break;
            case 2:
                image.setImageResource(R.drawable.splash_image3);
                title.setImageResource(R.drawable.splash_text3);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
