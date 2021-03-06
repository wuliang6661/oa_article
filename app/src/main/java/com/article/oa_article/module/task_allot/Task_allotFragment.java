package com.article.oa_article.module.task_allot;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.OrderAndTaskInfoBO;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.event.CommitVisableEvent;
import com.article.oa_article.bean.event.UpdateTaskEvent;
import com.article.oa_article.bean.request.AddTaskRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.Constant;
import com.article.oa_article.util.DateUtils;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.MyOrderActivity;
import com.article.oa_article.view.mobanmanager.DataBean;
import com.article.oa_article.view.mobanmanager.MobanManagerActivity;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.article.oa_article.zxing.activity.CaptureActivity;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private boolean isShunYan = false;  //默认是任务编辑
    private List<AddTaskRequest.OrderTasksBean> tasks;
    private int orderId;
    private int type = 0;   //默认可编辑

    boolean isTaskEdit = false;   //是否有任务正在编辑状态
    private boolean isOrder = true;     //默认是订单下的任务
    View view;

    private long endTime;   //分派任务的结束时间
    private int planNum;
    private String name, unit, remark;
    private long time;

    private LGRecycleViewAdapter<AddTaskRequest.OrderTasksBean> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fra_task_allot, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }


//    @OnClick(R.id.task_bar)
//    public void barClick() {
//        if (taskListLayout.getVisibility() == View.VISIBLE) {
//            taskListLayout.setVisibility(View.GONE);
//            addTaskLayout.setVisibility(View.GONE);
//            taskCheck.setChecked(true);
//        } else {
//            taskListLayout.setVisibility(View.VISIBLE);
//            if (type == 0 && isShunYan) {
//                addTaskLayout.setVisibility(View.VISIBLE);
//            }
//            taskCheck.setChecked(false);
//        }
//    }


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
                    EventBus.getDefault().post(new CommitVisableEvent(1));
                    taskRecycleView.setAdapter(null);
                    setSwipeMenu();
                    setTaskAdapter();
                }
                break;
            case R.id.continue_add:    //添加任务(显示添加任务弹窗)
                PopAddTaskWindow window = getPopWindow();
                window.setEndTime(endTime);
                window.setPlanNum(planNum, name, unit, time, remark);
                window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.moban_add:
                Intent intent = new Intent(getActivity(), MobanManagerActivity.class);
                startActivityForResult(intent, 0x11);
                break;
            case R.id.task_suress:   //完成
                if ("电脑上传".equals(taskSuress.getText().toString().trim())) {
                    startQrCode();
                } else {
                    for (AddTaskRequest.OrderTasksBean bean : tasks) {
                        if (StringUtils.isEmpty(bean.getNickName()) || StringUtils.isEmpty(bean.getPlanCompleteDate())
                                || bean.getPlanNum() == 0) {
                            showToast("请补全任务数据！");
                            return;
                        }
                        bean.setPlanCompleteDate(bean.getPlanCompleteDate().replaceAll("/", "-"));
                        if (!StringUtils.isEmpty(bean.getActualCompleteDate())) {
                            bean.setActualCompleteDate(bean.getActualCompleteDate().replaceAll("/", "-"));
                        }
                    }
                    AddTaskRequest request = new AddTaskRequest();
                    request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                    request.setObjectId(orderId);
                    request.setOrderTasks(tasks);
                    if (isOrder) {
                        mPresenter.addTaskByOrder(request);
                    } else {
                        mPresenter.addTaskByTask(request);
                    }
                }
                break;
        }
    }


    /**
     * 完成
     */
    public void commitTask() {
        for (AddTaskRequest.OrderTasksBean bean : tasks) {
            if (StringUtils.isEmpty(bean.getNickName()) || StringUtils.isEmpty(bean.getPlanCompleteDate())
                    || bean.getPlanNum() == 0) {
                showToast("请补全任务数据！");
                return;
            }
            bean.setPlanCompleteDate(bean.getPlanCompleteDate().replaceAll("/", "-"));
            if (!StringUtils.isEmpty(bean.getActualCompleteDate())) {
                bean.setActualCompleteDate(bean.getActualCompleteDate().replaceAll("/", "-"));
            }
        }
        AddTaskRequest request = new AddTaskRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setObjectId(orderId);
        request.setOrderTasks(tasks);
        if (isOrder) {
            mPresenter.addTaskByOrder(request);
        } else {
            mPresenter.addTaskByTask(request);
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
                if (tasks.size() == 0) {
                    taskRightButton.setVisibility(View.GONE);
                    addTaskLayout.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new CommitVisableEvent(1));
                    isShunYan = true;
                } else {
                    taskRightButton.setVisibility(View.VISIBLE);
                }
//                addTaskLayout.setVisibility(View.VISIBLE);
            } else {
                taskRightButton.setVisibility(View.GONE);
                addTaskLayout.setVisibility(View.GONE);
                EventBus.getDefault().post(new CommitVisableEvent(0));
            }
        });
    }


    /**
     * 设置任务分派的结束时间
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * 设置订单分派数量
     */
    public void setPlanNum(int planNum, String name, String unit, long time, String remark) {
        this.planNum = planNum;
        this.name = name;
        this.unit = unit;
        this.time = time;
        this.remark = remark;
    }


    /**
     * 设置任务列表
     */
    public void setTaskList(int atype, List<PenPaiTaskBO> taskList) {
        new Handler().post(() -> {
            this.type = atype;
            tasks.clear();
            for (PenPaiTaskBO task : taskList) {
                AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
                bean.setId(task.getId());
                bean.setUserId(task.getUserId());
                bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
                bean.setTaskName(task.getTaskName());
                bean.setNickName(task.getNickName());
                bean.setRemainingDate(task.getRemainingDate());
                bean.setStatus(task.getStatus());
                bean.setActualNum(task.getActualNum());
                bean.setPlanNum(task.getPlanNum());
                bean.setUnit(task.getUnit());
                bean.setPage(task.getPage());
                bean.setPlanCompleteDate(TimeUtils.millis2String(task
                        .getPlanCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
                bean.setActualCompleteDate(TimeUtils.millis2String(task
                        .getActualCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
                tasks.add(bean);
            }
            isTaskEdit = false;
            isEdit(type);
            if (type == 0 && tasks.size() == 0) {
                isShunYan = true;
            }
            if (type == 0 && adapter == null && isShunYan) {   //可编辑
                setSwipeMenu();
            }
            setTaskAdapter();
        });
    }


    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            if (viewType == 3) {  //已取消
                return;
            }
            if ((type == 0 && isShunYan) || isTaskEdit) {   //编辑状态
                // 2 删除
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                if (viewType == 0) {
                    deleteItem.setText("删除")
                            .setBackgroundColor(getResources().getColor(R.color.item_delete))
                            .setTextColor(Color.WHITE) // 文字颜色。
                            .setTextSize(15) // 文字大小。
                            .setWidth(SizeUtils.dp2px(63))
                            .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    deleteItem.setText("取消")
                            .setBackgroundColor(getResources().getColor(R.color.item_delete))
                            .setTextColor(Color.WHITE) // 文字颜色。
                            .setTextSize(15) // 文字大小。
                            .setWidth(SizeUtils.dp2px(63))
                            .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                }
                rightMenu.addMenuItem(deleteItem);
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };
        // 设置监听器。
        taskRecycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            AddTaskRequest.OrderTasksBean orderTasksBean = tasks.get(menuBridge.getAdapterPosition());
            if (orderTasksBean.getStatus() == 0) {   //未接受，则直接删除
                deleteTask(menuBridge.getAdapterPosition());
            } else {
                cancleTask(menuBridge.getAdapterPosition());
            }
        };
        // 菜单点击监听。
        taskRecycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }


    /**
     * 删除任务
     */
    private void deleteTask(int position) {
        new AlertDialog(getActivity()).builder().setGone().setMsg("确认删除该任务？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    if (tasks.get(position).getId() == 0) {
                        tasks.remove(position);
                        setTaskAdapter();
                    } else {
                        mPresenter.cancleTask(tasks.get(position).getId(), position, true);
                    }
                }).show();
    }

    /**
     * 取消任务
     */
    private void cancleTask(int position) {
        new AlertDialog(getActivity()).builder().setGone().setMsg("确认取消该任务？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    mPresenter.cancleTask(tasks.get(position).getId(), position, false);
                }).show();
    }

    /**
     * 设置任务列表布局
     */
    private void setTaskAdapter() {
        adapter = new LGRecycleViewAdapter<AddTaskRequest.OrderTasksBean>(tasks) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_task_layout;
            }

            @Override
            public void convert(LGViewHolder holder, AddTaskRequest.OrderTasksBean s, int position) {
                holder.setText(R.id.task_name, s.getTaskName());
                if (!StringUtils.isEmpty(s.getNickName())) {
                    holder.setText(R.id.task_person_name, s.getNickName().length() > 3 ? s.getNickName().substring(0, 3) + "..." : s.getNickName());
                } else {
                    holder.setText(R.id.task_person_name, s.getNickName());
                }
                holder.setText(R.id.task_shiji_num, s.getActualNum() == 0 ? "0" : s.getActualNum() + "");
                if (StringUtils.isEmpty(s.getUnit())) {
                    holder.setText(R.id.task_jihua_num, s.getPlanNum() == 0 ? "--" : s.getPlanNum() + "");
                } else {
                    holder.setText(R.id.task_jihua_num, s.getPlanNum() + (StringUtils.isEmpty(s.getUnit()) ? "" : " / " + s.getUnit()));
                }
                if (StringUtils.isEmpty(s.getPlanCompleteDate())) {
                    holder.setText(R.id.task_date, "--");
                } else {
                    holder.setText(R.id.task_date, s.getPlanCompleteDate().replaceAll("-", "/"));
                }
                TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                surplus_time.setTextSize(17);
                if (s.getRemainingDate() != 0) {
                    surplus_time.setText(s.getRemainingDate() + "天");
                    if (s.getRemainingDate() > 0) {
                        surplus_time.setTextColor(Color.parseColor("#71EA45"));
                    } else {
                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                    }
                } else {
                    surplus_time.setText("0天");
                    surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                }
                TextView taskType = (TextView) holder.getView(R.id.task_type);
                switch (s.getStatus()) {
                    case 0:   //待接受
                        taskType.setTextColor(Color.parseColor("#F4CA40"));
                        taskType.setText("未接受");
                        holder.setText(R.id.task_shiji_num, "--");
                        break;
                    case 1:  //已接受
                        taskType.setTextColor(Color.parseColor("#8D8C91"));
                        taskType.setText("进行中");
                        break;
                    case 2:   //已完成
                        taskType.setTextColor(Color.parseColor("#8D8C91"));
                        taskType.setText("已完成");
                        surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                        if (!StringUtils.isEmpty(s.getActualCompleteDate())) {
                            surplus_time.setText(s.getActualCompleteDate().replaceAll("-", "/"));
                        } else {
                            surplus_time.setText("");
                        }
                        surplus_time.setTextSize(11);
                        break;
                    case 3:
                        taskType.setTextColor(Color.parseColor("#8D8C91"));
                        taskType.setText("已取消");
                        surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                        surplus_time.setText("");
                        surplus_time.setTextSize(11);
                        break;
                    default:
                        taskType.setTextColor(Color.parseColor("#F4CA40"));
                        taskType.setText("未分派");
//                        surplus_time.setText("0天");
//                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                        break;
                }
            }

            @Override
            public int getItemViewType(int position) {
                return tasks.get(position).getStatus();
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            if ((type == 0 && isShunYan) || isTaskEdit) {   //可编辑
                PopAddTaskWindow window = getPopWindow();
                window.setData(position, tasks.get(position));
                window.setEndTime(endTime);
                window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            } else {
//                if (tasks.get(position).getStatus() != 0) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", tasks.get(position).getId());
//                    gotoActivity(MyOrderActivity.class, bundle, false);
//                } else {
////                    Bundle bundle = new Bundle();
////                    bundle.putInt("id", tasks.get(position).getId());
////                    bundle.putBoolean("isOrder", false);
////                    gotoActivity(Order_detailsActivity.class, bundle, false);
//                }
                switch (tasks.get(position).getPage()) {
                    case 1:   //待接受
                        Bundle bundle = new Bundle();
                        bundle.putInt("taskId", tasks.get(position).getId());
                        bundle.putBoolean("isHome", true);
                        gotoActivity(AcceptedTaskActivity.class, bundle, false);
                        break;
                    case 2:   //  我的任务
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("taskId", tasks.get(position).getId());
                        gotoActivity(MyOrderActivity.class, bundle1, false);
                        break;
                    case 3:   // 任务详情
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("id", tasks.get(position).getId());
                        bundle2.putBoolean("isOrder", false);
                        gotoActivity(Order_detailsActivity.class, bundle2, false);
                        break;
                    case 4:  //订单详情
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("id", tasks.get(position).getId());
                        bundle3.putBoolean("isOrder", true);
                        gotoActivity(Order_detailsActivity.class, bundle3, false);
                        break;
                }
            }
        });
        if (type == 0) {
//            if (tasks.size() == 0) {
            taskRightButton.setVisibility(View.GONE);
            continueAdd.setText("添加任务");
            mobanAdd.setVisibility(View.VISIBLE);
            taskSuress.setText("电脑上传");
//            } else {
//                taskRightButton.setVisibility(View.VISIBLE);
//                continueAdd.setText("继续添加");
//                mobanAdd.setVisibility(View.GONE);
//                taskSuress.setText("完成");
//            }
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
        taskRightButton.setText("编辑");
        taskRightButton.setVisibility(View.VISIBLE);
        addTaskLayout.setVisibility(View.GONE);
        EventBus.getDefault().post(new CommitVisableEvent(0));
        EventBus.getDefault().post(new UpdateTaskEvent());
    }

    @Override
    public void shunyanSourss() {
        showToast("顺延成功！");
        EventBus.getDefault().post(new UpdateTaskEvent());
    }

    @Override
    public void cancleSuress(int position, boolean isDelete) {
        if (isDelete) {
            showToast("任务删除成功！");
        } else {
            showToast("任务取消成功！");
        }
        EventBus.getDefault().post(new UpdateTaskEvent());
    }

    public boolean getIsTaskEdit() {
        return isTaskEdit;
    }


    private PopAddTaskWindow getPopWindow() {
        PopAddTaskWindow window = new PopAddTaskWindow(getActivity());
        window.setListener((position, name, num, danwei, personId, date, remark, oldbean) -> {
            AddTaskRequest.OrderTasksBean bean = new AddTaskRequest.OrderTasksBean();
            bean.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
            bean.setPlanCompleteDate(date.replaceAll("/", "-"));
            int day = DateUtils.getGapCount(new Date(), new Date(TimeUtils.string2Millis(date,
                    new SimpleDateFormat("yyyy/MM/dd"))));
            bean.setRemainingDate(day);
            bean.setPlanNum(Integer.parseInt(num));
            bean.setRemark(remark);
            bean.setTaskName(name);
            bean.setUnit(danwei);
            bean.setTaskType(0);
            bean.setUserId(personId.getId());
            bean.setTaskType(personId.getTaskType());
            bean.setNickName(personId.getName());
            if (oldbean != null) {
                bean.setId(oldbean.getId());
            }
            if (position == -1) {
                tasks.add(bean);
            } else {
                tasks.set(position, bean);
            }
            isTaskEdit = true;
            isShunYan = true;
            taskRightButton.setText("一键顺延");
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
//            bean.setActualNum(muBanTaskBO.);
            bean.setPlanCompleteDate(muBanTaskBO.getPlanCompleteDate());
            bean.setPlanNum(muBanTaskBO.getPlanNum());
            bean.setUnit(muBanTaskBO.getUnit());
            bean.setRemark(muBanTaskBO.getRemark());
            tasks.add(bean);
        }
        isTaskEdit = true;
        taskRightButton.setText("一键顺延");
        setTaskAdapter();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == 0x11) {
            DataBean dataBean = (DataBean) data.getSerializableExtra("data");
            onEvent(dataBean.getMubans());
        }
    }

    // 开始扫码
    private void startQrCode() {
        // 申请相机权限
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(getActivity(), "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                // 文件读写权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(getActivity(), "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
