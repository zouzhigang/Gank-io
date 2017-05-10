package com.summer.meizitu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.summer.meizitu.core.BActivity;
import com.summer.meizitu.fragment.AndroidFragment;
import com.summer.meizitu.fragment.AppFragment;
import com.summer.meizitu.fragment.ExpandingResourcesFragment;
import com.summer.meizitu.fragment.FuliFragment;
import com.summer.meizitu.fragment.IOSFragment;
import com.summer.meizitu.fragment.RestVideoFragment;
import com.summer.meizitu.viewpage.MyViewPager;
import com.summer.meizitu.viewpage.TabPageIndicator;

/**
 * App首页
 */
public class HomeActivity extends BActivity {
    private MyViewPager mPager;
    private String[] CONTENT = new String[] { "福利", "Android", "iOS","拓展资源","休息视频","App" };
    private FragmentPagerAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView() ;
    }
    private void initView(){
        mAdapter = new ZimilanProductAdapter(getSupportFragmentManager());
        mPager = (MyViewPager) findViewById(R.id.tab_pager);
        mPager.setAdapter(mAdapter);
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        indicator.setViewPager(mPager);
    }

    class ZimilanProductAdapter extends FragmentPagerAdapter {
        public ZimilanProductAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return FuliFragment.newInstance();
                case 1:
                    return AndroidFragment.newInstance();
                case 2:
                    return IOSFragment.newInstance();
                case 3:
                    return ExpandingResourcesFragment.newInstance();
                case 4:
                    return RestVideoFragment.newInstance() ;
                case 5:
                    return AppFragment.newInstance() ;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序!", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            System.gc();
            System.exit(0);
        }
    }
}
