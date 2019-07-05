package com.article.oa_article.module.complanyzizhi;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
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
 * 公司资质详情
 */

public class ComplanyZizhiFragment extends MVPBaseFragment<ComplanyZizhiContract.View, ComplanyZizhiPresenter>
        implements ComplanyZizhiContract.View {

    @BindView(R.id.guanli_num)
    TextView guanliNum;
    @BindView(R.id.jishu_num)
    TextView jishuNum;
    @BindView(R.id.pugong_num)
    TextView pugongNum;
    @BindView(R.id.shebei_recycle)
    RecyclerView shebeiRecycle;
    @BindView(R.id.mianji_num)
    TextView mianjiNum;
    @BindView(R.id.xingzhi_num)
    TextView xingzhiNum;
    Unbinder unbinder;

    ComplanBO complanBo;
    @BindView(R.id.chang_line)
    View changLine;
    @BindView(R.id.chang_text)
    TextView changText;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complany_zizhi, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        shebeiRecycle.setLayoutManager(manager);
        shebeiRecycle.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        shebeiRecycle.addItemDecoration(itemDecoration);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycle.setLayoutManager(manager1);
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
            if (isShow) {
                changLine.setVisibility(View.VISIBLE);
                changText.setVisibility(View.VISIBLE);
                imageRecycle.setVisibility(View.VISIBLE);
            } else {
                changLine.setVisibility(View.GONE);
                changText.setVisibility(View.GONE);
                imageRecycle.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 设置页面显示
     */
    private void setUIMsg() {
        guanliNum.setText(complanBo.getCompanyInfos().getAdminNumber() + "个");
        jishuNum.setText(complanBo.getCompanyInfos().getTechnicalNumber() + "个");
        pugongNum.setText(complanBo.getCompanyInfos().getOrdinaryNumber() + "个");
        mianjiNum.setText(complanBo.getCompanyInfos().getPlantArea() + "㎡");
        xingzhiNum.setText(complanBo.getCompanyInfos().getPlantNature() == 0 ? "自建" : "租赁");

        setAdapter();
        setImageAdapter();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<ComplanBO.DevicesBean> adapter =
                new LGRecycleViewAdapter<ComplanBO.DevicesBean>(complanBo.getDevices()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_shebei;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ComplanBO.DevicesBean imageBO, int position) {
                        holder.setText(R.id.device_name, imageBO.getName());
                        holder.setText(R.id.device_value, imageBO.getNum() + "");
                    }
                };
        shebeiRecycle.setAdapter(adapter);
    }


    private void setImageAdapter() {
        if (complanBo.getCompanyInfos().getPlantImage().isEmpty()) {
            imageRecycle.setVisibility(View.GONE);
            changText.setVisibility(View.GONE);
            changLine.setVisibility(View.GONE);
        } else {
            imageRecycle.setVisibility(View.VISIBLE);
            changText.setVisibility(View.VISIBLE);
            changLine.setVisibility(View.VISIBLE);
        }
        LGRecycleViewAdapter<ImageBO> adapter = new LGRecycleViewAdapter<ImageBO>(complanBo.getCompanyInfos().getPlantImage()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.image_add;
            }

            @Override
            public void convert(LGViewHolder holder, ImageBO imageBO, int position) {
                holder.setImageUrl(getActivity(), R.id.image, imageBO.url);
                holder.setText(R.id.image_name, imageBO.name);
            }
        };
        imageRecycle.setAdapter(adapter);
    }
}
