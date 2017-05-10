package com.summer.meizitu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summer.meizitu.DetailsWebViewActivity;
import com.summer.meizitu.R;
import com.summer.meizitu.core.BaseListFragment;
import com.summer.meizitu.model.BaseModel;
import com.summer.meizitu.model.Benefit;
import com.summer.meizitu.request.Api;
import com.summer.meizitu.widgets.pull.BaseViewHolder;
import com.summer.meizitu.widgets.pull.PullRecycler;
import com.summer.meizitu.widgets.pull.layoutmanager.ILayoutManager;
import com.summer.meizitu.widgets.pull.layoutmanager.MyLinearLayoutManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AppFragment extends BaseListFragment<Benefit> {
    private int page = 1;

    public static Fragment newInstance() {
        AppFragment f = new AppFragment();
        return f;
    }

    public AppFragment() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getActivity());
    }

    class SampleViewHolder extends BaseViewHolder {

        TextView tv_desc;

        public SampleViewHolder(View itemView) {
            super(itemView);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        }

        @Override
        public void onBindViewHolder(int position) {
            tv_desc.setText(mDataList.get(position).desc);
        }

        @Override
        public void onItemClick(View view, int position) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsWebViewActivity.class);
            intent.putExtra("mUrl",mDataList.get(position).url);
            startActivity(intent);

        }

    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<BaseModel<ArrayList<Benefit>>> call = api.getAppdefaultBenefits(20, page++);

        call.enqueue(new Callback<BaseModel<ArrayList<Benefit>>>() {
                         @Override
                         public void onResponse(Call<BaseModel<ArrayList<Benefit>>> call, Response<BaseModel<ArrayList<Benefit>>> response) {
                             if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                                 mDataList.clear();
                             }
                             if (response.body().results == null || response.body().results.size() == 0) {
                                 recycler.enableLoadMore(false);
                             } else {
                                 recycler.enableLoadMore(true);
                                 mDataList.addAll(response.body().results);
                                 adapter.notifyDataSetChanged();
                             }
                             recycler.onRefreshCompleted();
                         }

                         @Override
                         public void onFailure(Call<BaseModel<ArrayList<Benefit>>> call, Throwable t) {
                             recycler.onRefreshCompleted();
                         }
                     }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}

