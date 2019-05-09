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
import com.article.oa_article.mvp.MVPBaseFragment;

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
}
