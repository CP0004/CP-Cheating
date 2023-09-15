package com.cp.jo.draw;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.cp.jo.tool.LocalData;
import com.cp.jo.tool.Tools;
import com.cp.jo.ui.MainActivity;

public class Overlay extends Service {

    private Context context;
    private Overlay overlay;
    private WindowManager windowManager;
    private Draw draw;

    //-- C++ Link
    private native void Close();

    private native boolean getReady();

    public static native void DrawOn(Draw draw, Canvas canvas);

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Start();
        ShowWindow();
    }

    //Check For data C++ and on
    public void Start() {
        if (overlay == null) {
            Thread Timer1 = new Thread(this::getReady);
            Timer1.start();
            Thread Timer2 = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    //Exe file
                    Tools.commandShell(context, LocalData.STATIC_LOCAL_PATH_DOWNLOAD + MainActivity.matrix);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Timer2.start();
        }
    }

    //-- layout drawing Show
    private void ShowWindow() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        draw = new Draw(context);
        WindowManager.LayoutParams params = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0, 0, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.RGBA_8888);
        else
            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0, 0, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.RGBA_8888);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;
        windowManager.addView(draw, params);
    }

    //-- Hide NavigationBarHeight
    private int GetNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        @SuppressLint({"DiscouragedApi", "InternalInsetResource"}) int resourced = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourced > 0 && !hasMenuKey) {
            return getResources().getDimensionPixelSize(resourced);
        }
        return 0;
    }

    //-- Stop Draw
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (draw != null) {
            windowManager.removeView(draw);
            Close();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}