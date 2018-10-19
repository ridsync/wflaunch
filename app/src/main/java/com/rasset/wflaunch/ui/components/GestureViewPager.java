package com.rasset.wflaunch.ui.components;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.rasset.wflaunch.utils.Logger;

/**
 * Created by hajins on 2015-08-24.
 */
public class GestureViewPager extends ViewPager {

    private boolean isScrolled = true;

    public GestureViewPager(Context context) {
        super(context);
    }

    public GestureViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logger.d(this, "onTouchEvent isScrolled = "  + isScrolled  + "getX "  + ev.getX() + " getY "  + ev.getY());
        if (this.isScrolled) {
            return super.onTouchEvent(ev);
        }

        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Logger.d(this, "onScrollChanged isScrolled = " + isScrolled + " l = " + l + " t= " + t + " oldl " + oldl + " oldt " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logger.d(this, "onInterceptTouchEvent isScrolled = "  + isScrolled  + "getX "  + ev.getX() + " getY "  + ev.getY());
        if (this.isScrolled) {
            return super.onInterceptTouchEvent(ev);
        }
        Logger.d(this, "getTop = " + getTop());
        return false;
    }

    public void setSwipeEnabled(boolean enabled) {
        this.isScrolled = enabled;
    }
}
