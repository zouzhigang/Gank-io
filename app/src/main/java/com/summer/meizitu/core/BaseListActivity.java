package com.summer.meizitu.core;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.summer.meizitu.R;
import com.summer.meizitu.widgets.pull.BaseListAdapter;
import com.summer.meizitu.widgets.pull.BaseViewHolder;
import com.summer.meizitu.widgets.pull.DividerItemDecoration;
import com.summer.meizitu.widgets.pull.PullRecycler;
import com.summer.meizitu.widgets.pull.layoutmanager.ILayoutManager;
import com.summer.meizitu.widgets.pull.layoutmanager.MyLinearLayoutManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;



public abstract class BaseListActivity<T> extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_base_list);
    }

    @Override
    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    @Override
    protected void setUpData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
//        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
        return null ;
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }
    protected int getItemType(int position) {
        return 0;
    }
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
