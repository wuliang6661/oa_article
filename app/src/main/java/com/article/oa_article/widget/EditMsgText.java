package com.article.oa_article.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.article.oa_article.R;

public class EditMsgText extends LinearLayout {


    private TextView title;
    private EditText message;
    private TextView zhongdian;

    private Context context;

    public EditMsgText(Context context) {
        this(context, null);
    }

    public EditMsgText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("Recycle")
    public EditMsgText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        //初始化自定义属性信息
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditMsgText);
        String hintText = array.getString(R.styleable.EditMsgText_hint_text);
        String editText = array.getString(R.styleable.EditMsgText_edit_text);
        String titleText = array.getString(R.styleable.EditMsgText_title_text);
        int maxLength = array.getInt(R.styleable.EditMsgText_edit_max_length, 6);
        int editType = array.getInt(R.styleable.EditMsgText_edit_type, 0);
        boolean isZhongdian = array.getBoolean(R.styleable.EditMsgText_edit_zhongdian, false);
        View view = LayoutInflater.from(context).inflate(R.layout.edit_fouce, null);
        title = view.findViewById(R.id.edit_left_text);
        message = view.findViewById(R.id.edit_text);
        zhongdian = view.findViewById(R.id.zhongdian);

        initView();
        title.setText(titleText);
        message.setHint(hintText);
        message.setText(editText);
        message.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        if (editType == 1) {  //数字输入
            message.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (isZhongdian) {
            zhongdian.setVisibility(VISIBLE);
        }

        addView(view);
    }


    private void initView() {
        message.setOnFocusChangeListener((view, b) -> {
            if (!b) {    //失去焦点
                if (StringUtils.isEmpty(getText())) {
                    title.setTextColor(ContextCompat.getColor(context, R.color.f_white));
                    message.setTextColor(ContextCompat.getColor(context, R.color.f_white));
                } else {
                    title.setTextColor(ContextCompat.getColor(context, R.color.hint_color));
                    message.setTextColor(ContextCompat.getColor(context, R.color.f_white));
                }
            }
        });
    }


    public String getText() {
        return message.getText().toString().trim();
    }


    public void setText(String text) {
        message.setText(text);
    }

    public void setEnabled(boolean isEnable) {
        message.setEnabled(isEnable);
        title.setTextColor(ContextCompat.getColor(context, R.color.hint_color));
        message.setTextColor(ContextCompat.getColor(context, R.color.f_white));
    }

}
