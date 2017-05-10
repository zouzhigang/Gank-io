package com.summer.meizitu;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.summer.meizitu.core.BActivity;


public class DetailsWebViewActivity extends BActivity {
    private WebView webView;
    private SwipeRefreshLayout swipeRefresh;
    private String mUrl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details_web_view);
        mUrl = getIntent().getStringExtra("mUrl");
        initView();
    }

    private void initView(){
        //SwipeRefresh
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_contain);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新加载刷新页面
                webView.loadUrl(webView.getUrl());
            }
        });
        //首次启动刷新页面
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                webView.loadUrl(webView.getUrl());
            }
        });
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        //WebView
        webView = (WebView)findViewById(R.id.web_show);

        webView.loadUrl(mUrl);
        //添加javaScript支持
        webView.getSettings().setJavaScriptEnabled(true);
        //取消滚动条
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //触摸焦点起作用
        webView.requestFocus();
        //点击链接继续在当前browser中响应
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置进度条
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    //隐藏进度条
                    swipeRefresh.setRefreshing(false);
                }else if(!swipeRefresh.isRefreshing()){
                    swipeRefresh.setRefreshing(true);
                }
            }
        });

    }

}
