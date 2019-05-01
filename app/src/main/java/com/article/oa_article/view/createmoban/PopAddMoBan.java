package com.article.oa_article.view.createmoban;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.article.oa_article.R;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.view.SelectPersonAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PopAddMoBan extends PopupWindow {

    private Activity activity;
    private View dialogView;

    private EditText taskName;
    private TextView personName;
    private PersonBO personBO;

    private int selectPosition = -1;

    PopAddMoBan(Activity activity, String name, String person, int userId) {
        super(activity);

        EventBus.getDefault().register(this);
        this.activity = activity;
        dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_moban, null);
        initView(name, person, userId);
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> {
            EventBus.getDefault().unregister(this);
            backgroundAlpha(1f);
        });
    }


    /**
     * 初始化布局
     */
    private void initView(String name, String person, int userId) {
        taskName = dialogView.findViewById(R.id.edit_task_name);
        RelativeLayout selectPerson = dialogView.findViewById(R.id.select_person);
        personName = dialogView.findViewById(R.id.person_name);
        TextView cancle = dialogView.findViewById(R.id.cancle);
        Button commit = dialogView.findViewById(R.id.next_button);

        if (!StringUtils.isEmpty(name)) {
            taskName.setText(name);
            personName.setText(person);
            personBO = new PersonBO();
            personBO.setId(userId);
            personBO.setName(person);
        }
        selectPerson.setOnClickListener(view -> {
            Intent intent = new Intent(activity, SelectPersonAct.class);
            activity.startActivity(intent);
        });
        cancle.setOnClickListener(view -> dismiss());
        commit.setOnClickListener(view -> {
            String strName = taskName.getText().toString().trim();
            String strPerson = personName.getText().toString().trim();

            if (StringUtils.isEmpty(strName)) {
                ToastUtils.showShort("请输入任务名称！");
                return;
            }
            if (StringUtils.isEmpty(strPerson)) {
                ToastUtils.showShort("请选择执行人！");
                return;
            }
            if (listener != null) {
                listener.commit(selectPosition, strName, personBO);
                dismiss();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonBO a) {
        this.personBO = a;
        personName.setText(a.getName());
    }

    /***
     * 显示时将屏幕置为透明
     */
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    private onCommitListener listener;

    public void setListener(onCommitListener listener) {
        this.listener = listener;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }

    public interface onCommitListener {

        void commit(int position, String taskName, PersonBO person);
    }
}

