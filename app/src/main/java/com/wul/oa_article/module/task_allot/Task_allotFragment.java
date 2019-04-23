package com.wul.oa_article.module.task_allot;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.MuBanTaskBO;
import com.wul.oa_article.bean.OrderAndTaskInfoBO;
import com.wul.oa_article.bean.PenPaiTaskBO;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.view.OrderDetailsActivity;
import com.wul.oa_article.view.PcUpdateAct;
import com.wul.oa_article.view.mobanmanager.MobanManagerActivity;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
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
    SwipeMenuRecyclerView taskRecycleView;
    @BindView(R.id.task_list_layout)
    LinearLayout taskListLayout;
    @BindView(R.id.continue_add)
    TextView continueAdd;
    @BindView(R.id.task_suress)
    TextView taskSuress;
    @BindView(R.id.add_task_layout)
    RelativeLayout addTaskLayout;
    Unbinder unbinder;
    @BindView(R.id.moban_add)
    TextView mobanAdd;

    private boolean isShunYan = true;  //默认是一键顺延
    private List<AddTaskRequest.OrderTasksBean> tasks;
    private int orderId;
    private int type = 0;   //默认可编辑

    boolean isTaskEdit = false;   //是否有任务正在编辑状态

    private boolean isOrder = true;     //默认是订单下的任务

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
        EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        orderId = getArguments().getInt("orderId");
