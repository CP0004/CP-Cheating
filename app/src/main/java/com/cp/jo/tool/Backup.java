package com.cp.jo.tool;

import static com.cp.jo.tool.Preference.getValueFloat;

import android.content.Context;

import com.cp.jo.databinding.LayoutFloatBinding;
import com.cp.jo.enums.SendCPP;

public class Backup {

    public static void reStoreFloatWindow(Context context, LayoutFloatBinding binding) {
        //-- Radar
        binding.FloatRootRadar.RadarCbLine.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbLine.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbSkeleton.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbSkeleton.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbInfoPlayer.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbInfoPlayer.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbWeapon.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbWeapon.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbMark.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbMark.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbHealth.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbHealth.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbGrenade.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbGrenade.getText().toString().trim()));
      //  binding.FloatRootRadar.RadarCbBox.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbBox.getText().toString().trim()));
        binding.FloatRootRadar.RadarCbDist.setChecked(Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbDist.getText().toString().trim()));
        //C++
        Tools.linkValue(SendCPP.LINE.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbLine.getText().toString().trim()));
        Tools.linkValue(SendCPP.SKELETON.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbSkeleton.getText().toString().trim()));
        Tools.linkValue(SendCPP.INFO_PLAYER.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbInfoPlayer.getText().toString().trim()));
        Tools.linkValue(SendCPP.WEAPON.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbWeapon.getText().toString().trim()));
        Tools.linkValue(SendCPP.MARK.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbMark.getText().toString().trim()));
        Tools.linkValue(SendCPP.HEALTH.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbHealth.getText().toString().trim()));
        Tools.linkValue(SendCPP.GRENADE.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbGrenade.getText().toString().trim()));
      //  Tools.linkValue(SendCPP.BOX.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbBox.getText().toString().trim()));
        Tools.linkValue(SendCPP.DIST.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootRadar.RadarCbDist.getText().toString().trim()));
//        //-- Memory
//        binding.FloatRootMemory.MemoryRbHeadAimbot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbHeadAimbot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryRbChestAimbot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbChestAimbot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryRbFootAimbot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbFootAimbot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryRbDistAimbot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbDistAimbot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryRbCrossAimbot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbCrossAimbot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryCbIsBot.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryCbIsBot.getText().toString().trim()));
//        binding.FloatRootMemory.MemoryCbIsKnock.setChecked(Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryCbIsKnock.getText().toString().trim()));
//        if (Preference.getValueFloat(context, binding.FloatRootMemory.MemoryTvFovAimbot.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootMemory.MemoryTvFovAimbot.getText().toString().trim(), 100.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootMemory.MemoryTvDistAimbot.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootMemory.MemoryTvDistAimbot.getText().toString().trim(), 100.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetScopeAimbot.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootMemory.MemoryTvOffsetScopeAimbot.getText().toString().trim(), 50.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbot.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbot.getText().toString().trim(), 50.0f);
//        binding.FloatRootMemory.MemorySbFovAimbot.setProgress((int) (getValueFloat(context, binding.FloatRootMemory.MemoryTvFovAimbot.getText().toString().trim()) * 10));
//        binding.FloatRootMemory.MemorySbDistAimbot.setProgress((int) (getValueFloat(context, binding.FloatRootMemory.MemoryTvDistAimbot.getText().toString().trim()) * 10));
//        binding.FloatRootMemory.MemorySbOffsetScopeAimbot.setProgress((int) (getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetScopeAimbot.getText().toString().trim()) * 10));
//        binding.FloatRootMemory.MemorySbOffsetNoScopeAimbot.setProgress((int) (getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbot.getText().toString().trim()) * 10));
//        binding.FloatRootMemory.MemoryTvFovAimbotCount.setText(String.valueOf(getValueFloat(context, binding.FloatRootMemory.MemoryTvFovAimbot.getText().toString().trim())));
//        binding.FloatRootMemory.MemoryTvDistAimbotCount.setText(String.valueOf(getValueFloat(context, binding.FloatRootMemory.MemoryTvDistAimbot.getText().toString().trim())));
//        binding.FloatRootMemory.MemoryTvOffsetScopeAimbotCount.setText(String.valueOf(getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetScopeAimbot.getText().toString().trim())));
//        binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbotCount.setText(String.valueOf(getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbot.getText().toString().trim())));
        //C++
