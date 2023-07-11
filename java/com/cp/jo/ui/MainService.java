package com.cp.jo.ui;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.cp.jo.R;
import com.cp.jo.databinding.LayoutFloatBinding;
import com.cp.jo.enums.SendCPP;
import com.cp.jo.tool.Backup;
import com.cp.jo.tool.GestureDetectors;
import com.cp.jo.tool.PropertiesActivity;
import com.cp.jo.tool.Tools;

public class MainService extends Service {

    static {
        System.loadLibrary("native-lib");
    }

    @SuppressLint("StaticFieldLeak")
    public static LayoutFloatBinding binding;
    private GestureDetector gestureDetector;
    private View view;
    public static MainService INSTANT;
    private WindowManager manager;
    private WindowManager.LayoutParams params;

    private float touchXLogo, touchYLogo;
    private float touchXLayout, touchYLayout;
    private int posXLogo, posYLogo;
    private int posXLayout, posYLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        //--------------- Refrains ---------------//
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme_CP);
        CreateOverly(contextThemeWrapper);
        binding = LayoutFloatBinding.bind(view);
        gestureDetector = new GestureDetector(this, new GestureDetectors());

        //--------------- Default Setting App ---------------//
        PropertiesActivity.pageFloatLogo(binding);
        PropertiesActivity.pageFloatRadar(binding);
        Backup.reStoreFloatWindow(this, binding);

        //--------------- Events ---------------//
        binding.FloatRootHome.setOnTouchListener((viewV, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Get Touch Y,X
                    posXLayout = params.x;
                    posYLayout = params.y;
                    touchXLayout = event.getRawX();
                    touchYLayout = event.getRawY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    params.x = posXLayout + (int) (event.getRawX() - touchXLayout);
                    params.y = posYLayout + (int) (event.getRawY() - touchYLayout);
                    manager.updateViewLayout(view, params);
                    return true;
            }
            return false;
        });

        binding.FloatImgLogo.setOnTouchListener((viewV, event) -> {
            if (gestureDetector.onTouchEvent(event)) {
                PropertiesActivity.pageFloatHome(binding);
                return true;
            } else {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        posXLogo = params.x;
                        posYLogo = params.y;
                        touchXLogo = event.getRawX();
                        touchYLogo = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = posXLogo + (int) (event.getRawX() - touchXLogo);
                        params.y = posYLogo + (int) (event.getRawY() - touchYLogo);
                        manager.updateViewLayout(view, params);
                        return true;
                }
                return false;
            }
        });

        binding.FloatBtnClose.setOnClickListener(v -> PropertiesActivity.pageFloatLogo(binding));
        binding.FloatBtnRadar.setOnClickListener(v -> PropertiesActivity.pageFloatRadar(binding));
        binding.FloatBtnItem.setOnClickListener(v -> PropertiesActivity.pageFloatItems(binding));
        binding.FloatBtnMemory.setOnClickListener(v -> PropertiesActivity.pageFloatMemory(binding));
        binding.FloatBtnSetting.setOnClickListener(v -> PropertiesActivity.pageFloatSetting(binding));

        //-- Radar
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbLine, true, SendCPP.LINE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbSkeleton, true, SendCPP.SKELETON.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbInfoPlayer, true, SendCPP.INFO_PLAYER.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbWeapon, true, SendCPP.WEAPON.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbMark, true, SendCPP.MARK.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbHealth, true, SendCPP.HEALTH.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbGrenade, true, SendCPP.GRENADE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootRadar.RadarCbDist, true, SendCPP.DIST.ordinal(), -1, true);
        //-- Memory
        Tools.OnChecked(this, binding.FloatRootMemory.MemoryCbLess, false, SendCPP.LESS.ordinal(), -1, Tools.checkPremium());
        Tools.OnChecked(this, binding.FloatRootMemory.MemoryCbCross, false, SendCPP.CROSS.ordinal(), -1, Tools.checkPremium());
        Tools.OnChecked(this, binding.FloatRootMemory.MemoryCbAimbot, false, SendCPP.IS_AIMBOT.ordinal(), -1, Tools.checkPremium());
        Tools.OnChecked(this, binding.FloatRootMemory.MemoryCbBullet, false, SendCPP.AIMBOT_SDK.ordinal(), -1, Tools.checkPremium());
        Tools.OnSeekBar(this, binding.FloatRootMemory.MemorySbIpadView, binding.FloatRootMemory.MemoryTvIpadView, binding.FloatRootMemory.MemoryTvIpadViewCount, false, SendCPP.IPAD_VIEW.ordinal(), true);
        //-- Setting
        Tools.OnSeekBar(this, binding.FloatRootSetting.SettingSbWidthLine, binding.FloatRootSetting.SettingTvWidthLine, binding.FloatRootSetting.SettingTvWidthLineCount, true, SendCPP.WIDTH_LINE.ordinal(), true);
        Tools.OnSeekBar(this, binding.FloatRootSetting.SettingSbRadius, binding.FloatRootSetting.SettingTvRadius, binding.FloatRootSetting.SettingTvRadiusCount, true, SendCPP.RADIUS360.ordinal(), true);
        Tools.OnSeekBar(this, binding.FloatRootSetting.SettingSbSizeItem, binding.FloatRootSetting.SettingTvSizeItem, binding.FloatRootSetting.SettingTvSizeItemCount, true, SendCPP.SIZE_ITEM.ordinal(), true);

        //-- Items
        //ITEM
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbItemDrop, true, SendCPP.DROP.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbItemBox, true, SendCPP.BOX.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbItemCoins, true, SendCPP.COINS.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbItemFlareGun, true, SendCPP.FLARE_GUN.ordinal(), -1, true);
        //AR
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArAKM, true, SendCPP.AKM.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArM16A4, true, SendCPP.M16A4.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArSCARL, true, SendCPP.SCAR_L.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArM416, true, SendCPP.M416.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArGROZA, true, SendCPP.GROZA.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArAUG, true, SendCPP.AUG.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArQBZ, true, SendCPP.QBZ.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArM762, true, SendCPP.M762.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArMk47, true, SendCPP.MK47.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbAr636C, true, SendCPP._636C.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArFAMAS, true, SendCPP.FAMAS.ordinal(), -1, true);
        //SR
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSrKar98k, true, SendCPP.KAR98K.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSrM24, true, SendCPP.M24.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSrAWM, true, SendCPP.AWM.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSrWin94, true, SendCPP.WIN94.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSrMosin, true, SendCPP.MOSIN.ordinal(), -1, true);
        //DMR
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrSKS, true, SendCPP.SKS.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrVSS, true, SendCPP.VSS.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrMini14, true, SendCPP.MINI14.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrMk14, true, SendCPP.MK14.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrSLR, true, SendCPP.SLR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrQBU, true, SendCPP.QUB.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMdrMk12, true, SendCPP.MK12.ordinal(), -1, true);
        //SMG
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgUZI, true, SendCPP.UZI.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgUMP45, true, SendCPP.UMP45.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgVector, true, SendCPP.VECTOR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgBizon, true, SendCPP.BIZON.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgThompson, true, SendCPP.THOMPSON.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgMP5K, true, SendCPP.MP5K.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbSmgP90, true, SendCPP.P90.ordinal(), -1, true);
        //SHOTGUN
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbShotgunS686, true, SendCPP.S686.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbShotgunS1897, true, SendCPP.S1897.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbShotgunS12k, true, SendCPP.S12K.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbShotgunDBS, true, SendCPP.DBS.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbShotgunM1014, true, SendCPP.M1014.ordinal(), -1, true);
        //LMG
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbLmgM249, true, SendCPP.M249.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbLmgDP28, true, SendCPP.DP_28.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbLmgM163, true, SendCPP.M163.ordinal(), -1, true);
        //SCOPE
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeX8, true, SendCPP.x8.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeX6, true, SendCPP.x6.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeX4, true, SendCPP.x4.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeX3, true, SendCPP.x3.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeX2, true, SendCPP.x2.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbScopeRedPoint, true, SendCPP.RED_POINT.ordinal(), -1, true);
        //MELEE
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMeleePan, true, SendCPP.PAN.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMeleeMachete, true, SendCPP.MACHETE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMeleeCrowbar, true, SendCPP.CROWBAR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMeleeSickle, true, SendCPP.SICKLE.ordinal(), -1, true);
        //OTHER
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbOtherCrossbow, true, SendCPP.CROSSBOW.ordinal(), -1, true);
        //HELMET
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbHelmetLv1, true, SendCPP.HELMET_LV1.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbHelmetLv2, true, SendCPP.HELMET_LV2.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbHelmetLv3, true, SendCPP.HELMET_LV3.ordinal(), -1, true);
        //ARMOR
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArmorLv1, true, SendCPP.ARMOR_LV1.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArmorLv2, true, SendCPP.ARMOR_LV2.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbArmorLv3, true, SendCPP.ARMOR_LV3.ordinal(), -1, true);
        //BACKPACK
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbBackpackLv1, true, SendCPP.BACKPACK_LV1.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbBackpackLv2, true, SendCPP.BACKPACK_LV2.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbBackpackLv3, true, SendCPP.BACKPACK_LV3.ordinal(), -1, true);
        //MAD KIT
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitEnergy, true, SendCPP.ENERGY.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitAdrenaline, true, SendCPP.ADRENALINE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitPainkiller, true, SendCPP.PAINKILLER.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitBandages, true, SendCPP.BANDAGES.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitFirst, true, SendCPP.FIRST.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitMadKit, true, SendCPP.MADKIT.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMadKitFuel, true, SendCPP.FUEL.ordinal(), -1, true);
        //MUZZLE
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleCompensator, true, SendCPP.COMPENSATOR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleVertical, true, SendCPP.VERTICAL.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleEQAr, true, SendCPP.EQ_AR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleEQSp, true, SendCPP.EQ_SP.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleEQSmg, true, SendCPP.EQ_SMG.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleEAr, true, SendCPP.E_AR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleESp, true, SendCPP.E_SP.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbMuzzleESmg, true, SendCPP.E_SMG.ordinal(), -1, true);
        //VEHICLE
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleUAZ, true, SendCPP.UAZ.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleBRDM, true, SendCPP.BRDM.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleBus, true, SendCPP.BUS.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleUTV, true, SendCPP.UTV.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleMonster, true, SendCPP.MONSTER.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehiclePickUp, true, SendCPP.PICKUP.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleRony, true, SendCPP.RONY.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleDacia, true, SendCPP.DACIA.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleCoupe, true, SendCPP.COUPE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleMirado, true, SendCPP.MIRADO.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleTukshai, true, SendCPP.TUKSHAI.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleSidecar, true, SendCPP.SIDECAR.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleSnowmobile, true, SendCPP.SNOWMOBILE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleSnowbike, true, SendCPP.SNOWBIKE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleMotorcycle, true, SendCPP.MOTORCYCIE.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleScooter, true, SendCPP.SCOOTER.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleBuggy, true, SendCPP.BUGGY.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleGlider, true, SendCPP.GILDER.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehiclePG117, true, SendCPP.PG117.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleAquaRail, true, SendCPP.AQUAPAIL.ordinal(), -1, true);
        Tools.OnChecked(this, binding.FloatRootItems.ItemsCbVehicleBicycle, true, SendCPP.BICYCLE.ordinal(), -1, true);
    }

    //-- Show overly Window
    @SuppressLint({"InflateParams", "RtlHardcoded"})
    void CreateOverly(ContextThemeWrapper context) {
        manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.layout_float, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        else
            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 500;
        params.y = 500;
        manager.addView(view, params);
    }

    //-- check off or on class
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) {
            manager.removeView(view);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}