//        type = getArguments().getInt("type", 0);


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


    @OnClick({R.id.task_right_button, R.id.continue_add, R.id.task_suress, R.id.moban_add})
    public void rightClick(View view) {
        switch (view.getId()) {
            case R.id.task_right_button:
                if (isShunYan) {     //显示顺延弹窗
                    if (isTaskEdit) {
                        showToast("您有任务正在编辑！请先提交！");
                        return;
                    }
                    PopShunYanWindow shunYanWindow = new PopShunYanWindow(getActivity());
                    shunYanWindow.setListener(text -> mPresenter.updatePlanDate(Integer.parseInt(text), orderId, isOrder ? 0 : 1));
                    shunYanWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                } else {     //任务编辑
                    isShunYan = true;
                    taskRightButton.setText("一键顺延");
                    addTaskLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.continue_add:    //添加任务(显示添加任务弹窗)
                PopAddTaskWindow window = getPopWindow();
                window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.moban_add:
                gotoActivity(MobanManagerActivity.class, false);
                break;
            case R.id.task_suress:   //完成
                if ("电脑上传".equals(taskSuress.getText().toString().trim())) {
                    gotoActivity(PcUpdateAct.class, false);
                } else {
                    for (AddTaskRequest.OrderTasksBean bean : tasks) {
                        if (StringUtils.isEmpty(bean.getNickName()) || StringUtils.isEmpty(bean.getPlanCompleteDate())
                                || bean.getPlanNum() == 0) {
                            showToast("请补全任务数据！");
                            return;
                        }
                    }
                    AddTaskRequest request = new AddTaskRequest();
                    request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                    request.setObjectId(orderId);
                    request.setOrderTasks(tasks);
                    mPresenter.addTaskByOrder(request);
                }
                break;
        }
    }


    /**
     * 设置数据列表
     */
    public void setData(OrderAndTaskInfoBO infoBO) {
        orderId = infoBO.getOrder().getOrderInfo().getId();
        tasks = infoBO.getTaskList();
        setTaskAdapter();
    }

    /**
     * 设置任务状态
     */
    public void setIsOrder(boolean isOrder, int id) {
        this.isOrder = isOrder;
        this.orderId = id;
    }

    /**
     * 设置是否可编辑
     */
    public void isEdit(int type) {
        this.type = type;
        new Handler().post(() -> {
            if (type == 0) {   //可编辑
                taskRightButton.setVisibility(View.VISIBLE);
                addTaskLayout.setVisibility(View.VISIBLE);
            } else {
                taskRightButton.setVisibility(View.GONE);
                addTaskLayout.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 设置任务列表
     */
    public void setTaskList(int atype, List<PenPaiTaskBO> taskList) {
        new Handler().post(() -> {
            this.type = atype;
            for (PenPaiTaskBO task : taskList) {
                AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
                bean.setUserId(task.getUserId());
                bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                bean.setTaskName(task.getTaskName());
                bean.setNickName(task.getNickName());
                bean.setRemainingDate(task.getRemainingDate());
                bean.setPlanCompleteDate(TimeUtils.millis2String(task
                        .getPlanCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
                tasks.add(bean);
            }
            isTaskEdit = false;
            setTaskAdapter();
        });
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
                        holder.setText(R.id.task_person_name, s.getNickName());
                        holder.setText(R.id.task_shiji_num, "--");
                        holder.setText(R.id.task_jihua_num, (s.getPlanNum() == 0 ? "--" : s.getPlanNum()) + "/" +
                                (StringUtils.isEmpty(s.getUnit()) ? "--" : s.getUnit()));
                        if (StringUtils.isEmpty(s.getPlanCompleteDate())) {
                            holder.setText(R.id.task_date, "--");
                        } else {
                            holder.setText(R.id.task_date, s.getPlanCompleteDate().replaceAll("-", "/"));
                        }
                        if (s.getRemainingDate() != 0) {
                            TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                            surplus_time.setText(s.getRemainingDate() + "天");
                            if (s.getRemainingDate() > 0) {
                                surplus_time.setTextColor(Color.parseColor("#71EA45"));
                            } else {
                                surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                            }
                        }
                    }
                };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            if (type == 0) {   //可编辑
                PopAddTaskWindow window = getPopWindow();
                window.setData(position, tasks.get(position));
                window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("id", tasks.get(position).getId());
                gotoActivity(OrderDetailsActivity.class, bundle, false);
            }
        });
        adapter.setOnItemClickListener(R.id.tv_delete, (view, position) -> taskRecycleView.smoothCloseMenu());
        if (type == 0) {
            if (tasks.size() == 0) {
                taskRightButton.setVisibility(View.GONE);
                continueAdd.setText("添加任务");
                mobanAdd.setVisibility(View.VISIBLE);
                taskSuress.setText("电脑上传");
            } else {
                taskRightButton.setVisibility(View.VISIBLE);
                continueAdd.setText("继续添加");
                mobanAdd.setVisibility(View.GONE);
                taskSuress.setText("完成");
            }
        }
        taskRecycleView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void taskSourss() {
        showToast("分派成功！");
        isTaskEdit = false;
        isShunYan = false;
        taskRightButton.setText("任务编辑");
        addTaskLayout.setVisibility(View.GONE);
    }

    @Override
    public void shunyanSourss() {
        showToast("顺延成功！");
        if (isOrder) {
            getInfo();
        } else {
            getOrderByTaskId();
        }
    }


    private PopAddTaskWindow getPopWindow() {
        PopAddTaskWindow window = new PopAddTaskWindow(getActivity());
        window.setListener((position, name, num, danwei, personId, date, remark) -> {
            AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
            bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
            bean.setPlanCompleteDate(date.replaceAll("/", "-"));
            bean.setPlanNum(Integer.parseInt(num));
            bean.setRemark(remark);
            bean.setTaskName(name);
            bean.setUnit(danwei);
            bean.setTaskType(0);
            bean.setUserId(personId.getId());
            bean.setNickName(personId.getName());
            if (position == -1) {
                tasks.add(bean);
            } else {
                tasks.set(position, bean);
            }
            isTaskEdit = true;
            setTaskAdapter();
        });
        return window;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(List<MuBanTaskBO> muBanTaskBOS) {
        for (MuBanTaskBO muBanTaskBO : muBanTaskBOS) {
            AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
            bean.setUserId(muBanTaskBO.getUserId());
            bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
            bean.setTaskName(muBanTaskBO.getTaskName());
            bean.setNickName(muBanTaskBO.getNickName());
            tasks.add(bean);
        }
        isTaskEdit = true;
        setTaskAdapter();
    }


    /**
     * 根据订单ID获取信息
     */
    public void getInfo() {
        IdRequest request = new IdRequest();
        request.setId(orderId);
        HttpServerImpl.getInfoByOrderId(request).subscribe(new HttpResultSubscriber<OrderAndTaskInfoBO>() {
            @Override
            public void onSuccess(OrderAndTaskInfoBO s) {
                setData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId() {
        IdRequest request = new IdRequest();
        request.setId(orderId);
        HttpServerImpl.getInfoByTaskId(request).subscribe(new HttpResultSubscriber<OrderAndTaskInfoBO>() {
            @Override
            public void onSuccess(OrderAndTaskInfoBO s) {
                setData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
