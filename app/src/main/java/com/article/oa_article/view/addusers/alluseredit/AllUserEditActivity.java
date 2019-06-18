package com.article.oa_article.view.addusers.alluseredit;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.util.phone.PhoneDto;
import com.article.oa_article.view.moveaddperson.MoveAddPersonActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 编辑批量联系人页面
 */

public class AllUserEditActivity extends MVPBaseActivity<AllUserEditContract.View, AllUserEditPresenter>
        implements AllUserEditContract.View {

    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.person_recycle)
    RecyclerView personRecycle;
    @BindView(R.id.add_pinglei)
    LinearLayout addPinglei;

    List<PhoneDto> selectPerson;

    private boolean isNeiBu;

    List<AddUserRequest> requests;

    private int selectPosition = 0;

    @Override
    protected int getLayout() {
        return R.layout.act_add_user_edit;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("批量添加联系人");

        btnAlbum.setText("保存");
        btnAlbum.setVisibility(View.VISIBLE);

        isNeiBu = getIntent().getBooleanExtra("isNeiBu", false);
        selectPerson = (List<PhoneDto>) getIntent().getSerializableExtra("select");
        requests = new ArrayList<>();
        for (PhoneDto phoneDto : selectPerson) {
            AddUserRequest request = new AddUserRequest();
            request.setPhone(phoneDto.getTelPhone());
            request.setPhoto(phoneDto.getPhoto());
            request.setName(phoneDto.getName());
            requests.add(request);
        }

        initView();
        setAdapter();
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        personRecycle.setLayoutManager(manager);
    }


    @OnClick(R.id.btn_album)
    public void save() {
        for (AddUserRequest request : requests) {
            request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
            if (request.getDepartId() == 0 && request.getLabelId() == 0) {
                showToast("请补全添加人员的信息！");
                return;
            }
        }
        mPresenter.addUsers(requests);
    }


    @OnClick(R.id.add_pinglei)
    public void add() {
        finish();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<AddUserRequest> adapter = new LGRecycleViewAdapter<AddUserRequest>(requests) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_person_edit;
            }

            @Override
            public void convert(LGViewHolder holder, AddUserRequest phoneDto, int position) {
                holder.setText(R.id.person_name, phoneDto.getName());
                holder.setText(R.id.phone, phoneDto.getPhone());
                ImageView personImg = (ImageView) holder.getView(R.id.person_img);
                if (!StringUtils.isEmpty(phoneDto.getPhoto())) {
                    Glide.with(AllUserEditActivity.this).load(Uri.parse(phoneDto.getPhoto())).into(personImg);
                }
            }
        };
        adapter.setOnItemClickListener(R.id.delete_person, (view, position) -> {
            requests.remove(position);
            adapter.setData(requests);
        });
        adapter.setOnItemClickListener(R.id.edit_person, (view, position) -> {
            selectPosition = position;
            Intent intent = new Intent(AllUserEditActivity.this, MoveAddPersonActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isNeiBu", isNeiBu);
            bundle.putBoolean("isEdit", true);
            bundle.putSerializable("user", requests.get(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, 0x11);
        });
        personRecycle.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0x11:
                AddUserRequest request = (AddUserRequest) data.getSerializableExtra("user");
                requests.set(selectPosition, request);
                setAdapter();
                break;
        }
    }

    @Override
    public void sourss() {
        showToast("添加成功,已发送添加好友请求！");
        AppManager.getAppManager().goHome();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
