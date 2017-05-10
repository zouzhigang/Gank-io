package com.summer.meizitu.viewpage;

import java.lang.reflect.Field;
import java.util.TimerTask;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class MyViewPager extends ViewPager {
	private ViewPager mPager;
	private int abc = 1;
	// private int mCurPage = 0;
	private float mLastMotionX;
	String TAG = "@";
	private GuidePageChangeListener m_guide_pagechange_listener = new GuidePageChangeListener();
	private float firstDownX;
	private float firstDownY;
	private boolean flag = false;

	private float mLastMotionY;
	private float xDistance, yDistance;

	private GestureDetector mGestureDetector;

	private Handler adClickHandler;

	public ViewPager getmPager() {
		return mPager;
	}

	public void setmPager(ViewPager mPager) {
		this.mPager = mPager;
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPageChangeListener(m_guide_pagechange_listener);
	}

	public MyViewPager(Context context) {
		super(context);
		this.setOnPageChangeListener(m_guide_pagechange_listener);
	}

	// public int getCurPage() {
	// return mCurPage;
	// }

	// 是否启动图片轮播功能
	public void setIsOpen(boolean open) {

		if (open == true) {
			handler.postDelayed(timerTask, 4500);
			
			try {
	            Field field = ViewPager.class.getDeclaredField("mScroller");
	            field.setAccessible(true);
	            FixedSpeedScroller scroller = new FixedSpeedScroller(this.getContext(),
	                    new AccelerateInterpolator());
	            field.set(this, scroller);
	            scroller.setmDuration(700);
	        } catch (Exception e) {
	           // LogUtils.e(TAG, "", e);
	        }
		}
		
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = 0;
			yDistance = 0;
			// down事件注册不拦截
			getParent().requestDisallowInterceptTouchEvent(true);
			abc = 1;
			// down下来的x y坐标
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (abc == 1) {
				// 获得滑动的距离 x y
				xDistance += Math.abs(x - mLastMotionX);
				yDistance += Math.abs(y - mLastMotionY);
				// 如果X滑动比较长 认为这个事件是左右滑动
				if (xDistance > yDistance + 5) {
					// 向右滑动 并且当前处于第一页 允许出现menu也就是取消注册不允许拦截
					if (x - mLastMotionX > 2 && getCurrentItem() == 0) {
						abc = 0;
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					// 向左滑动
					if (x - mLastMotionX < -2
							&& getCurrentItem() == getAdapter().getCount() - 1) {
						abc = 0;
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else if (yDistance > xDistance + 5) {
					// 如果Y滑动比较长 直接取消注册
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				// if (y - mLastMotionY > 2) {
				// abc = 0;
				// getParent().requestDisallowInterceptTouchEvent(false);
				// }
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			// getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(arg0);
	}

	public void setGestureDetector(GestureDetector gesturedetector) {
		mGestureDetector = gesturedetector;
		setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return false;
			}
		});
	}

	/** 指引页面改监听器 */
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// Log.i("voice", arg0+ "-------------onPageScrollStateChanged");

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			// mCurPage = arg0;
		}
	}

	// 图片轮播具体方法

	public void setHandler(Handler handler) {
		this.adClickHandler = handler;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (getAdapter() == null || getAdapter().getCount() == 0) {
				return;
			}
			int curItem = getCurrentItem() + 1;
			if (curItem >= getAdapter().getCount()) {
				curItem = 0;
			}
			setCurrentItem(curItem, true);
		}
	};

	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(1);
			handler.postDelayed(this, 4500);
		}
	};
	
	
	public class FixedSpeedScroller extends Scroller {
	    private int mDuration = 1500;
	 
	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }
	 
	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }
	 
	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }
	 
	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }
	 
	    public void setmDuration(int time) {
	        mDuration = time;
	    }
	 
	    public int getmDuration() {
	        return mDuration;
	    }
	}
}
