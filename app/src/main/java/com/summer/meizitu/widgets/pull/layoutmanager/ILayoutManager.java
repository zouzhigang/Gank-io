package com.summer.meizitu.widgets.pull.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.summer.meizitu.widgets.pull.BaseListAdapter;



public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}
