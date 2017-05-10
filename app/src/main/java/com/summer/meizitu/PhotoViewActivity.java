package com.summer.meizitu;

import android.os.Bundle;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.summer.meizitu.core.BActivity;

public class PhotoViewActivity extends BActivity {
    private PhotoView img_photoview;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initView();

    }

    private void initView() {
        img_photoview = (PhotoView) findViewById(R.id.img_photoview);
        // 启用图片缩放功能
        img_photoview.enable();
        mUrl = getIntent().getStringExtra("mUrl");
        Glide.with(img_photoview.getContext())
                .load(mUrl)
                .centerCrop()
                .placeholder(R.color.app_primary_color)
                .crossFade()
                .into(img_photoview);

    }


}
