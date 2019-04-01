package com.wul.oa_article.view.main.home.accepted;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.AcceptedOrderBo;
import com.wul.oa_article.bean.event.MsgNumEvent;
import com.wul.oa_article.bean.request.AsseptRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 待接受
 */

public class AcceptedFragment extends MVPBaseFragment<AcceptedContract.View, AcceptedPresenter>
        implements AcceptedContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_asspte, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    AsseptRequest request;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        request = new AsseptRequest();
        request.setPageNum(1);
        request.setPageSize(1000);
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setId(commonId);
        getAsseptOrder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取待接受的订单列表
     */
    private void getAsseptOrder() {
        HttpServerImpl.getAsseptOrder(request).subscribe(new HttpResultSubscriber<List<AcceptedOrderBo>>() {
            @Override
            public void onSuccess(List<AcceptedOrderBo> s) {
                MsgNumEvent event = new MsgNumEvent();
                event.num = s.size();
                EventBus.getDefault().post(event);
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AsseptRequest request) {
        this.request = request;
        getAsseptOrder();
    }


    /**
     * 未接受订单列表
     */
    private void setAdapter(List<AcceptedOrderBo> s) {
        LGRecycleViewAdapter<AcceptedOrderBo> adapter = new LGRecycleViewAdapter<AcceptedOrderBo>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_asspte_order;
            }

            @Override
            public void convert(LGViewHolder holder, AcceptedOrderBo acceptedOrderBo, int position) {
                if (acceptedOrderBo.getTaskType() == 1) {
                    holder.getView(R.id.waibu_img).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.waibu_img).setVisibility(View.GONE);
                }
                holder.setText(R.id.order_name, acceptedOrderBo.getCompanyOrderName());
                holder.setText(R.id.task_name, acceptedOrderBo.getTaskName());
                holder.setText(R.id.task_time, acceptedOrderBo.getPlanCompleteDate().replaceAll("-", "/"));
            }
        };
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
