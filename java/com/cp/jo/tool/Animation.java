package com.cp.jo.tool;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.cp.jo.R;

public class Animation {

    public static int DelaySplash = 500;
    public static int Delay = 100;

    //Animate SplashActivity
    public static void AnimateSplash(final View view, char typeSplash, Context context) {
        int typeId = R.anim.splash_bottom;
        switch (typeSplash) {
            case 'T':
                typeId = R.anim.splash_top;
                break;
            case 'B':
                typeId = R.anim.splash_bottom;
                break;
            case 'E':
                typeId = R.anim.splash_end;
                break;
            case 'S':
                typeId = R.anim.splash_start;
                break;
            default:
                break;
        }
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, typeId);
        view.startAnimation(animation);
        new Handler().postDelayed(view::clearAnimation, DelaySplash);
    }

    //Animate Bounce
    public static void AnimateBounce(@NonNull final View view, Context context) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce_anim);
        view.startAnimation(animation);
        new Handler().postDelayed(view::clearAnimation, Delay);
    }

    //Animate Bounce
    public static void AnimateLayout(@NonNull final View view, Context context) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_layout);
        view.startAnimation(animation);
        new Handler().postDelayed(view::clearAnimation, Delay);
    }

}