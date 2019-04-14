package com.wul.oa_article.view.main.personlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 通讯录
 */

public class PersonListFragment extends MVPBaseFragment<PersonListContract.View, PersonListPresenter>
        implements PersonListContract.View {


    @BindView(R.id.comple_img)
    ImageView compleImg;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.neibu_text)
    TextView neibuText;
    @BindView(R.id.out_text)
    TextView outText;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.expand_list)
    ExpandableListView expandList;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_person_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup.LayoutParams params = line.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth() / 2;
        line.setLayoutParams(params);

        OrderQueryRequest request = new OrderQueryRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getNeiUsers(request);
    }


    @OnClick({R.id.neibu_text, R.id.out_text})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.neibu_text:
                TranslateAnimation animation = new TranslateAnimation(ScreenUtils.getScreenWidth() / 2,
                        0, 0, 0);
                animation.setFillAfter(true);
                animation.setDuration(500);
                // 给图片添加动画
                line.startAnimation(animation);
                break;
            case R.id.out_text:
                TranslateAnimation animation1 = new TranslateAnimation(0,
                        ScreenUtils.getScreenWidth() / 2, 0, 0);
                animation1.setFillAfter(true);
                animation1.setDuration(500);
                // 给图片添加动画
                line.startAnimation(animation1);
                break;
        }
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
