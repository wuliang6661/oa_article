package com.article.oa_article.module.complanydetails;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 公司详情展示
 */

public class ComplanyDetailsFragment extends MVPBaseFragment<ComplanyDetailsContract.View, ComplanyDetailsPresenter>
        implements ComplanyDetailsContract.View {


    @BindView(R.id.gongsi_img_recycle)
    RecyclerView gongsiImgRecycle;
    @BindView(R.id.gongsi_all_name)
    TextView gongsiAllName;
    @BindView(R.id.gongsi_jian_name)
    TextView gongsiJianName;
    @BindView(R.id.gongsi_address)
    TextView gongsiAddress;
    @BindView(R.id.gongsi_phone)
    TextView gongsiPhone;
    @BindView(R.id.gongsi_email)
    TextView gongsiEmail;
    Unbinder unbinder;

    ComplanBO complanBo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complany_details, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setComplanBo(ComplanBO complanBo) {
        this.complanBo = complanBo;

    }

}
