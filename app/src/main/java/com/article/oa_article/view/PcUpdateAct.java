package com.article.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.request.ScanRequest;
import com.article.oa_article.util.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.OnClick;

public class PcUpdateAct extends BaseActivity {


    private String result;

    @Override
    protected int getLayout() {
        return R.layout.act_pc_update;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("电脑上传");

        result = getIntent().getExtras().getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
    }


    @OnClick(R.id.next_button)
    public void onClick() {
        getContent();
    }


    private void getContent() {
        ScanRequest request = new ScanRequest();
        request.setRedisKey(result);
        HttpServerImpl.getFileContent(request).subscribe(new HttpResultSubscriber<List<MuBanTaskBO>>() {
            @Override
            public void onSuccess(List<MuBanTaskBO> muBanTaskBOS) {
                EventBus.getDefault().post(muBanTaskBOS);
                finish();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
