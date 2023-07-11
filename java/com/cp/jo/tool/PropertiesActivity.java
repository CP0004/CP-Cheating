package com.cp.jo.tool;

import android.graphics.Color;
import android.view.View;

import com.cp.jo.R;
import com.cp.jo.databinding.ActivityMainBinding;
import com.cp.jo.databinding.LayoutFloatBinding;

public class PropertiesActivity {
    //-- Page Splash
    public static void pageSplash(ActivityMainBinding binding) {
        binding.MainRootSplash.getRoot().setVisibility(View.VISIBLE);
        binding.MainRootKey.getRoot().setVisibility(View.GONE);
        binding.MainRootHome.getRoot().setVisibility(View.GONE);
        binding.MainRootSetting.getRoot().setVisibility(View.GONE);
        binding.MainRootLog.getRoot().setVisibility(View.GONE);
        binding.MainBtnActionbarBottom.setVisibility(View.GONE);
    }

    //-- Page Key
    public static void pageKey(ActivityMainBinding binding) {
        binding.MainRootSplash.getRoot().setVisibility(View.GONE);
        binding.MainRootKey.getRoot().setVisibility(View.VISIBLE);
        binding.MainRootHome.getRoot().setVisibility(View.GONE);
        binding.MainRootSetting.getRoot().setVisibility(View.GONE);
        binding.MainRootLog.getRoot().setVisibility(View.GONE);
        binding.MainBtnActionbarBottom.setVisibility(View.GONE);
    }

    //-- Page Home
    public static void pageHome(ActivityMainBinding binding) {
        binding.MainRootSplash.getRoot().setVisibility(View.GONE);
        binding.MainRootKey.getRoot().setVisibility(View.GONE);
        binding.MainRootHome.getRoot().setVisibility(View.VISIBLE);
        binding.MainRootSetting.getRoot().setVisibility(View.GONE);
        binding.MainRootLog.getRoot().setVisibility(View.GONE);
        binding.MainBtnActionbarBottom.setVisibility(View.VISIBLE);
    }

    //-- Page Setting
    public static void pageSetting(ActivityMainBinding binding) {
        binding.MainRootSplash.getRoot().setVisibility(View.GONE);
        binding.MainRootKey.getRoot().setVisibility(View.GONE);
        binding.MainRootHome.getRoot().setVisibility(View.GONE);
        binding.MainRootSetting.getRoot().setVisibility(View.VISIBLE);
        binding.MainRootLog.getRoot().setVisibility(View.GONE);
        binding.MainBtnActionbarBottom.setVisibility(View.VISIBLE);
    }

    //-- Page Log
    public static void pageLog(ActivityMainBinding binding) {
        binding.MainRootSplash.getRoot().setVisibility(View.GONE);
        binding.MainRootKey.getRoot().setVisibility(View.GONE);
        binding.MainRootHome.getRoot().setVisibility(View.GONE);
        binding.MainRootSetting.getRoot().setVisibility(View.GONE);
        binding.MainRootLog.getRoot().setVisibility(View.VISIBLE);
        binding.MainBtnActionbarBottom.setVisibility(View.GONE);
    }

    //-- Page Float Radar
    public static void pageFloatRadar(LayoutFloatBinding binding) {
        binding.FloatRootRadar.getRoot().setVisibility(View.VISIBLE);
        binding.FloatRootItems.getRoot().setVisibility(View.GONE);
        binding.FloatRootMemory.getRoot().setVisibility(View.GONE);
        binding.FloatRootSetting.getRoot().setVisibility(View.GONE);
        binding.FloatBtnRadar.setBackgroundResource(R.drawable.style_background_card_primary_top);
        binding.FloatBtnItem.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnMemory.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnSetting.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatTvRadar.setTextColor(Color.parseColor("#f8f8f8"));
        binding.FloatTvItem.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvMemory.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvSetting.setTextColor(Color.parseColor("#1c1a22"));
    }

    //-- Page Float Items
    public static void pageFloatItems(LayoutFloatBinding binding) {
        binding.FloatRootRadar.getRoot().setVisibility(View.GONE);
        binding.FloatRootItems.getRoot().setVisibility(View.VISIBLE);
        binding.FloatRootMemory.getRoot().setVisibility(View.GONE);
        binding.FloatRootSetting.getRoot().setVisibility(View.GONE);
        binding.FloatBtnRadar.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnItem.setBackgroundResource(R.drawable.style_background_card_primary_top);
        binding.FloatBtnMemory.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnSetting.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatTvRadar.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvItem.setTextColor(Color.parseColor("#f8f8f8"));
        binding.FloatTvMemory.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvSetting.setTextColor(Color.parseColor("#1c1a22"));
    }

    //-- Page Float Memory
    public static void pageFloatMemory(LayoutFloatBinding binding) {
        binding.FloatRootRadar.getRoot().setVisibility(View.GONE);
        binding.FloatRootItems.getRoot().setVisibility(View.GONE);
        binding.FloatRootMemory.getRoot().setVisibility(View.VISIBLE);
        binding.FloatRootSetting.getRoot().setVisibility(View.GONE);
        binding.FloatBtnRadar.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnItem.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnMemory.setBackgroundResource(R.drawable.style_background_card_primary_top);
        binding.FloatBtnSetting.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatTvRadar.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvItem.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvMemory.setTextColor(Color.parseColor("#f8f8f8"));
        binding.FloatTvSetting.setTextColor(Color.parseColor("#1c1a22"));
    }

    //-- Page Float Setting
    public static void pageFloatSetting(LayoutFloatBinding binding) {
        binding.FloatRootRadar.getRoot().setVisibility(View.GONE);
        binding.FloatRootItems.getRoot().setVisibility(View.GONE);
        binding.FloatRootMemory.getRoot().setVisibility(View.GONE);
        binding.FloatRootSetting.getRoot().setVisibility(View.VISIBLE);
        binding.FloatBtnRadar.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnItem.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnMemory.setBackgroundResource(R.drawable.style_background_card_in_sub_top);
        binding.FloatBtnSetting.setBackgroundResource(R.drawable.style_background_card_primary_top);
        binding.FloatTvRadar.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvItem.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvMemory.setTextColor(Color.parseColor("#1c1a22"));
        binding.FloatTvSetting.setTextColor(Color.parseColor("#f8f8f8"));
    }

    //-- Page Float Home
    public static void pageFloatHome(LayoutFloatBinding binding) {
        binding.FloatRootHome.setVisibility(View.VISIBLE);
        binding.FloatImgLogo.setVisibility(View.GONE);
    }

    //-- Page Float Logo
    public static void pageFloatLogo(LayoutFloatBinding binding) {
        binding.FloatRootHome.setVisibility(View.GONE);
        binding.FloatImgLogo.setVisibility(View.VISIBLE);
    }
}