//        Tools.linkValue(SendCPP.FOV_AIMBOT.ordinal(), getValueFloat(context, binding.FloatRootMemory.MemoryTvFovAimbot.getText().toString().trim()), false);
//        Tools.linkValue(SendCPP.DIST_AIMBOT.ordinal(), getValueFloat(context, binding.FloatRootMemory.MemoryTvDistAimbot.getText().toString().trim()), false);
//        Tools.linkValue(SendCPP.OFFSET_SCOPE.ordinal(), getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetScopeAimbot.getText().toString().trim()), false);
//        Tools.linkValue(SendCPP.OFFSET_NO_SCOPE.ordinal(), getValueFloat(context, binding.FloatRootMemory.MemoryTvOffsetNoScopeAimbot.getText().toString().trim()), false);
//        if (Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbHeadAimbot.getText().toString().trim()))
//            Tools.linkValue(SendCPP.TARGET_AIMBOT.ordinal(), 0, false);
//        else if (Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbChestAimbot.getText().toString().trim()))
//            Tools.linkValue(SendCPP.TARGET_AIMBOT.ordinal(), 1, false);
//        else if (Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbFootAimbot.getText().toString().trim()))
//            Tools.linkValue(SendCPP.TARGET_AIMBOT.ordinal(), 2, false);
//        if (Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbDistAimbot.getText().toString().trim()))
//            Tools.linkValue(SendCPP.PRIORITY_AIMBOT.ordinal(), 0, false);
//        else if (Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryRbCrossAimbot.getText().toString().trim()))
//            Tools.linkValue(SendCPP.PRIORITY_AIMBOT.ordinal(), 1, false);
//        Tools.linkValue(SendCPP.IS_BOT_AIMBOT.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryCbIsBot.getText().toString().trim()));
//        Tools.linkValue(SendCPP.IS_KNOCK_AIMBOT.ordinal(), -1, Preference.getValueBoolean(context, binding.FloatRootMemory.MemoryCbIsKnock.getText().toString().trim()));
        //-- Setting
        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvWidthLine.getText().toString().trim()) <= 0.0f)
            Preference.setValue(context, binding.FloatRootSetting.SettingTvWidthLine.getText().toString().trim(), 0.5f);
        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvRadius.getText().toString().trim()) <= 0.0f)
            Preference.setValue(context, binding.FloatRootSetting.SettingTvRadius.getText().toString().trim(), 10.0f);
        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeItem.getText().toString().trim()) <= 0.0f)
            Preference.setValue(context, binding.FloatRootSetting.SettingTvSizeItem.getText().toString().trim(), 16.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeVehicle.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootSetting.SettingTvSizeVehicle.getText().toString().trim(), 16.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvUpInfo.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootSetting.SettingTvUpInfo.getText().toString().trim(), 0.0f);
