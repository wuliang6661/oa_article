package com.article.oa_article.view.moveaddperson;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.bumen.BumenActivity;
import com.article.oa_article.view.lablecustom.LableCustomActivity;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoveAddPersonActivity extends MVPBaseActivity<MoveAddPersonContract.View, MoveAddPersonPresenter>
        implements MoveAddPersonContract.View {

    @BindView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @BindView(R.id.bumen_name)
    TextView bumenName;
    @BindView(R.id.select_bumen)
    RelativeLayout selectBumen;
    @BindView(R.id.waibu_flow_name)
    TextView waibuFlowName;
    @BindView(R.id.select_waibu_flow)
    RelativeLayout selectWaibuFlow;
    @BindView(R.id.hint_message_layout)
    TextView hintMessageLayout;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.bumen_layout)
    LinearLayout bumenLayout;
    @BindView(R.id.bumen_line)
    View bumenLine;
    @BindView(R.id.waibu_point)
    TextView waibuPoint;
    @BindView(R.id.btn_album)
    Button btnAlbum;

    private boolean isNeiBu = true;   //默认添加内部联系人

    private BuMenFlowBO buMenFlowBO;

    LableBo.SysLabelsBean labelsBean;
    LableBo.CustomLabelsBean customLabelsBean;

    boolean isFirst;

    boolean isEdit;

    private AddUserRequest editRequest;

    @Override
    protected int getLayout() {
        return R.layout.act_moveadd_person;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("手动添加");

        isNeiBu = getIntent().getExtras().getBoolean("isNeiBu");
        isEdit = getIntent().getExtras().getBoolean("isEdit", false);
        if (isNeiBu) {
            hintMessageLayout.setVisibility(View.VISIBLE);
            bumenLayout.setVisibility(View.VISIBLE);
            bumenLine.setVisibility(View.VISIBLE);
            waibuPoint.setVisibility(View.GONE);
        } else {
            hintMessageLayout.setVisibility(View.GONE);
            bumenLayout.setVisibility(View.GONE);
            bumenLine.setVisibility(View.GONE);
            waibuPoint.setVisibility(View.VISIBLE);
        }
        if (isEdit) {
            hintMessageLayout.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            btnAlbum.setText("保存");
            btnAlbum.setVisibility(View.VISIBLE);
            editRequest = (AddUserRequest) getIntent().getSerializableExtra("user");
            editPhoneNum.setText(editRequest.getPhone().replaceAll(" ", ""));
        }
    }


    @OnClick(R.id.next_button)
    public void savePerson() {
        String phone = editPhoneNum.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号！");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast("请输入正确手机号！");
            return;
        }
        AddUserRequest request = new AddUserRequest();
        request.setPhone(phone);
        if (isNeiBu) {
            if (buMenFlowBO == null && labelsBean == null && customLabelsBean == null) {
                showToast("请至少填写两项！");
                return;
            }
            if (buMenFlowBO != null) {
                request.setDepartId(Integer.parseInt(buMenFlowBO.getId()));
            }
            if (isFirst) {
                if (labelsBean != null)
                    request.setLabelId(labelsBean.getId());
            } else {
                if (customLabelsBean != null)
                    request.setLabelId(customLabelsBean.getId());
            }
        } else {
            if (labelsBean == null && customLabelsBean == null) {
                showToast("请选择外部联系人标签！");
                return;
            }
            if (isFirst) {
                if (labelsBean != null)
                    request.setLabelId(labelsBean.getId());
            } else {
                if (customLabelsBean != null)
                    request.setLabelId(customLabelsBean.getId());
            }
        }
        if (isEdit) {
            request.setName(editRequest.getName());
            request.setPhoto(editRequest.getPhoto());
            Intent intent = new Intent();
            intent.putExtra("user", request);
            setResult(0x11, intent);
            finish();
        } else {
            mPresenter.addUser(request);
        }
    }


    @OnClick(R.id.btn_album)
    public void save() {
        savePerson();
    }


    @OnClick(R.id.select_bumen)
    public void selectBumen() {
        Intent intent = new Intent(this, BumenActivity.class);
        startActivityForResult(intent, 0x11);
    }


    @OnClick(R.id.select_waibu_flow)
    public void waibuClick() {
        Intent intent = new Intent(this, LableCustomActivity.class);
        startActivityForResult(intent, 0x22);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0x11:
                buMenFlowBO = (BuMenFlowBO) data.getSerializableExtra("bumen");
                bumenName.setText(buMenFlowBO.getName());
                break;
            case 0x22:
                isFirst = data.getBooleanExtra("isFirst", true);
                if (isFirst) {
                    labelsBean = (LableBo.SysLabelsBean) data.getSerializableExtra("lable");
                    waibuFlowName.setText(labelsBean.getName());
                } else {
                    customLabelsBean = (LableBo.CustomLabelsBean) data.getSerializableExtra("lable");
                    waibuFlowName.setText(customLabelsBean.getName());
                }
                break;
        }
    }

    @Override
    public void addUserSuress() {
        showToast("添加成功,已发送添加好友请求！");
        finish();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
