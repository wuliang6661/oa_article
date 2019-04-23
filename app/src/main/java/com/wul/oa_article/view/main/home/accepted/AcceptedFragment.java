package com.wul.oa_article.view.main.home.accepted;


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

import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.AcceptedOrderBo;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.bean.event.MsgNumEvent;
import com.wul.oa_article.bean.request.AsseptRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

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
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (s.get(position).getTaskType() == 1) {   //外部订单
                    showAleatDialog();
                } else {   //内部订单，跳转至接受订单页
                    showAleatDialog();

//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", s.get(position).getTaskId());
//                    gotoActivity(AcceptedTaskActivity.class, bundle, false);
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


    private void showAleatDialog() {
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
        });
        recyclerView.setAdapter(adapter);
        dialog.setView(dialogView);
        dialog.show();
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