//        if (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeInfo.getText().toString().trim()) <= 0.0f)
//            Preference.setValue(context, binding.FloatRootSetting.SettingTvSizeInfo.getText().toString().trim(), 0.0f);
        binding.FloatRootSetting.SettingSbWidthLine.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvWidthLine.getText().toString().trim()) * 10));
        binding.FloatRootSetting.SettingSbRadius.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvRadius.getText().toString().trim()) * 10));
        binding.FloatRootSetting.SettingSbSizeItem.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeItem.getText().toString().trim()) * 10));
        //  binding.FloatRootSetting.SettingSbSizeVehicle.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeVehicle.getText().toString().trim()) * 10));
        //  binding.FloatRootSetting.SettingSbUpInfo.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvUpInfo.getText().toString().trim()) * 10));
        //  binding.FloatRootSetting.SettingSbSizeInfo.setProgress((int) (Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeInfo.getText().toString().trim()) * 10));
        binding.FloatRootSetting.SettingTvWidthLineCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvWidthLine.getText().toString().trim())));
        binding.FloatRootSetting.SettingTvRadiusCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvRadius.getText().toString().trim())));
        binding.FloatRootSetting.SettingTvSizeItemCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeItem.getText().toString().trim())));
        //  binding.FloatRootSetting.SettingTvSizeVehicleCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeVehicle.getText().toString().trim())));
        //  binding.FloatRootSetting.SettingTvUpInfoCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvUpInfo.getText().toString().trim())));
        //  binding.FloatRootSetting.SettingTvSizeInfoCount.setText(String.valueOf(Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeInfo.toString().trim())));
        //C++
        Tools.linkValue(SendCPP.WIDTH_LINE.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvWidthLine.getText().toString().trim()), false);
        Tools.linkValue(SendCPP.RADIUS360.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvRadius.getText().toString().trim()), false);
        Tools.linkValue(SendCPP.SIZE_ITEM.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeItem.getText().toString().trim()), false);
        // Tools.linkValue(SendCPP.SIZE_VEHICLE.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeVehicle.getText().toString().trim()), false);
        // Tools.linkValue(SendCPP.SIZE_INFO.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvSizeInfo.getText().toString().trim()), false);
        // Tools.linkValue(SendCPP.UP_INFO.ordinal(), Preference.getValueFloat(context, binding.FloatRootSetting.SettingTvUpInfo.getText().toString().trim()), false);
        //-- Items
        //ITEM
        binding.FloatRootItems.ItemsCbItemDrop.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbItemDrop.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbItemBox.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbItemBox.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbItemCoins.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbItemCoins.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbItemFlareGun.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbItemFlareGun.getText().toString().trim()));
        //AR
        binding.FloatRootItems.ItemsCbArAKM.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArAKM.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArM16A4.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArM16A4.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArSCARL.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArSCARL.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArM416.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArM416.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArGROZA.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArGROZA.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArAUG.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArAUG.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArQBZ.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArQBZ.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArM762.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArM762.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArMk47.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArMk47.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbAr636C.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbAr636C.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArFAMAS.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArFAMAS.getText().toString().trim()));
        //SR
        binding.FloatRootItems.ItemsCbSrKar98k.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSrKar98k.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSrM24.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSrM24.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSrAWM.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSrAWM.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSrWin94.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSrWin94.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSrMosin.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSrMosin.getText().toString().trim()));
        //DMR
        binding.FloatRootItems.ItemsCbMdrSKS.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrSKS.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrVSS.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrVSS.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrMini14.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrMini14.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrMk14.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrMk14.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrSLR.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrSLR.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrQBU.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrQBU.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMdrMk12.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMdrMk12.getText().toString().trim()));
        //SMG
        binding.FloatRootItems.ItemsCbSmgUZI.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgUZI.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgUMP45.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgUMP45.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgVector.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgVector.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgBizon.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgBizon.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgThompson.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgThompson.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgMP5K.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgMP5K.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbSmgP90.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbSmgP90.getText().toString().trim()));
        //SHOTGUN
        binding.FloatRootItems.ItemsCbShotgunS686.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbShotgunS686.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbShotgunS1897.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbShotgunS1897.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbShotgunS12k.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbShotgunS12k.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbShotgunDBS.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbShotgunDBS.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbShotgunM1014.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbShotgunM1014.getText().toString().trim()));
        //LMG
        binding.FloatRootItems.ItemsCbLmgM249.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbLmgM249.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbLmgDP28.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbLmgDP28.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbLmgM163.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbLmgM163.getText().toString().trim()));
        //SCOPE
        binding.FloatRootItems.ItemsCbScopeX8.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeX8.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbScopeX6.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeX6.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbScopeX4.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeX4.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbScopeX3.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeX3.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbScopeX2.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeX2.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbScopeRedPoint.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbScopeRedPoint.getText().toString().trim()));
        //MELEE
        binding.FloatRootItems.ItemsCbMeleePan.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMeleePan.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMeleeMachete.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMeleeMachete.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMeleeCrowbar.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMeleeCrowbar.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMeleeSickle.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMeleeSickle.getText().toString().trim()));
        //OTHER
        binding.FloatRootItems.ItemsCbOtherCrossbow.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbOtherCrossbow.getText().toString().trim()));
        //HELMET
        binding.FloatRootItems.ItemsCbHelmetLv1.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbHelmetLv1.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbHelmetLv2.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbHelmetLv2.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbHelmetLv3.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbHelmetLv3.getText().toString().trim()));
        //ARMOR
        binding.FloatRootItems.ItemsCbArmorLv1.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArmorLv1.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArmorLv2.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArmorLv2.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbArmorLv3.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbArmorLv3.getText().toString().trim()));
        //BACKPACK
        binding.FloatRootItems.ItemsCbBackpackLv1.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbBackpackLv1.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbBackpackLv2.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbBackpackLv2.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbBackpackLv3.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbBackpackLv3.getText().toString().trim()));
        //MAD KIT
        binding.FloatRootItems.ItemsCbMadKitEnergy.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitEnergy.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitAdrenaline.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitAdrenaline.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitPainkiller.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitPainkiller.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitBandages.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitBandages.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitFirst.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitFirst.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitMadKit.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitMadKit.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMadKitFuel.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMadKitFuel.getText().toString().trim()));
        //MUZZLE
        binding.FloatRootItems.ItemsCbMuzzleCompensator.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleCompensator.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleVertical.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleVertical.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleEQAr.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleEQAr.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleEQSp.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleEQSp.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleEQSmg.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleEQSmg.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleEAr.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleEAr.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleESp.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleESp.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbMuzzleESmg.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbMuzzleESmg.getText().toString().trim()));
        //VEHICLE
        binding.FloatRootItems.ItemsCbVehicleUAZ.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleUAZ.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleBRDM.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleBRDM.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleBus.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleBus.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleUTV.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleUTV.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleMonster.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleMonster.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehiclePickUp.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehiclePickUp.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleRony.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleRony.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleDacia.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleDacia.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleCoupe.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleCoupe.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleMirado.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleMirado.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleTukshai.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleTukshai.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleSidecar.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleSidecar.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleSnowmobile.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleSnowmobile.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleSnowbike.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleSnowbike.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleMotorcycle.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleMotorcycle.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleScooter.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleScooter.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleBuggy.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleBuggy.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleGlider.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleGlider.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehiclePG117.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehiclePG117.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleAquaRail.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleAquaRail.getText().toString().trim()));
        binding.FloatRootItems.ItemsCbVehicleBicycle.setChecked(Preference.getValueBoolean(context, binding.FloatRootItems.ItemsCbVehicleBicycle.getText().toString().trim()));
    }
}
