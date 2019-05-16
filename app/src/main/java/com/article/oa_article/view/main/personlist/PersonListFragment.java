package com.article.oa_article.view.main.personlist;


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
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.moveaddperson.MoveAddPersonActivity;
import com.article.oa_article.view.person_details.Person_detailsActivity;
import com.blankj.utilcode.util.ScreenUtils;

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
    LinearLayout titleLayout;

    IdRequest request;
    int selectMenu = 1;   //默认内部联系人

    boolean isSelectPerson = false;   //是选择联系人进入
    @BindView(R.id.edit_name)
    EditText editName;


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

        request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getNeiUsers(request);

        initView();
    }


    private void initView() {
        expandList.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            if (isSelectPerson) {
                PersonBO personBO = bumenBOS.get(i).getUser().get(i1);
                EventBus.getDefault().post(personBO);
                Objects.requireNonNull(getActivity()).finish();
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("personId", bumenBOS.get(i).getUser().get(i1).getId());
                bundle.putBoolean("isNeiBu", selectMenu == 1);
                bundle.putString("departName", bumenBOS.get(i).getUser().get(i1).getDepart());
                gotoActivity(Person_detailsActivity.class, bundle, false);
            }
            return true;
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
        if (isSelectPerson) {
            addImg.setVisibility(View.GONE);
        } else {
            if (selectMenu == 1) {
                if (MyApplication.getCommon().getIsAdmin() == 1) {
                    addImg.setVisibility(View.VISIBLE);
                } else {
                    addImg.setVisibility(View.GONE);
                }
            } else {
                addImg.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.add_img)
    public void addImage() {
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

            }
        });
        addPop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    public void setTitleVisiable() {
        new Handler().post(() -> {
            titleLayout.setVisibility(View.VISIBLE);
            complanName.setText(MyApplication.getCommon().getCompanyName());
//                Glide.with(getActivity()).load(MyApplication.getCommon().)
        });
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

    @Override
    public void getPersonListByNeiBu(List<BumenBO> bumenBOS) {
        this.bumenBOS = bumenBOS;
        ExpandAdapter expandAdapter = new ExpandAdapter(getActivity(), bumenBOS, false);
        expandList.setAdapter(expandAdapter);
        for (int i = 0; i < bumenBOS.size(); i++) {
            expandList.expandGroup(i);
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


    public void setIsSelectPerson(boolean isSelectPerson) {
        this.isSelectPerson = isSelectPerson;
        new Handler().post(() -> {
            if (isSelectPerson) {
                addImg.setVisibility(View.GONE);
            } else {
                if (selectMenu == 1) {
                    if (MyApplication.getCommon().getIsAdmin() == 1) {
                        addImg.setVisibility(View.VISIBLE);
                    } else {
                        addImg.setVisibility(View.GONE);
                    }
                } else {
                    addImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}