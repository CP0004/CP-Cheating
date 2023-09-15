package com.cp.jo.tool;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureDetectors extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
