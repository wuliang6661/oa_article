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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;

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
    @BindView(R.id.card_fanmian)
    ImageView cardFanmian;
    @BindView(R.id.card_zhengmian)
    ImageView cardZhengmian;
    @BindView(R.id.zhizhao)
    ImageView zhizhao;
    @BindView(R.id.card_line)
    View cardLine;
    @BindView(R.id.card_text)
    TextView cardText;
    @BindView(R.id.zhengmian_layout)
    RelativeLayout zhengmianLayout;
    @BindView(R.id.fanmian_layout)
    RelativeLayout fanmianLayout;
    @BindView(R.id.zhizhao_line)
    View zhizhaoLine;
    @BindView(R.id.zhizhao_layout)
    LinearLayout zhizhaoLayout;
    @BindView(R.id.card_layout)
    LinearLayout cardLayout;

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


    public void setShow(boolean isShow) {
        new Handler().post(() -> {
            if (isShow) {   //企业认证
                gongsiImgRecycle.setVisibility(View.GONE);
                cardLayout.setVisibility(View.VISIBLE);
                cardText.setVisibility(View.VISIBLE);
                cardLine.setVisibility(View.VISIBLE);
                zhizhaoLine.setVisibility(View.VISIBLE);
                zhizhaoLayout.setVisibility(View.VISIBLE);

                String fanmianUrl = complanBo.getCompanyInfos().getIdBackImage().url;
                if (!StringUtils.isEmpty(fanmianUrl)) {
                    Glide.with(getActivity()).load(fanmianUrl).into(cardFanmian);
                    fanmianLayout.setVisibility(View.VISIBLE);
                } else {
                    fanmianLayout.setVisibility(View.GONE);
                }
                String zhengmian = complanBo.getCompanyInfos().getIdFrontImage().url;
                if (!StringUtils.isEmpty(fanmianUrl)) {
                    Glide.with(getActivity()).load(zhengmian).into(cardZhengmian);
                    zhengmianLayout.setVisibility(View.VISIBLE);
                } else {
                    zhengmianLayout.setVisibility(View.GONE);
                }
                if (StringUtils.isEmpty(zhengmian) && StringUtils.isEmpty(fanmianUrl)) {
                    cardLine.setVisibility(View.GONE);
                    cardText.setVisibility(View.GONE);
                }
                String zhizhaoUrl = complanBo.getCompanyInfos().getBusinessLicense().url;
                if (!StringUtils.isEmpty(zhizhaoUrl)) {
                    Glide.with(getActivity()).load(zhizhaoUrl).into(zhizhao);
                    zhizhaoLayout.setVisibility(View.VISIBLE);
                    zhizhaoLine.setVisibility(View.VISIBLE);
                } else {
                    zhizhaoLayout.setVisibility(View.GONE);
                    zhizhaoLine.setVisibility(View.GONE);
                }
            } else {  //外部联系人名片
                gongsiImgRecycle.setVisibility(View.VISIBLE);
                cardLayout.setVisibility(View.GONE);
                cardText.setVisibility(View.GONE);
                cardLine.setVisibility(View.GONE);
                zhizhaoLine.setVisibility(View.GONE);
                zhizhaoLayout.setVisibility(View.GONE);
            }
        });
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
