package com.article.oa_article.view.main.message;


import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.bean.event.MsgFragmentEvent;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.MyOrderActivity;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 消息fragment
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter>
        implements MessageContract.View {


    @BindView(R.id.edit_mode)
    TextView editMode;
    @BindView(R.id.msg_num)
    TextView msgNum;
    @BindView(R.id.message_recycle)
    RecyclerView messageRecycle;
    Unbinder unbinder;
    @BindView(R.id.all_select)
    TextView allSelect;
    @BindView(R.id.yidu_layout)
    LinearLayout yiduLayout;
    @BindView(R.id.weidu_layout)
    LinearLayout weiduLayout;
    @BindView(R.id.operate_layout)
    LinearLayout operateLayout;

    MessageAdapter adapter;
    private boolean isEdit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_message, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        mPresenter.getMessageList(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getReadCount(Integer.parseInt(MyApplication.getCommonId()));
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getMessageList(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getReadCount(Integer.parseInt(MyApplication.getCommonId()));
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        messageRecycle.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset)));
        messageRecycle.addItemDecoration(itemDecoration);
    }


    @OnClick({R.id.edit_mode, R.id.all_select})
    public void titleClick(View view) {
        switch (view.getId()) {
            case R.id.edit_mode:
                if (isEdit) {   //已经是编辑状态,则点击取消
                    adapter.setEdit(false);
                    allSelect.setVisibility(View.GONE);
                    editMode.setText("编辑");
                    isEdit = false;
                    operateLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.share_pop_out));
                    operateLayout.setVisibility(View.GONE);
                } else {
                    adapter.setEdit(true);
                    allSelect.setVisibility(View.VISIBLE);
                    editMode.setText("取消");
                    isEdit = true;
                    operateLayout.setVisibility(View.VISIBLE);
                    operateLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.share_pop_in));
                }
                break;
            case R.id.all_select:
                adapter.setIsAllSelect();
                break;
        }
    }


    @OnClick({R.id.yidu_layout, R.id.weidu_layout})
    public void buttomClick(View view) {
        StringBuilder builder = new StringBuilder();
        if (adapter.selectList.isEmpty()) {
            showToast("请选择需要操作的消息！");
            return;
        }
        for (MsgBO msgBO : adapter.selectList.values()) {
            builder.append(msgBO.getId()).append(",");
        }
        isClick = false;
        switch (view.getId()) {
            case R.id.yidu_layout:
                mPresenter.setMsgRead(builder.toString().substring(0, builder.length() - 1), 1);
                break;
            case R.id.weidu_layout:
                mPresenter.setMsgRead(builder.toString().substring(0, builder.length() - 1), 0);
                break;
        }
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

    @Override
    public void getNoReadCount(Integer num) {
        int notNum = num.intValue();
        if (notNum <= 0) {
            msgNum.setVisibility(View.GONE);
        } else {
            msgNum.setVisibility(View.VISIBLE);
            msgNum.setText(notNum > 999 ? "..." : notNum + "");
        }
        EventBus.getDefault().post(new MsgFragmentEvent(notNum));
    }

    private List<MsgBO> msg;

    @Override
    public void getMsgList(List<MsgBO> msgBOS) {
        this.msg = msgBOS;
        setAdapter(msgBOS);
    }

    @Override
    public void readSuress() {
        adapter.selectList.clear();
        mPresenter.getMessageList(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getReadCount(Integer.parseInt(MyApplication.getCommonId()));
        if (!isClick) {
            titleClick(editMode);
        }
    }

    boolean isClick = false;


    private void setAdapter(List<MsgBO> msgBOS) {
        if (adapter != null) {
            adapter.setData(msgBOS);
            return;
        }
        adapter = new MessageAdapter(msgBOS);
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            if (msg.get(position).getMessageType() == 0 || msg.get(position).getMessageType() == 4) {   //好友申请
                gotoActivity(FriendApplyActivity.class, false);
            } else {
                switch (msg.get(position).getPage()) {
                    case 1:   //待接受
                        Bundle bundle = new Bundle();
                        bundle.putInt("taskId", msg.get(position).getId());
                        bundle.putBoolean("isHome", true);
                        gotoActivity(AcceptedTaskActivity.class, bundle, false);
                        break;
                    case 2:   //  我的任务
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("taskId", msg.get(position).getId());
                        gotoActivity(MyOrderActivity.class, bundle1, false);
                        break;
                    case 3:   // 订单详情
                        if (msg.get(position).getMessageType() == 7 || msg.get(position).getMessageType() == 8) {
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("id", msg.get(position).getId());
                            bundle2.putBoolean("isOrder", true);
                            gotoActivity(Order_detailsActivity.class, bundle2, false);
                        } else {
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("id", msg.get(position).getId());
                            bundle2.putBoolean("isOrder", false);
                            gotoActivity(Order_detailsActivity.class, bundle2, false);
                        }
                        break;
                }
            }
            isClick = true;
            mPresenter.setMsgRead(msg.get(position).getId() + "", 1);
        });
        messageRecycle.setAdapter(adapter);
    }


    class MessageAdapter extends LGRecycleViewAdapter<MsgBO> {

        private List<MsgBO> msgBOS;
        private boolean isEdit;
        Map<Integer, MsgBO> selectList;

        @SuppressLint("UseSparseArrays")
        MessageAdapter(List<MsgBO> dataList) {
            super(dataList);
            msgBOS = dataList;
            selectList = new HashMap<>();
        }

        @Override
        public int getLayoutId(int viewType) {
            if (viewType == 0 || viewType == 4) {  //好友申请通知
                return R.layout.item_msg_person;
            } else {      //任务进度
                return R.layout.item_msg_order;
            }
        }

        /**
         * 设置是否可选中
         */
        void setEdit(boolean isEdit) {
            this.isEdit = isEdit;
            notifyDataSetChanged();
        }

        void setIsAllSelect() {
            selectList.clear();
            for (int i = 0; i < msgBOS.size(); i++) {
                selectList.put(i, msgBOS.get(i));
            }
            notifyDataSetChanged();
        }

        @Override
        public void setData(List<MsgBO> dataList) {
            super.setData(dataList);
            this.msgBOS = dataList;
        }

        @Override
        public void convert(LGViewHolder holder, MsgBO msgBO, int position) {
            holder.setImageUrl(getActivity(), R.id.person_img, msgBO.getImage());
            CheckBox box = (CheckBox) holder.getView(R.id.checkbox);
            if (isEdit) {
                box.setVisibility(View.VISIBLE);
                holder.getView(R.id.point).setVisibility(View.GONE);
                if (selectList.get(position) == null) {
                    box.setChecked(false);
                } else {
                    box.setChecked(true);
                }
            } else {
                box.setVisibility(View.GONE);
                if (msgBO.getReadStatus() == 0) {
                    holder.getView(R.id.point).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.point).setVisibility(View.INVISIBLE);
                }
            }
            box.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    selectList.put(position, msgBOS.get(position));
                } else {
                    selectList.remove(position);
                }
            });
            String content = msgBO.getNickName() + "  " + msgBO.getContent();
            if (msgBO.getMessageType() == 0 || msgBO.getMessageType() == 4) {
                holder.setText(R.id.msg_message, content.length() > 20 ? content.substring(0, 12) + "..." : content);
            } else {
                holder.setText(R.id.msg_message, content.length() > 20 ? content.substring(0, 12) + "..." : content);
                TextView msgType = (TextView) holder.getView(R.id.msg_type);
                if (msgType != null) {
                    switch (msgBO.getTaskStatus()) {
                        case "2":
                            msgType.setText("已完成");
                            msgType.setTextColor(Color.parseColor("#F4CA40"));
                            break;
                        case "3":
                            msgType.setText("已取消");
                            msgType.setTextColor(Color.parseColor("#E92B2B"));
                            break;
                    }
                }
                holder.setText(R.id.msg_order_name, msgBO.getOrderName() + "  " + msgBO.getOrderNum());
                if (msgBO.getMessageType() != 1) {
                    holder.getView(R.id.leave_layout).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.leave_layout).setVisibility(View.VISIBLE);
                    holder.setText(R.id.msg_level, msgBO.getTaskLevel() + "级任务");
                }
                holder.setText(R.id.order_date, TimeUtils.getFriendlyTimeSpanByNow(msgBO.getCreateDate()));
            }
        }

        @Override
        public int getItemViewType(int position) {
            return msgBOS.get(position).getMessageType();
        }
    }
}
