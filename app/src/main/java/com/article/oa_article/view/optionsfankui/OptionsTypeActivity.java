package com.article.oa_article.view.optionsfankui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.FankuiTypeBO;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OptionsTypeActivity extends BaseActivity {


    @BindView(R.id.recycle_title)
    TextView recycleTitle;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;
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
                flowLayout.setAdapter(adapter);
                if (!s.isEmpty()) {
                    fankuiTypeBO = s.get(0);
                }
                flowLayout.setOnTagClickListener((view, position, parent) -> {
                    adapter.setSelectPosition(position);
                    adapter.notifyDataChanged();
                    fankuiTypeBO = s.get(position);
                    return true;
                });
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    class FlowAdapter extends TagAdapter<FankuiTypeBO> {

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        FlowAdapter(List<FankuiTypeBO> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, FankuiTypeBO buMenFlowBO) {
            View view = getLayoutInflater().inflate(R.layout.item_bumen_flow,
                    null);
            TextView bumenText = view.findViewById(R.id.flow_text);
            bumenText.setText(buMenFlowBO.getName());
            if (position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(OptionsTypeActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);
            } else {
                bumenText.setTextColor(ContextCompat.getColor(OptionsTypeActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
            return view;
        }
    }

}
