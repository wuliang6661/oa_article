package com.article.oa_article.view.main.message;


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
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.TimeUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
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
            msgNum.setText(notNum + "");
        }
    }

    @Override
    public void getMsgList(List<MsgBO> msgBOS) {
        setAdapter(msgBOS);
    }


    private void setAdapter(List<MsgBO> msgBOS) {
        LGRecycleViewAdapter<MsgBO> adapter = new LGRecycleViewAdapter<MsgBO>(msgBOS) {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 0) {  //好友申请通知
                    return R.layout.item_msg_person;
                } else {      //任务进度
                    return R.layout.item_msg_order;
                }
            }

            @Override
            public void convert(LGViewHolder holder, MsgBO msgBO, int position) {
                if (msgBO.getReadStatus() == 0) {
                    holder.getView(R.id.point).setVisibility(View.INVISIBLE);
                } else {
                    holder.getView(R.id.point).setVisibility(View.VISIBLE);
                }
                holder.setImageUrl(getActivity(), R.id.person_img, msgBO.getImage());
                if (msgBO.getMessageType() == 0) {
                    holder.setText(R.id.msg_message, msgBO.getNickName() + "  " + msgBO.getContent());
                } else {
                    holder.setText(R.id.msg_message, msgBO.getNickName() + "  " + msgBO.getContent());
                    switch (msgBO.getTaskStatus()) {
                        case "2":
                            holder.setText(R.id.msg_type, "已完成");
                            break;
                        case "3":
                            holder.setText(R.id.msg_type, "已取消");
                            break;
                    }
                    holder.setText(R.id.msg_order_name, msgBO.getOrderName() + "  " + msgBO.getOrderNum());
                    holder.setText(R.id.msg_level, msgBO.getTaskLevel() + "级任务");
                    holder.setText(R.id.order_date, TimeUtils.getFriendlyTimeSpanByNow(msgBO.getCreateDate()));
                }
            }

            @Override
            public int getItemViewType(int position) {
                return msgBOS.get(position).getMessageType();
            }
        };
        messageRecycle.setAdapter(adapter);
    }
}
