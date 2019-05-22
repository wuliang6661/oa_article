package com.article.oa_article.module.complanydetails;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

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

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gongsiImgRecycle.setLayoutManager(manager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setComplanBo(ComplanBO complanBo) {
        this.complanBo = complanBo;
        new Handler().post(this::setUIMsg);
    }


    /**
     * 设置页面显示
     */
    private void setUIMsg() {
        gongsiAllName.setText(complanBo.getCompanyInfos().getCompanyName());
        gongsiJianName.setText(complanBo.getCompanyInfos().getShortName());
        gongsiAddress.setText(complanBo.getCompanyInfos().getCompanyAddress());
        gongsiPhone.setText(complanBo.getCompanyInfos().getContactWay());
        gongsiEmail.setText(complanBo.getCompanyInfos().getCompanyEmail());

        setAdapter();
    }


    private void setAdapter() {
        List<ImageBO> imageBOS = new ArrayList<>();
        imageBOS.add(complanBo.getCompanyInfos().getBusinessLicense());
        LGRecycleViewAdapter<ImageBO> adapter = new LGRecycleViewAdapter<ImageBO>(imageBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_image;
            }

            @Override
            public void convert(LGViewHolder holder, ImageBO imageBO, int position) {
                holder.setImageUrl(getActivity(), R.id.image, imageBO.url);
            }
        };
        gongsiImgRecycle.setAdapter(adapter);
    }
}
