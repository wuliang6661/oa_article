package com.article.oa_article.view.main.personlist;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.addusers.AddUsersActivity;
import com.article.oa_article.view.bumen.BumenActivity;
import com.article.oa_article.view.moveaddperson.MoveAddPersonActivity;
import com.article.oa_article.view.person_details.Person_detailsActivity;
import com.article.oa_article.widget.PopShare;
import com.article.oa_article.wxapi.WxShareUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 通讯录
 */

public class PersonListFragment extends MVPBaseFragment<PersonListContract.View, PersonListPresenter>
        implements PersonListContract.View {


    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.neibu_text)
    TextView neibuText;
    @BindView(R.id.out_text)
    TextView outText;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.expand_list)
    ExpandableListView expandList;
    Unbinder unbinder;
    @BindView(R.id.complany_img)
    ImageView complanyImg;
    @BindView(R.id.complan_name)
    TextView complanName;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;

    IdRequest request;
    int selectMenu = 1;   //默认内部联系人

    boolean isSelectPerson = false;   //是选择联系人进入
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.jiange_line)
    View jiangeLine;


    private List<BumenBO> bumenBOS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_person_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup.LayoutParams params = line.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth() / 2;
        line.setLayoutParams(params);

//        popSwitchLable = new PopSwitchLable(this);
//        popSwitchLable.setListener((personBO, text) -> {
//            editName.setText("");
//            if (text != null) {
//                mPresenter.updateDeart(personBO.getId(), Integer.parseInt(text.getId()));
//            }
//        });
        request = new IdRequest();
        mPresenter.getNeiUsers(request);

        initView();
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (selectMenu == 1) {
            mPresenter.getNeiUsers(request);
        } else {
            mPresenter.getOutUsers(request);
        }
        setAddVisiable();
        if (!MyApplication.isHaveCommon()) {
            complanName.setText("暂无企业");
            return;
        }
        complanName.setText(MyApplication.getCommon().getCompanyName());
    }

    private void initView() {
        expandList.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            if (isSelectPerson) {
                PersonBO personBO = bumenBOS.get(i).getUser().get(i1);
                personBO.setTaskType(selectMenu == 1 ? 0 : 1);
                EventBus.getDefault().post(personBO);
                Objects.requireNonNull(getActivity()).finish();
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("personId", bumenBOS.get(i).getUser().get(i1).getId());
                bundle.putBoolean("isNeiBu", selectMenu == 1);
                bundle.putString("departName", bumenBOS.get(i).getUser().get(i1).getDepart());
                gotoActivity(Person_detailsActivity.class, bundle, false);
            }
            return false;
        });
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (selectMenu == 1) {
                    request.setName(editable.toString());
                    mPresenter.getNeiUsers(request);
                } else {
                    request.setName(editable.toString());
                    mPresenter.getOutUsers(request);
                }
            }
        });
    }


    @OnClick({R.id.neibu_text, R.id.out_text})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.neibu_text:
                if (selectMenu != 1) {
                    TranslateAnimation animation = new TranslateAnimation(ScreenUtils.getScreenWidth() / 2,
                            0, 0, 0);
                    animation.setFillAfter(true);
                    animation.setDuration(500);
                    // 给图片添加动画
                    line.startAnimation(animation);
                    mPresenter.getNeiUsers(request);
                    selectMenu = 1;
                }
                break;
            case R.id.out_text:
                if (selectMenu != 2) {
                    TranslateAnimation animation1 = new TranslateAnimation(0,
                            ScreenUtils.getScreenWidth() / 2, 0, 0);
                    animation1.setFillAfter(true);
                    animation1.setDuration(500);
                    // 给图片添加动画
                    line.startAnimation(animation1);
                    mPresenter.getOutUsers(request);
                    selectMenu = 2;
                }
                break;
        }
        setAddVisiable();
    }


    private void setAddVisiable() {
        if (isSelectPerson) {
            addImg.setVisibility(View.GONE);
            jiangeLine.setVisibility(View.VISIBLE);
        } else {
//            if (selectMenu == 1) {
//                if (MyApplication.getCommon().getIsAdmin() == 1) {
            addImg.setVisibility(View.VISIBLE);
            jiangeLine.setVisibility(View.GONE);
//                } else {
//                    addImg.setVisibility(View.GONE);
//                    jiangeLine.setVisibility(View.VISIBLE);
//                }
//            } else {
//                addImg.setVisibility(View.VISIBLE);
//                jiangeLine.setVisibility(View.GONE);
//            }
//            if (!MyApplication.isHaveCommon()) {
//                addImg.setVisibility(View.GONE);
//                jiangeLine.setVisibility(View.VISIBLE);
//                return;
//            }
        }
    }

    @OnClick(R.id.add_img)
    public void addImage() {
        if (!MyApplication.isHaveCommon()) {
            showToast("暂无企业，无法添加好友！");
            return;
        }
        if (selectMenu == 1 && MyApplication.getCommon().getIsAdmin() != 1) {
            showToast("您不是管理员，无法添加内部联系人");
            return;
        }
        PopPersonAdd addPop = new PopPersonAdd(getActivity());
        addPop.setListener(new PopPersonAdd.onCommitListener() {
            @Override
            public void shoudongAdd() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isNeiBu", selectMenu == 1);
                gotoActivity(MoveAddPersonActivity.class, bundle, false);
            }

            @Override
            public void piliangAdd() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isNeiBu", selectMenu == 1);
                gotoActivity(AddUsersActivity.class, bundle, false);
            }
        });
        addPop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    public void setTitleVisiable() {
        new Handler().post(() -> {
            titleLayout.setVisibility(View.VISIBLE);
            complanyImg.setVisibility(View.VISIBLE);
            if (!MyApplication.isHaveCommon()) {
                complanName.setText("暂无企业");
                return;
            }
            complanName.setText(MyApplication.getCommon().getCompanyName());
            Glide.with(getActivity()).load(MyApplication.getCommon().getLogo()).into(complanyImg);
        });
    }


    @OnClick(R.id.fpply_friend)
    public void friendManager(){
        PopShare share = new PopShare(getActivity());
        share.setListener(new PopShare.onCommitListener() {
            @Override
            public void shareFriend() {
                mPresenter.getShareMsg(0);
            }

            @Override
            public void shareMenmens() {
                mPresenter.getShareMsg(1);
            }
        });
        share.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getShare(int flag, ShareBo shareBo) {
        WxShareUtils.get().wechatShare(getActivity(), flag, shareBo);
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //    PopSwitchLable popSwitchLable;
    private BuMenFlowBO buMenFlowBO;

    @Override
    public void getPersonListByNeiBu(List<BumenBO> bumenBOS) {
        this.bumenBOS = bumenBOS;
        ExpandAdapter expandAdapter = new ExpandAdapter(getActivity(), bumenBOS, false);
        expandAdapter.setIsSelect(isSelectPerson);
        expandAdapter.setListener((groupPosition, childPosition) -> {
//            popSwitchLable.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//            popSwitchLable.setText(bumenBOS.get(groupPosition).getUser().get(childPosition));
            Intent intent = new Intent(getActivity(), BumenActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("person", bumenBOS.get(groupPosition).getUser().get(childPosition));
            startActivityForResult(intent, 0x11);
        });
        expandList.setAdapter(expandAdapter);
        for (int i = 0; i < bumenBOS.size(); i++) {
            expandList.expandGroup(i);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0x11:
                buMenFlowBO = (BuMenFlowBO) data.getSerializableExtra("bumen");
//                if (buMenFlowBO != null) {
//                    popSwitchLable.setFlow(buMenFlowBO);
//                }
                break;
        }
    }

    @Override
    public void getPersonListByWaiBu(List<BumenBO> bumenBOS) {
        this.bumenBOS = bumenBOS;
        ExpandAdapter expandAdapter = new ExpandAdapter(getActivity(), bumenBOS, true);
        expandList.setAdapter(expandAdapter);
        for (int i = 0; i < bumenBOS.size(); i++) {
            expandList.expandGroup(i);
        }
    }

    @Override
    public void updateDeats() {
        mPresenter.getNeiUsers(request);
    }


    public void setIsSelectPerson(boolean isSelectPerson) {
        this.isSelectPerson = isSelectPerson;
        new Handler().post(() -> {
            if (isSelectPerson) {
                addImg.setVisibility(View.GONE);
                jiangeLine.setVisibility(View.VISIBLE);
            } else {
                addImg.setVisibility(View.VISIBLE);
                jiangeLine.setVisibility(View.GONE);
                if (!MyApplication.isHaveCommon()) {
                    complanName.setText("暂无企业");
                }
//                if (selectMenu == 1) {
//                    if (MyApplication.getCommon().getIsAdmin() == 1) {
//                        addImg.setVisibility(View.VISIBLE);
//                        jiangeLine.setVisibility(View.GONE);
//                    } else {
//                        addImg.setVisibility(View.GONE);
//                        jiangeLine.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    addImg.setVisibility(View.VISIBLE);
//                    jiangeLine.setVisibility(View.GONE);
//                }
            }
        });
    }
}
