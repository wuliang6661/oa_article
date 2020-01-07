package com.article.oa_article.view.main.home.accepted;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AcceptedOrderBo;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.bean.event.MsgNumEvent;
import com.article.oa_article.bean.request.AsseptRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.CreateActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

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
        request.setId(MyApplication.getCommonId());
        getAsseptOrder();
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
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


    LGRecycleViewAdapter<AcceptedOrderBo> adapter;

    /**
     * 未接受订单列表
     */
    private void setAdapter(List<AcceptedOrderBo> s) {
        if (adapter != null) {
            adapter.setData(s);
            return;
        }
        adapter = new LGRecycleViewAdapter<AcceptedOrderBo>(s) {
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
                if (acceptedOrderBo.getIsChoose() == 1) {
                    holder.setText(R.id.select_button, "选择");
                } else {
                    holder.setText(R.id.select_button, "接受");
                }
                holder.setText(R.id.order_name, acceptedOrderBo.getCompanyOrderName());
                holder.setText(R.id.task_name, acceptedOrderBo.getTaskName());
                holder.setText(R.id.task_time, acceptedOrderBo.getPlanCompleteDate().replaceAll("-", "/"));
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (s.get(position).getTaskType() == 1) {   //外部订单
                    if (s.get(position).getIsChoose() == 1) {  //选择
                        showAleatDialog(s.get(position).getTaskId());
                    } else {
                        mPresenter.getWaiBuOrder(s.get(position).getTaskId(), MyApplication.userBo.getCompanys().get(0).getId());
                    }
                } else {   //内部订单，跳转至接受订单页
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId", s.get(position).getTaskId());
                    gotoActivity(AcceptedTaskActivity.class, bundle, false);
                }
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void showAleatDialog(int taskId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.create();
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_complany, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset)));
        recyclerView.addItemDecoration(itemDecoration);
        DlalogAdapter adapter = new DlalogAdapter(MyApplication.userBo.getCompanys());
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            adapter.setPosition(position);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            showProgress();
            mPresenter.selectComplan(taskId, MyApplication.userBo.getCompanys().get(position).getId());
        });
        recyclerView.setAdapter(adapter);
        dialog.setView(dialogView);
        dialog.show();
    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
        showToast(msg);
    }

    @Override
    public void getClientInfo(int taskId, ClientOrderBo clientOrderBo) {
        stopProgress();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isWaibu", true);
        bundle.putInt("taskId", taskId);
        bundle.putSerializable("client", clientOrderBo);
        gotoActivity(CreateActivity.class, bundle, false);
    }

    @Override
    public void selectComplanSouress(int taskId, int complanId) {
        if (complanId == Integer.parseInt(MyApplication.getCommonId())) {
            mPresenter.getWaiBuOrder(taskId, complanId);
        } else {
            stopProgress();
            getAsseptOrder();
        }
    }


    class DlalogAdapter extends LGRecycleViewAdapter<UserBo.CompanysBean> {

        int select = 0;


        public DlalogAdapter(List<UserBo.CompanysBean> dataList) {
            super(dataList);
        }

        public void setPosition(int select) {
            this.select = select;
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_complany;
        }

        @Override
        public void convert(LGViewHolder holder, UserBo.CompanysBean companysBean, int position) {
            holder.setText(R.id.complny_name, companysBean.getCompanyName());
            CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
            if (select == position) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }
    }

}
