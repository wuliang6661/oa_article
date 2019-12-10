package com.article.oa_article.view.mobanmanager;

//先写一个接口
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);//移动时方法

    void onItemDissmiss(int position);//消失时方法
}

