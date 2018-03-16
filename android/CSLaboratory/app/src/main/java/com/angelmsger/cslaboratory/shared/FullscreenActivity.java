package com.angelmsger.cslaboratory.shared;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.angelmsger.cslaboratory.R;

/**
 * 一个能够通过与用户互动显示和隐藏系统 UI 的全屏幕 activity (i.e.
 * status bar and navigation/system bar)。 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * 根据 {@link #AUTO_HIDE_DELAY_MILLIS} 决定系统 UI 是否自动隐藏。
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * 如果 {@link #AUTO_HIDE} 被设置在隐藏系统 UI 前等待的毫秒数。
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     *  一些旧设备在 UI widget 更新与导航栏状态改变之间需要一小段延迟时间。
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // 延时去除状态栏和导航栏

            // 注意以下的一些内容是 API 16 (Jelly Bean) 和 API 19 (KitKat) 新增的。
            // 使用它们是安全的，因为他们是编译时内联的，并且在早期设备上不会有任何作用。
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // 延时显示 UI 组件
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * 用来为布局内部 UI 控制延迟隐藏系统 UI 的触摸事件监听器。
     * 这将阻止在隐藏过程中对 activity UI 的操作。
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // 设置监听器监听用户触摸以显示或隐藏系统 UI。
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // 当与 UI 控制器互动时延时任何 hide() 过程以防止在 UI 变动过程中加入的不和谐行为。
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // 在 activity 被建立后的短时间延时后触发 hide()，提示用户 UI 控制器当前是可用的。
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // 首先隐藏 UI。
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // 创建一个线程以隐藏状态栏和导航栏。
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // 显示系统栏。
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // 创建一个线程以延时一小段时间后显示系统组件
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * 计划一个在 [delay] 毫秒后对 hide() 的调用, 取消之前
     * 已经计划的调用。
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
