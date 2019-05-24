package com.article.oa_article.view.addusers;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.phone.PhoneDto;
import com.article.oa_article.util.phone.PhoneUtil;
import com.article.oa_article.view.addusers.alluseredit.AllUserEditActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 批量从通讯录添加好友
 */

public class AddUsersActivity extends MVPBaseActivity<AddUsersContract.View, AddUsersPresenter>
        implements AddUsersContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.person_recycle)
    RecyclerView personRecycle;

    @SuppressLint("UseSparseArrays")
    private Map<Integer, PhoneDto> selectPersons = new HashMap<>();

    private boolean isNeiBu;

    @Override
    protected int getLayout() {
        return R.layout.act_add_users;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("添加手机通讯录");


        isNeiBu = getIntent().getExtras().getBoolean("isNeiBu");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        personRecycle.setLayoutManager(manager);
        requestPermission();
        initView();
    }


    private void initView() {
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getPersonList(editable.toString());
            }
        });
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        } else {
            getPersonList("");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    getPersonList("");
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, "未开启通讯录权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取通讯录用户
     */
    private void getPersonList(String msg) {
        List<PhoneDto> phones = new PhoneUtil(this).searchContacts(msg);
        selectPersons.clear();
        LGRecycleViewAdapter<PhoneDto> adapter = new LGRecycleViewAdapter<PhoneDto>(phones) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_persons_layout;
            }

            @Override
            public void convert(LGViewHolder holder, PhoneDto phoneDto, int position) {
                holder.setText(R.id.person_name, phoneDto.getName());
                holder.setText(R.id.phone, phoneDto.getTelPhone());
                CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
                ImageView personImg = (ImageView) holder.getView(R.id.person_img);
                if (!StringUtils.isEmpty(phoneDto.getPhoto())) {
                    Glide.with(AddUsersActivity.this).load(Uri.parse(phoneDto.getPhoto())).into(personImg);
                }
                checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) {
                        selectPersons.put(position, phoneDto);
                    } else {
                        selectPersons.remove(position);
                    }
                });
            }
        };
        personRecycle.setAdapter(adapter);
    }


    @OnClick(R.id.next_button)
    public void addUserClick() {
        List<PhoneDto> selctPerson = new ArrayList<>(selectPersons.values());
        Intent intent = new Intent(this, AllUserEditActivity.class);
        intent.putExtra("isNeiBu", isNeiBu);
        intent.putExtra("select", (Serializable) selctPerson);
        startActivity(intent);
    }

}
