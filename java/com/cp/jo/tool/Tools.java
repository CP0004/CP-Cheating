package com.cp.jo.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.jo.R;
import com.cp.jo.draw.Overlay;
import com.cp.jo.ui.MainService;
import com.google.android.material.chip.Chip;
import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Tools {

    //--Text Decryption System
    public static String Dec(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }

    //-- Get Uuid Device
    public static String getUuidDevice(Context context) {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String GetDeviceName;
        if (model.startsWith(manufacturer)) {
            GetDeviceName = model;
        } else {
            GetDeviceName = manufacturer + " " + model;
        }
        @SuppressLint("HardwareIds") String key = (GetDeviceName + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + Build.HARDWARE.replace("-", ""));
        UUID uniqueKey = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
        return uniqueKey.toString().replace("-", "");
    }

    //-- Get Info Device
    public static String getNameDevices() {
        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String model = Build.MODEL;
        int baseOs = Build.VERSION.SDK_INT;
        return manufacturer + "_" + brand + "_" + model + "_" + baseOs;
    }

    //-- Get Version Name App
    public static String getVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        String versionName = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //-- Open Link
    public static void goToURL(Context context, String url) {
        if (url != null) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    //-- Copy Text
    public static void copyText(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "" + text, Toast.LENGTH_SHORT).show();
    }

    //-- Onclick Checked
    public static void OnChecked(Context context, Chip view, boolean isSave, int index, float value, boolean isPremium) {
        view.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isPremium) {
                if (isSave)
                    Preference.setValue(context, view.getText().toString().trim(), isChecked);
                if (index >= 0) linkValue(index, value, isChecked);
            } else {
                view.setChecked(Tools.securityBool(LocalData.BOOL_F));
                Toast.makeText(context, context.getResources().getString(R.string.Text_premium), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //-- Onclick SeekBar
    public static void OnSeekBar(Context context, SeekBar view, TextView text, TextView count, boolean isSave, int index, boolean isPremium) {
        view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isPremium) {
                    float finalProgress = (float) (progress * 0.1);
                    count.setText(String.valueOf(finalProgress));
                    if (isSave)
                        Preference.setValue(context, text.getText().toString().trim(), finalProgress);
                    if (index >= 0) linkValue(index, finalProgress, false);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.Text_premium), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /////////////////////////////// Date ///////////////////////////////////////////////
    //-- Convert TimeMillis
    public static long timeMillis() {
        return System.currentTimeMillis();
    }

    //-- Convert TimeMillis To Date
    public static String millisToDate(long time) {
        return String.valueOf(DateFormat.format("yyyy/MM/dd | HH:mm:ss a", time));
    }

    //-- Convert TimeMillis To Number
    public static long millisToNumber(long time) {
        return Long.parseLong(String.valueOf(DateFormat.format("yyyyMMddkkmmss", time)));
    }

    //-- Extra Hour to date
    public static long addHour(int hour) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.HOUR_OF_DAY, hour);
        return c.getTimeInMillis();
    }

    //-- Extra Minute to date
    public static long addMinute(int minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MINUTE, minute);
        return c.getTimeInMillis();
    }

    //-- Extra Second to date
    public static long addSecond(int second) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, second);
        return c.getTimeInMillis();
    }


    //-- Difference Between Two Dates
    public static long differenceTwoDates(long deviceDate, long serverDate) {
        return TimeUnit.SECONDS.convert(serverDate - deviceDate, TimeUnit.MILLISECONDS);
    }


    public static String calculateTime(Context context, long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        return context.getResources().getString(R.string.Text_day) + ":" + day + "  " + context.getResources().getString(R.string.Text_hour) + ":" + hours + "  " + context.getResources().getString(R.string.Text_minute) + ":" + minute + "  " + context.getResources().getString(R.string.Text_second) + ":" + second;
    }

    //- Convert Base64 To ByteImage
    public static Bitmap Base64ToByte(String imageBase64) {
        byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    //-- Generator Key
    public static String generatorKey() {
        String ALLOWED_CHARACTERS = "ABCDEFGHIJKMLNOBQRSTUVWXYZabcdefghijkmlnobqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    //--//////////////////////

    //-- Permission Root
    public static void permissionRoot(Context context) {
        try {
            Runtime.getRuntime().exec("su -c " + context.getFilesDir() + "/" + "root", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void commandShell(Context context, String cmd) {
        Shell.cmd("su -c " + cmd).exec();
    }

    public static boolean launchPackage(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            Toast.makeText(context, "Package " + packageName + " not found in your device, please install it", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void injection(Context context, String pkg) {
        if (launchPackage(context, pkg)) {
            new Handler().postDelayed(() -> {
                context.startService(new Intent(context, MainService.class));
                context.startService(new Intent(context, Overlay.class));
            }, 1000);
            Toast.makeText(context, "LOADING...", Toast.LENGTH_LONG).show();
        }
    }

    //-- Writer Files
    public static void setDataFiles(Context context, String path, String nameFiles, String dataFiles) {
        try {
            File file = new File(path, nameFiles);
            FileWriter writer = new FileWriter(file);
            writer.append(dataFiles);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            Log.d("TAG_TEST", "setDataFiles: " + e);
            e.printStackTrace();
        }
    }

    //-- C++
    public static native String rights(int index);

    public static native boolean securityBool(float value);

    public static native boolean checkPremium();

    public static native void linkValue(int index, float value, boolean iCheck);

    public static native void setPremium(boolean premium);

}

