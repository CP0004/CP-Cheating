package com.cp.jo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.Keep;

import com.cp.jo.enums.SendCPP;
import com.cp.jo.tool.Tools;
import com.cp.jo.ui.MainService;

@Keep
public class Draw extends View {
    Paint mStrokePaint;
    Paint mFilledPaint;
    Paint mTextPaint;
    Paint mTextInfo;

    public Draw(Context context) {
        super(context, null, 0);
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
        InitializePaints();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == VISIBLE) {
            ClearCanvas(canvas);
            Overlay.DrawOn(this, canvas);
        }
        postInvalidate();
    }

    public void InitializePaints() {
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(false);
        mStrokePaint.setColor(Color.rgb(0, 0, 0));

        mFilledPaint = new Paint();
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setAntiAlias(false);
        mFilledPaint.setColor(Color.rgb(0, 0, 0));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(false);
        mTextPaint.setColor(Color.rgb(0, 0, 0));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setShadowLayer(2, 2, 2, Color.argb(100, 0, 0, 0));

        mTextInfo = new Paint();
        mTextInfo.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextInfo.setAntiAlias(false);
        mTextInfo.setColor(Color.rgb(0, 0, 0));
        mTextInfo.setTextAlign(Paint.Align.LEFT);
        mTextInfo.setStrokeWidth(1);
        mTextInfo.setShadowLayer(2, 2, 2, Color.argb(100, 0, 0, 0));


    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    //Line
    public void DrawLine(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }

    //RectStock
    public void DrawRect(Canvas cvs, int a, int r, int g, int b, float strokes, float x, float y, float width, float height) {
        mStrokePaint.setStrokeWidth(strokes);
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        cvs.drawRect(x, y, width, height, mStrokePaint);
    }

    //RectFill
    public void DrawFilledRect(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawRect(x, y, width, height, mFilledPaint);
    }

    //Text All
    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    //Text All
    public void DrawInfo(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextInfo.setARGB(a, r, g, b);
        mTextInfo.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextInfo);
    }

    //Circle Stroke
    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius, float strokeZ) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(strokeZ);
        cvs.drawCircle(posX, posY, radius, mStrokePaint);
    }

    //Circle Fill
    public void DrawFilledCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawCircle(posX, posY, radius, mFilledPaint);
    }

    private String Nation(String code) {
        if (code.equals("GL"))
            code = "Guest";
        else
            code = new String(Character.toChars((Character.codePointAt(code, 0) - 65) + 127462)) + new String(Character.toChars((Character.codePointAt(code, 1) - 65) + 127462));
        return code;
    }

    public void DrawWeapon(Canvas cvs, int a, int r, int g, int b, int id, int ammo, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        String nameWeapon = GetWeapon(id);
        if (nameWeapon != null) cvs.drawText(nameWeapon, posX, posY, mTextPaint);
    }

    //Item Show
    public void DrawItems(Canvas cvs, String itemName, float distance, float posX, float posY, float size) {
        mTextPaint.setColor(Color.argb(255, 80, 173, 204));
        mTextPaint.setTextSize(size);

        if (ObjectItems(itemName) != null) if (ObjectItems(itemName).equals("AirDropBox")) {
            cvs.drawText(ObjectItems(itemName) + "  -  " + Math.round(distance), posX, posY, mTextPaint);
        } else {
            cvs.drawText(ObjectItems(itemName), posX, posY, mTextPaint);
        }
    }

    // -- Convert Char To String
    public String CharToString(String text) {
        String[] namespace = text.split(":");
        char[] naming = new char[namespace.length];
        for (int i = 0; i < namespace.length; i++) {
            naming[i] = (char) Integer.parseInt(namespace[i]);
        }
        return new String(naming);
    }

    public void InfoPLayer(Canvas cvs, int a, int r, int g, int b, int teamId, String namePlayer, String flagPlayer, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(teamId + "   " + CharToString(namePlayer), posX, posY, mTextPaint);
    }

    //Vehicles Show (filter)
    public void DrawVehicles(Canvas cvs, String VehiclesName, float distance, float posX, float posY, float size, int hp, int fuel, boolean isAim) {
        mTextPaint.setColor(Color.argb(255, 255, 255, 255));
        mTextPaint.setTextSize(size);

        Tools.linkValue(SendCPP.HIDE_ITEMS.ordinal(), -1, ObjectVehicle(VehiclesName) != null);

        if (ObjectVehicle(VehiclesName) != null && fuel > 0 && hp > 0) {
            cvs.drawText(ObjectVehicle(VehiclesName) + "  [" + (int) distance + "]", posX, posY, mTextPaint);
        }

        if (fuel <= 0 && ObjectVehicle(VehiclesName) != null) {
            mTextPaint.setColor(Color.argb(255, 255, 157, 0));
            cvs.drawText("Empty Of Fuel" + "  [" + (int) distance + "]", posX, posY, mTextPaint);
        }

        if (hp <= 0 && ObjectVehicle(VehiclesName) != null) {
            mTextPaint.setColor(Color.argb(255, 255, 0, 0));
            cvs.drawText("Vehicle Explosive" + "  [" + (int) distance + "]", posX, posY, mTextPaint);
        }
    }

    public String ObjectVehicle(String name) {
        if (MainService.binding.FloatRootItems.ItemsCbVehicleBuggy.isChecked() && name.contains("Buggy")) {
            return "Buggy";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleBRDM.isChecked() && name.contains("BRDM")) {
            return "BRDM";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleBus.isChecked() && name.contains("Bus")) {
            return "Bus";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleUTV.isChecked() && name.contains("UTV")) {
            return "UTV";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleMonster.isChecked() && name.contains("Bigfoot")) {
            return "Monster";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehiclePickUp.isChecked() && name.contains("PickUp")) {
            return "PickUp";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleRony.isChecked() && name.contains("Rony")) {
            return "Rony";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleDacia.isChecked() && name.contains("Dacia")) {
            return "Dacia";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleCoupe.isChecked() && name.contains("CoupeRB")) {
            return "Coupe";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleMirado.isChecked() && name.contains("Mirado")) {
            return "Mirado";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleTukshai.isChecked() && name.contains("Tuk")) {
            return "Tukshai";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleUAZ.isChecked() && name.contains("UAZ")) {
            return "UAZ";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleSidecar.isChecked() && name.contains("MotorcycleC")) {
            return "Sidecar";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleSnowmobile.isChecked() && name.contains("Snowmobile")) {
            return "Snowmobile";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleSnowbike.isChecked() && name.contains("Snowbike")) {
            return "Snowbike";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleMotorcycle.isChecked() && name.contains("Motorcycle")) {
            return "Motorcycle";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleScooter.isChecked() && name.contains("Scooter")) {
            return "Scooter";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehiclePG117.isChecked() && name.contains("PG117")) {
            return "PG117";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleAquaRail.isChecked() && name.contains("AquaRail")) {
            return "AquaRail";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleGlider.isChecked() && name.contains("Motorglider")) {
            return "Glider";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleGlider.isChecked() && name.contains("Bike")) {
            return "Bicycle";
        } else {
            return null;
        }
    }

    public String ObjectItems(String name) {
        //AR
        if (MainService.binding.FloatRootItems.ItemsCbArAKM.isChecked() && name.contains("AKM")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "AKM";
        } else if (MainService.binding.FloatRootItems.ItemsCbArM16A4.isChecked() && name.contains("M16A4")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "M16A4";
        } else if (MainService.binding.FloatRootItems.ItemsCbArSCARL.isChecked() && name.contains("SCAR")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "SCAR-L";
        } else if (MainService.binding.FloatRootItems.ItemsCbArM416.isChecked() && name.contains("M416")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "M416";
        } else if (MainService.binding.FloatRootItems.ItemsCbArGROZA.isChecked() && name.contains("Groza")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "Groza";
        } else if (MainService.binding.FloatRootItems.ItemsCbArAUG.isChecked() && name.contains("AUG")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "AUG";
        } else if (MainService.binding.FloatRootItems.ItemsCbArQBZ.isChecked() && name.contains("QBZ")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "QBZ";
        } else if (MainService.binding.FloatRootItems.ItemsCbArM762.isChecked() && name.contains("M762")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "M762";
        } else if (MainService.binding.FloatRootItems.ItemsCbArMk47.isChecked() && name.contains("Mk47")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "Mk47";
        } else if (MainService.binding.FloatRootItems.ItemsCbAr636C.isChecked() && name.contains("G36")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "636C";
        } else if (MainService.binding.FloatRootItems.ItemsCbArFAMAS.isChecked() && name.contains("FAMAS")) {
            mTextPaint.setColor(Color.argb(255, 250, 255, 117));
            return "FAMAS";
        }
        //SP
        else if (MainService.binding.FloatRootItems.ItemsCbSrKar98k.isChecked() && name.contains("Kar98k")) {
            mTextPaint.setColor(Color.argb(255, 128, 255, 82));
            return "Kar98k";
        } else if (MainService.binding.FloatRootItems.ItemsCbSrM24.isChecked() && name.contains("M24")) {
            mTextPaint.setColor(Color.argb(255, 128, 255, 82));
            return "M24";
        } else if (MainService.binding.FloatRootItems.ItemsCbSrAWM.isChecked() && name.contains("AWM")) {
            mTextPaint.setColor(Color.argb(255, 128, 255, 82));
            return "AWM";
        } else if (MainService.binding.FloatRootItems.ItemsCbSrWin94.isChecked() && name.contains("Win94")) {
            mTextPaint.setColor(Color.argb(255, 128, 255, 82));
            return "Win94";
        } else if (MainService.binding.FloatRootItems.ItemsCbSrMosin.isChecked() && name.contains("Mosin")) {
            mTextPaint.setColor(Color.argb(255, 128, 255, 82));
            return "Mosin";
        }
        //Dmr
        else if (MainService.binding.FloatRootItems.ItemsCbMdrSKS.isChecked() && name.contains("SKS")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "SKS";
        } else if (MainService.binding.FloatRootItems.ItemsCbMdrVSS.isChecked() && name.contains("VSS")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "VSS";
        } else if (MainService.binding.FloatRootItems.ItemsCbMdrMini14.isChecked() && name.contains("Mini14")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "Mini14";
        } else if (MainService.binding.FloatRootItems.ItemsCbMdrMk14.isChecked() && name.contains("Mk14")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "Mk14";
        } else if (MainService.binding.FloatRootItems.ItemsCbMdrSLR.isChecked() && name.contains("SLR")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "SLR";
        } else if (MainService.binding.FloatRootItems.ItemsCbArQBZ.isChecked() && name.contains("QBZ")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "QBZ";
        } else if (MainService.binding.FloatRootItems.ItemsCbMdrMk12.isChecked() && name.contains("Mk12")) {
            mTextPaint.setColor(Color.argb(255, 82, 255, 163));
            return "Mk12";
        }
        //Smg
        else if (MainService.binding.FloatRootItems.ItemsCbSmgUZI.isChecked() && name.contains("Uzi")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "Uzi";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgUMP45.isChecked() && name.contains("UMP9")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "UMP9";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgVector.isChecked() && name.contains("Vector")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "Vector";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgThompson.isChecked() && name.contains("TommyGun")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "TommyGun";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgBizon.isChecked() && name.contains("PP19")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "PP19";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgMP5K.isChecked() && name.contains("MP5k")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "MP5k";
        } else if (MainService.binding.FloatRootItems.ItemsCbSmgP90.isChecked() && name.contains("P90")) {
            mTextPaint.setColor(Color.argb(255, 255, 166, 82));
            return "P90";
        }
        //Shotgun
        else if (MainService.binding.FloatRootItems.ItemsCbShotgunS686.isChecked() && name.contains("S686")) {
            mTextPaint.setColor(Color.argb(255, 191, 133, 78));
            return "S686";
        } else if (MainService.binding.FloatRootItems.ItemsCbShotgunS1897.isChecked() && name.contains("S1897")) {
            mTextPaint.setColor(Color.argb(255, 191, 133, 78));
            return "S1897";
        } else if (MainService.binding.FloatRootItems.ItemsCbShotgunS12k.isChecked() && name.contains("S12k")) {
            mTextPaint.setColor(Color.argb(255, 191, 133, 78));
            return "S12k";
        } else if (MainService.binding.FloatRootItems.ItemsCbShotgunDBS.isChecked() && name.contains("DP12")) {
            mTextPaint.setColor(Color.argb(255, 191, 133, 78));
            return "DP12";
        } else if (MainService.binding.FloatRootItems.ItemsCbShotgunM1014.isChecked() && name.contains("M1014")) {
            mTextPaint.setColor(Color.argb(255, 191, 133, 78));
            return "M1014";
        }
        //Lmg
        else if (MainService.binding.FloatRootItems.ItemsCbLmgM249.isChecked() && name.contains("M249")) {
            mTextPaint.setColor(Color.argb(255, 78, 191, 185));
            return "M249";
        } else if (MainService.binding.FloatRootItems.ItemsCbLmgDP28.isChecked() && name.contains("DP28")) {
            mTextPaint.setColor(Color.argb(255, 78, 191, 185));
            return "DP28";
        } else if (MainService.binding.FloatRootItems.ItemsCbLmgM163.isChecked() && name.contains("MG3")) {
            mTextPaint.setColor(Color.argb(255, 78, 191, 185));
            return "M163";
        }
        //Scope
        else if (MainService.binding.FloatRootItems.ItemsCbScopeX8.isChecked() && name.contains("8X")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "x8";
        } else if (MainService.binding.FloatRootItems.ItemsCbScopeX6.isChecked() && name.contains("6X")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "x6";
        } else if (MainService.binding.FloatRootItems.ItemsCbScopeX4.isChecked() && name.contains("4X")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "x4";
        } else if (MainService.binding.FloatRootItems.ItemsCbScopeX3.isChecked() && name.contains("3X")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "x3";
        } else if (MainService.binding.FloatRootItems.ItemsCbScopeX2.isChecked() && name.contains("2X")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "x2";
        } else if (MainService.binding.FloatRootItems.ItemsCbScopeRedPoint.isChecked() && name.contains("MZJ_HD")) {
            mTextPaint.setColor(Color.argb(255, 167, 78, 191));
            return "RedDot";
        }
        //Melee
        else if (MainService.binding.FloatRootItems.ItemsCbMeleeSickle.isChecked() && name.contains("Sickle")) {
            mTextPaint.setColor(Color.argb(255, 209, 113, 155));
            return "Sickle";
        } else if (MainService.binding.FloatRootItems.ItemsCbMeleePan.isChecked() && name.contains("Pan")) {
            mTextPaint.setColor(Color.argb(255, 209, 113, 155));
            return "Pan";
        } else if (MainService.binding.FloatRootItems.ItemsCbMeleeCrowbar.isChecked() && name.contains("Cowbar")) {
            mTextPaint.setColor(Color.argb(255, 209, 113, 155));
            return "Crowbar";
        } else if (MainService.binding.FloatRootItems.ItemsCbMeleeMachete.isChecked() && name.contains("Machete")) {
            mTextPaint.setColor(Color.argb(255, 209, 113, 155));
            return "Machete";
        }
        //Other
        else if (MainService.binding.FloatRootItems.ItemsCbOtherCrossbow.isChecked() && name.contains("Crossbow")) {
            mTextPaint.setColor(Color.argb(255, 209, 113, 155));
            return "Crossbow";
        }
        //Helmet
        else if (MainService.binding.FloatRootItems.ItemsCbHelmetLv1.isChecked() && name.contains("Helmet_Lv1")) {
            mTextPaint.setColor(Color.argb(255, 96, 156, 189));
            return "Helmet_1";
        } else if (MainService.binding.FloatRootItems.ItemsCbHelmetLv2.isChecked() && name.contains("Helmet_Lv2")) {
            mTextPaint.setColor(Color.argb(255, 96, 156, 189));
            return "Helmet_2";
        } else if (MainService.binding.FloatRootItems.ItemsCbHelmetLv3.isChecked() && name.contains("Helmet_Lv3")) {
            mTextPaint.setColor(Color.argb(255, 96, 156, 189));
            return "Helmet_3";
        }
        //Armor
        else if (MainService.binding.FloatRootItems.ItemsCbArmorLv1.isChecked() && name.contains("Armor_Lv1")) {
            mTextPaint.setColor(Color.argb(255, 96, 189, 121));
            return "Armor_1";
        } else if (MainService.binding.FloatRootItems.ItemsCbArmorLv2.isChecked() && name.contains("Armor_Lv2")) {
            mTextPaint.setColor(Color.argb(255, 96, 189, 121));
            return "Armor_2";
        } else if (MainService.binding.FloatRootItems.ItemsCbArmorLv3.isChecked() && name.contains("Armor_Lv3")) {
            mTextPaint.setColor(Color.argb(255, 96, 189, 121));
            return "Armor_3";
        }
        //BackPack
        else if (MainService.binding.FloatRootItems.ItemsCbBackpackLv1.isChecked() && name.contains("Bag_Lv1")) {
            mTextPaint.setColor(Color.argb(255, 135, 96, 189));
            return "Bag_1";
        } else if (MainService.binding.FloatRootItems.ItemsCbBackpackLv2.isChecked() && name.contains("Bag_Lv2")) {
            mTextPaint.setColor(Color.argb(255, 135, 96, 189));
            return "Bag_2";
        } else if (MainService.binding.FloatRootItems.ItemsCbBackpackLv3.isChecked() && name.contains("Bag_Lv3")) {
            mTextPaint.setColor(Color.argb(255, 135, 96, 189));
            return "Bag_3";
        }
        //Mad kit
        else if (MainService.binding.FloatRootItems.ItemsCbMadKitEnergy.isChecked() && name.contains("Drink")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "Energy";
        } else if (MainService.binding.FloatRootItems.ItemsCbMadKitPainkiller.isChecked() && name.contains("Pills")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "Painkiller";
        } else if (MainService.binding.FloatRootItems.ItemsCbMadKitFuel.isChecked() && name.contains("Destructible")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "Fuel";
        } else if (MainService.binding.FloatRootItems.ItemsCbMadKitAdrenaline.isChecked() && name.contains("Injection")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "Adrenaline";
        } else if (MainService.binding.FloatRootItems.ItemsCbMadKitFirst.isChecked() && name.contains("Firstaid")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "First";
        } else if (MainService.binding.FloatRootItems.ItemsCbMadKitMadKit.isChecked() && name.contains("FirstAidbox")) {
            mTextPaint.setColor(Color.argb(255, 255, 82, 82));
            return "MadKit";
        }
        //Muzzle
        else if (MainService.binding.FloatRootItems.ItemsCbMuzzleVertical.isChecked() && name.contains("Vertical")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "Vertical";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleCompensator.isChecked() && name.contains("QK_Large")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "Compensator";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleEQAr.isChecked() && name.contains("DJ_Large_EQ")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "AR_EQ";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleEAr.isChecked() && name.contains("DJ_Large_E")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "AR_E";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleESmg.isChecked() && name.contains("DJ_Mid_E")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "SMG_E";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleEQSmg.isChecked() && name.contains("DJ_Mid_EQ")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "SMG_EQ";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleESp.isChecked() && name.contains("DJ_Sniper_E")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "SNIPER_E";
        } else if (MainService.binding.FloatRootItems.ItemsCbMuzzleEQSp.isChecked() && name.contains("DJ_Sniper_EQ")) {
            mTextPaint.setColor(Color.argb(255, 156, 101, 111));
            return "SNIPER_EQ";
        } else if (MainService.binding.FloatRootItems.ItemsCbVehicleBicycle.isChecked() && name.contains("Bike")) {
            return "Bicycle";
        }
        //item
        else if (MainService.binding.FloatRootItems.ItemsCbItemDrop.isChecked() && name.contains("PlayerDeadInventoryBox_C")) {
            mTextPaint.setColor(Color.argb(255, 214, 214, 214));
            return "AirDropBox";
        } else if (MainService.binding.FloatRootItems.ItemsCbItemBox.isChecked() && name.contains("PickUpListWrapperActor")) {
            mTextPaint.setColor(Color.argb(255, 214, 214, 214));
            return "Box";
        } else if (MainService.binding.FloatRootItems.ItemsCbItemFlareGun.isChecked() && name.contains("Flaregun")) {
            mTextPaint.setColor(Color.argb(255, 185, 243, 43));
            return "Flaregun";
        } else if (MainService.binding.FloatRootItems.ItemsCbItemCoins.isChecked() && name.contains("GoldenToken")) {
            mTextPaint.setColor(Color.argb(255, 20, 255, 20));
            return "$1";
        } else {
            return null;
        }
    }

    private String GetWeapon(int id) {
        //AR and SMG
        if (id == 101006) return "AUG";
        if (id == 101008) return "M7";
        if (id == 101003) return "SCAR";
        if (id == 101004) return "M4";
        if (id == 101002) return "M16";
        if (id == 101009) return "Mk47";
        if (id == 101010) return "G36C";
        if (id == 101007) return "QBZ";
        if (id == 101001) return "AKM";
        if (id == 101005) return "Groza";
        if (id == 102005) return "Bizon";
        if (id == 102004) return "Tommy";
        if (id == 102007) return "MP5";
        if (id == 102002) return "UMP";
        if (id == 102003) return "Vector";
        if (id == 102001) return "Uzi";
        if (id == 105002) return "DP";
        if (id == 105001) return "Bkcy";
        //Snipers
        if (id == 103003) return "AWM";
        if (id == 103010) return "QBU";
        if (id == 103009) return "SLR";
        if (id == 103004) return "SKS";
        if (id == 103006) return "Mini";
        if (id == 103002) return "M24";
        if (id == 103001) return "Kar";
        if (id == 103005) return "VSS";
        if (id == 103008) return "Win";
        if (id == 103007) return "Mk14";
        //Shotguns and Hand weapons
        if (id == 104003) return "S12K";
        if (id == 104004) return "DBS";
        if (id == 104001) return "S686";
        if (id == 104002) return "S1897";
        if (id == 108003) return "Sickle";
        if (id == 108001) return "Machete";
        if (id == 108002) return "Crowbar";
        if (id == 107001) return "CrossBow";
        if (id == 108004) return "Pan";
        //Pistols
        if (id == 106006) return "SawedOff";
        if (id == 106003) return "R1895";
        if (id == 106008) return "Vz61";
        if (id == 106001) return "P92";
        if (id == 106004) return "P18C";
        if (id == 106005) return "R45";
        if (id == 106002) return "P1911";
        if (id == 106010) return "DesertEagle";
        return null;
    }

}