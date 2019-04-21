package com.wul.oa_article.module.task_allot;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.widget.SlideRecyclerView;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 任务分派（可编辑和不可编辑）
 */

public class Task_allotFragment extends MVPBaseFragment<Task_allotContract.View, Task_allotPresenter>
        implements Task_allotContract.View {


    @BindView(R.id.task_check)
    CheckBox taskCheck;
    @BindView(R.id.task_right_button)
    TextView taskRightButton;
    @BindView(R.id.task_bar)
    LinearLayout taskBar;
    @BindView(R.id.bar_order_name)
    TextView barOrderName;
    @BindView(R.id.bar_order_message)
    TextView barOrderMessage;
    @BindView(R.id.task_recycle_view)
    SlideRecyclerView taskRecycleView;
    @BindView(R.id.task_list_layout)
    LinearLayout taskListLayout;
    @BindView(R.id.continue_add)
    TextView continueAdd;
    @BindView(R.id.task_suress)
    TextView taskSuress;
    @BindView(R.id.add_task_layout)
    RelativeLayout addTaskLayout;
    Unbinder unbinder;

    private boolean isShunYan = true;  //默认是一键顺延
    private List<AddTaskRequest.OrderTasksBean> tasks;
    private int orderId;
    private int type = 0;   //默认可编辑

    public static Task_allotFragment newInstance(int type, int orderId) {
        Task_allotFragment fragment = new Task_allotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", orderId);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_task_allot, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderId = getArguments().getInt("orderId");
        type = getArguments().getInt("type",0);
        if(type == 0){   //可编辑
            taskRightButton.setVisibility(View.VISIBLE);
            addTaskLayout.setVisibility(View.VISIBLE);
        }else{
            taskRightButton.setVisibility(View.GONE);
            addTaskLayout.setVisibility(View.GONE);
        }

        tasks = new ArrayList<>();
        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycleView.setLayoutManager(manager);
        taskRecycleView.setNestedScrollingEnabled(false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        taskRecycleView.addItemDecoration(itemDecoration);

        setTaskAdapter();
    }


    @OnClick(R.id.task_bar)
    public void barClick() {
        if (taskListLayout.getVisibility() == View.VISIBLE) {
            taskListLayout.setVisibility(View.GONE);
            addTaskLayout.setVisibility(View.GONE);
            taskCheck.setChecked(true);
        } else {
            taskListLayout.setVisibility(View.VISIBLE);
            addTaskLayout.setVisibility(View.VISIBLE);
            taskCheck.setChecked(false);
        }
    }


    @OnClick({R.id.task_right_button, R.id.continue_add, R.id.task_suress})
    public void rightClick(View view) {
        switch (view.getId()) {
            case R.id.task_right_button:
                if (isShunYan) {     //显示顺延弹窗
                    PopShunYanWindow shunYanWindow = new PopShunYanWindow(getActivity());
                    shunYanWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                } else {     //任务编辑
                    isShunYan = true;
                    taskRightButton.setText("一键顺延");
                    addTaskLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.continue_add:    //添加任务(显示添加任务弹窗)
                PopAddTaskWindow window = new PopAddTaskWindow(getActivity());
                window.setListener((name, num, danwei, personId, date, remark) -> {
                    AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
                    bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                    bean.setPlanCompleteDate(date.replaceAll("/", "-"));
                    bean.setPlanNum(Integer.parseInt(num));
                    bean.setRemark(remark);
                    bean.setTaskName(name);
                    bean.setUnit(danwei);
                    bean.setTaskType(0);
                    tasks.add(bean);
                    setTaskAdapter();
                });
                window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.task_suress:   //完成

                isShunYan = false;
                taskRightButton.setText("任务编辑");
                addTaskLayout.setVisibility(View.GONE);
                AddTaskRequest request = new AddTaskRequest();
                request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                request.setObjectId(orderId);
                request.setOrderTasks(tasks);
                mPresenter.addTaskByOrder(request);
                break;
        }
    }


    /**
     * 设置任务列表布局
     */
    private void setTaskAdapter() {
        LGRecycleViewAdapter<AddTaskRequest.OrderTasksBean> adapter =
                new LGRecycleViewAdapter<AddTaskRequest.OrderTasksBean>(tasks) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_task_layout;
                    }

                    @Override
                    public void convert(LGViewHolder holder, AddTaskRequest.OrderTasksBean s, int position) {
                        holder.setText(R.id.task_name, s.getTaskName());
//                        holder.setText(R.id.task_person_name,);
                        holder.setText(R.id.task_shiji_num, "--");
                        holder.setText(R.id.task_jihua_num, s.getPlanNum() + "/" + s.getUnit());
                        holder.setText(R.id.task_date, s.getPlanCompleteDate().replaceAll("-", "/"));
                    }
                };
        adapter.setOnItemClickListener(R.id.tv_delete, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                taskRecycleView.closeMenu();
            }
        });
        taskRecycleView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
