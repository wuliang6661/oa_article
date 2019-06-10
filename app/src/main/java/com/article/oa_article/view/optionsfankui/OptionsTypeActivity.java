package com.article.oa_article.view.optionsfankui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.FankuiTypeBO;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OptionsTypeActivity extends BaseActivity {


    @BindView(R.id.recycle_title)
    TextView recycleTitle;
    @BindView(R.id.tag_recycle)
    RecyclerView tagRecycle;
    @BindView(R.id.next_button)
    Button nextButton;


    FankuiTypeBO fankuiTypeBO;

    @Override
    protected int getLayout() {
        return R.layout.act_bumen_flow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("意见反馈");
        recycleTitle.setText("反馈类型");
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        tagRecycle.setLayoutManager(manager);
        getFeedType();
    }


    @OnClick(R.id.next_button)
    public void click() {
        Intent intent = new Intent();
        intent.putExtra("type", fankuiTypeBO);
        setResult(0x11, intent);
        finish();
    }


    /**
     * 获取反馈类型
     */
    private void getFeedType() {
        PersonServiceImpl.getOptionType().subscribe(new HttpResultSubscriber<List<FankuiTypeBO>>() {
            @Override
            public void onSuccess(List<FankuiTypeBO> s) {
                FlowAdapter adapter = new FlowAdapter(s);
                adapter.setOnItemClickListener(R.id.flow_text, (view, position) -> {
                    adapter.setSelectPosition(position);
                    adapter.notifyDataSetChanged();
                    fankuiTypeBO = s.get(position);
                });
                tagRecycle.setAdapter(adapter);
                if (!s.isEmpty()) {
                    fankuiTypeBO = s.get(0);
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    class FlowAdapter extends LGRecycleViewAdapter<FankuiTypeBO> {

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        FlowAdapter(List<FankuiTypeBO> datas) {
            super(datas);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_bumen_flow;
        }

        @Override
        public void convert(LGViewHolder holder, FankuiTypeBO buMenFlowBO, int position) {
            TextView bumenText = (TextView) holder.getView(R.id.flow_text);
            bumenText.setText(buMenFlowBO.getName());
            if (position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(OptionsTypeActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);
            } else {
                bumenText.setTextColor(ContextCompat.getColor(OptionsTypeActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
        }
    }

}
