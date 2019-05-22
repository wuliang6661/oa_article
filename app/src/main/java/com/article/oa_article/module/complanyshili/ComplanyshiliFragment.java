package com.article.oa_article.module.complanyshili;


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

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComplanyshiliFragment extends MVPBaseFragment<ComplanyshiliContract.View, ComplanyshiliPresenter>
        implements ComplanyshiliContract.View {


    @BindView(R.id.zizhi_recycle)
    RecyclerView zizhiRecycle;
    @BindView(R.id.rongyu_recycle)
    RecyclerView rongyuRecycle;
    Unbinder unbinder;

    ComplanBO complanBo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complany_shili, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        zizhiRecycle.setLayoutManager(manager);
        zizhiRecycle.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        zizhiRecycle.addItemDecoration(itemDecoration);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rongyuRecycle.setLayoutManager(manager1);
        rongyuRecycle.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration1 = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration1.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        rongyuRecycle.addItemDecoration(itemDecoration1);
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


    private void setUIMsg() {
        setZizhiAdapter();
        setRongYuAdapter();
    }

    /**
     * 设置资质适配器
     */
    private void setZizhiAdapter() {
        LGRecycleViewAdapter<ComplanBO.CompanyQualificationsBean> adapter = new
                LGRecycleViewAdapter<ComplanBO.CompanyQualificationsBean>(complanBo.getCompanyQualifications()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_zizhi_details;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ComplanBO.CompanyQualificationsBean imageBO, int position) {
                        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.zizhi_img_recycle);
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(new LGRecycleViewAdapter<ImageBO>(imageBO.getQualificationImage()) {
                            @Override
                            public int getLayoutId(int viewType) {
                                return R.layout.item_complan_img;
                            }

                            @Override
                            public void convert(LGViewHolder holder, ImageBO o, int position) {
                                holder.setImageUrl(getActivity(), R.id.card_fanmian, o.url);
                                holder.setText(R.id.image_text, o.name);
                            }
                        });
                    }
                };
        zizhiRecycle.setAdapter(adapter);
    }


    /**
     * 设置荣誉资源适配器
     */
    private void setRongYuAdapter() {
        LGRecycleViewAdapter<ComplanBO.CompanyHonorsBean> adapter = new
                LGRecycleViewAdapter<ComplanBO.CompanyHonorsBean>(complanBo.getCompanyHonors()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_rongyu_detail;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ComplanBO.CompanyHonorsBean imageBO, int position) {
                        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rongyu_img_recycle);
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(new LGRecycleViewAdapter<ImageBO>(imageBO.getHonorImage()) {
                            @Override
                            public int getLayoutId(int viewType) {
                                return R.layout.item_complan_img;
                            }

                            @Override
                            public void convert(LGViewHolder holder, ImageBO o, int position) {
                                holder.setImageUrl(getActivity(), R.id.card_fanmian, o.url);
                                holder.setText(R.id.image_text, o.name);
                            }
                        });
                    }
                };
        rongyuRecycle.setAdapter(adapter);
    }

}
