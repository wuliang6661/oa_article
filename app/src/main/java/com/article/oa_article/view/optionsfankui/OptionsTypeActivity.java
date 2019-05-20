package com.article.oa_article.view.optionsfankui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.view.bumen.BumenActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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

    List<String> types = new ArrayList<>();

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
    }


    @OnClick(R.id.next_button)
    public void click() {

    }


    class FlowAdapter extends TagAdapter<String> {

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        FlowAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String buMenFlowBO) {
            View view = getLayoutInflater().inflate(R.layout.item_bumen_flow,
                    null);
            TextView bumenText = view.findViewById(R.id.flow_text);
            bumenText.setText(buMenFlowBO);
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
