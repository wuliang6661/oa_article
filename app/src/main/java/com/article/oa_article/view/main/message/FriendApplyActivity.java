package com.article.oa_article.view.main.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.MessageServiceImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.AggentUserBO;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2710:09
 * desc   :  好友申请
 * version: 1.0
 */
public class FriendApplyActivity extends BaseActivity {


    @BindView(R.id.friend_recycle)
    RecyclerView friendRecycle;
    @BindView(R.id.see_more)
    TextView seeMore;

    List<AggentUserBO> aggents;   //未通过的好友申请列表

    private boolean isSeeMore = false;   //默认未查看更多

    @Override
    protected int getLayout() {
        return R.layout.act_friend_apply;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("新的好友");

        initView();
        getAggentUser();
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        friendRecycle.setLayoutManager(manager);
    }

    @OnClick(R.id.see_more)
    public void seeMore() {
        MessageServiceImpl.getMoreInfo().subscribe(new HttpResultSubscriber<List<AggentUserBO>>() {
            @Override
            public void onSuccess(List<AggentUserBO> aggentUserBOS) {
                if (aggents == null) {
                    aggents = aggentUserBOS;
                } else {
                    aggents.addAll(aggentUserBOS);
                }
                seeMore.setVisibility(View.GONE);
                isSeeMore = true;
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取数据
     */
    private void getAggentUser() {
        MessageServiceImpl.getToAggentUser().subscribe(new HttpResultSubscriber<List<AggentUserBO>>() {
            @Override
            public void onSuccess(List<AggentUserBO> s) {
                aggents = s;
                setAdapter();
                if (isSeeMore) {
                    seeMore();
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void setAdapter() {
        LGRecycleViewAdapter<AggentUserBO> adapter = new LGRecycleViewAdapter<AggentUserBO>(aggents) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_agent_user;
            }

            @Override
            public void convert(LGViewHolder holder, AggentUserBO aggentUserBO, int position) {
                holder.setText(R.id.user_message, aggentUserBO.getNickName() + "  " + aggentUserBO.getContent());
                holder.setImageUrl(FriendApplyActivity.this, R.id.person_img, aggentUserBO.getImage());
                if (aggentUserBO.getStatus() == 0) {
                    holder.getView(R.id.select_button).setVisibility(View.VISIBLE);
                    holder.getView(R.id.cancle_button).setVisibility(View.VISIBLE);
                    holder.getView(R.id.type_text).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.select_button).setVisibility(View.GONE);
                    holder.getView(R.id.cancle_button).setVisibility(View.GONE);
                    holder.getView(R.id.type_text).setVisibility(View.VISIBLE);
                }
            }
        };
        adapter.setOnItemClickListener(R.id.select_button, (view, position) -> {
            if (aggents.get(position).getMessageType() == 0) {
                agreeUser(aggents.get(position).getObjectId());
            } else {
                agreeComplan(aggents.get(position).getObjectId());
            }
        });
        adapter.setOnItemClickListener(R.id.cancle_button, (view, position) -> {
//            if (aggents.get(position).getMessageType() == 0) {
            resureUser(aggents.get(position).getObjectId());
//            } else {
////                agreeComplan(aggents.get(position).getObjectId());
//            }
        });
        friendRecycle.setAdapter(adapter);
    }


    /**
     * 同意好友申请
     */
    private void agreeUser(int id) {
        MessageServiceImpl.agreeUser(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("已同意！");
                getAggentUser();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 拒绝好友申请
     */
    private void resureUser(int id) {
        MessageServiceImpl.resureUser(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("已同意！");
                getAggentUser();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 同意公司申请
     */
    private void agreeComplan(int id) {
        PersonServiceImpl.agreeAddComplan(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("已同意！");
                getAggentUser();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
