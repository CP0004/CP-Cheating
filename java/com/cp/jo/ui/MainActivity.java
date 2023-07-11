package com.cp.jo.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cp.jo.R;
import com.cp.jo.adapter.AdapterProtection;
import com.cp.jo.databinding.ActivityMainBinding;
import com.cp.jo.databinding.LayoutFloatBinding;
import com.cp.jo.draw.Overlay;
import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.enums.SendCPP;
import com.cp.jo.model.ModelKey;
import com.cp.jo.model.ModelProtection;
import com.cp.jo.model.ModelServer;
import com.cp.jo.tool.Animation;
import com.cp.jo.tool.Dialog;
import com.cp.jo.tool.LocalData;
import com.cp.jo.tool.Preference;
import com.cp.jo.tool.PropertiesActivity;
import com.cp.jo.tool.Tools;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    public static String game;
    public static String matrix;

    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private boolean isKey;


    private AdapterProtection adapterProtection;

    private long dateExpiration = 0L;
    private Dialog dialog;
    private long timeRefreshPage = 0L;

    private Thread thread;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //--------------- Refrains ---------------//
        if (viewModel == null)
            viewModel = new ViewModelProvider(MainActivity.this).get(MainViewModel.class);

        //---------------  Default  Setting App ---------------//
        @SuppressLint("InflateParams") View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_float, null);
        LayoutFloatBinding bindingFloat = LayoutFloatBinding.bind(view);
        PropertiesActivity.pageSplash(binding);
        binding.MainRootSplash.SplashBtnContinue.setVisibility(View.GONE);
        binding.MainRootSplash.SplashPbLoading.setVisibility(View.VISIBLE);
        binding.MainRootKey.KeyBtnLogin.setEnabled(false);
        binding.MainRootKey.KeyBtnLogin.setVisibility(View.VISIBLE);
        binding.MainRootKey.KeyPbLoading.setVisibility(View.INVISIBLE);

        //--------------- Callback Data on Server ---------------//
        requestPermission(Preference.getValueString(MainActivity.this, LocalData.KEY_KEY), false);
        //--------------- LOOP TIME ---------------//
        //-- Refresh Time Key
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            if (dateExpiration > 0L) {
                                binding.MainRootHome.HomeTvTimeLeft.setVisibility(View.VISIBLE);
                                if (Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration) > 0) {
                                    binding.MainRootSetting.SettingTvTimeLeft.setText(Tools.calculateTime(MainActivity.this, Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration)));
                                    binding.MainRootHome.HomeTvTimeLeft.setText(Tools.calculateTime(MainActivity.this, Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration)));
                                } else
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                            } else binding.MainRootHome.HomeTvTimeLeft.setVisibility(View.GONE);
                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

        //--------------- Adapters ---------------//
        //--Adapter Protection
        adapterProtection = new AdapterProtection(MainActivity.this, new ArrayList<>(), (dataProtections, position) -> {
            if (dataProtections.get(position).isPremium()) {
                if (Tools.checkPremium()) {
                    Tools.commandShell(MainActivity.this, dataProtections.get(position).getCommandShell());
                }
            } else {
                Tools.commandShell(MainActivity.this, dataProtections.get(position).getCommandShell());
            }
        });

        //--------------- Event ---------------//
        binding.MainRootSplash.SplashBtnContinue.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootSplash.SplashBtnContinue, MainActivity.this);
            requestPermission(Preference.getValueString(MainActivity.this, LocalData.KEY_KEY), true);
        });

        binding.MainRootKey.KeyEtKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isInfo();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isInfo();
                if (s.toString().trim().length() >= 20) {
                    binding.MainRootKey.KeyTilKey.setError(null);
                    isKey = true;
                } else {
                    binding.MainRootKey.KeyTilKey.setError(getResources().getString(R.string.Text_Key_Limits));
                    isKey = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isInfo();
            }
        });

        binding.MainRootKey.KeyBtnLogin.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootKey.KeyBtnLogin, MainActivity.this);
            if (binding.MainRootKey.KeyEtKey.getText().toString().trim().length() == 20) {
                requestPermission(binding.MainRootKey.KeyEtKey.getText().toString().trim(), true);
                binding.MainRootKey.KeyBtnLogin.setVisibility(View.INVISIBLE);
                binding.MainRootKey.KeyPbLoading.setVisibility(View.VISIBLE);
            }

        });

        binding.MainRootSetting.SettingTvLogout.setOnClickListener(v -> {
            Preference.setValue(MainActivity.this, LocalData.KEY_KEY, null);
            PropertiesActivity.pageKey(binding);
        });

        binding.MainBtnActionbarBottom.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Menu_btn_home:
                    if (!item.isChecked()) {
                        PropertiesActivity.pageHome(binding);
                    }
                    break;
                case R.id.Menu_btn_setting:
                    if (!item.isChecked()) {
                        PropertiesActivity.pageSetting(binding);
                    }
                    break;
            }
            return true;
        });

        binding.MainRootHome.HomeBtnSelectGame32.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnSelectGame32, MainActivity.this);
            matrix = LocalData.STATIC_LIB_MATRIX_32;
        });

        binding.MainRootHome.HomeBtnSelectGame64.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnSelectGame64, MainActivity.this);
            matrix = LocalData.STATIC_LIB_MATRIX_64;
        });

        binding.MainRootHome.HomeBtnInjectionGl.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnInjectionGl, MainActivity.this);
            if (matrix != null)
                Tools.injection(MainActivity.this, LocalData.STATIC_PACKAGE_GL);
            else
                Toast.makeText(this, getResources().getString(R.string.Text_Select_CPU), Toast.LENGTH_SHORT).show();
        });

        binding.MainRootHome.HomeBtnInjectionKr.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnInjectionKr, MainActivity.this);
            if (matrix != null)
                Tools.injection(MainActivity.this, LocalData.STATIC_PACKAGE_KR);
            else
                Toast.makeText(this, getResources().getString(R.string.Text_Select_CPU), Toast.LENGTH_SHORT).show();
        });

        binding.MainRootHome.HomeBtnInjectionVn.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnInjectionVn, MainActivity.this);
            if (matrix != null)
                Tools.injection(MainActivity.this, LocalData.STATIC_PACKAGE_VN);

            else
                Toast.makeText(this, getResources().getString(R.string.Text_Select_CPU), Toast.LENGTH_SHORT).show();
        });

        binding.MainRootHome.HomeBtnInjectionTw.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnInjectionTw, MainActivity.this);
            if (matrix != null)
                Tools.injection(MainActivity.this, LocalData.STATIC_PACKAGE_TW);
            else
                Toast.makeText(this, getResources().getString(R.string.Text_Select_CPU), Toast.LENGTH_SHORT).show();
        });

        binding.MainRootHome.HomeBtnInjectionFt.setOnClickListener(v -> {
            Animation.AnimateBounce(binding.MainRootHome.HomeBtnInjectionFt, MainActivity.this);
            matrix = LocalData.STATIC_LIB_MATRIX_FARLIGHT_32;
            if (matrix != null)
                Tools.injection(MainActivity.this, LocalData.STATIC_PACKAGE_FT);
            else
                Toast.makeText(this, getResources().getString(R.string.Text_Select_CPU), Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint("InlinedApi")
    private void requestPermission(String key, boolean isRefresh) {
        Dexter.withContext(getApplicationContext()).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
                Manifest.permission.QUERY_ALL_PACKAGES,
                Manifest.permission.BIND_CALL_REDIRECTION_SERVICE,
                Manifest.permission.MANAGE_MEDIA,
                Manifest.permission.MANAGE_DOCUMENTS).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (!Settings.canDrawOverlays(MainActivity.this)) {
                    startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
                    binding.MainRootSplash.SplashBtnContinue.setVisibility(View.VISIBLE);
                    binding.MainRootSplash.SplashPbLoading.setVisibility(View.GONE);
                    Tools.permissionRoot(MainActivity.this);
                } else {
                    Tools.permissionRoot(MainActivity.this);
                    binding.MainRootSplash.SplashBtnContinue.setVisibility(View.GONE);
                    binding.MainRootSplash.SplashPbLoading.setVisibility(View.VISIBLE);
                    //--------------- Request and Response Server ---------------//
                    viewModel.initGetDataServer(Tools.Dec(Tools.rights(SendCPP.EMAIL.ordinal())), Tools.Dec(Tools.rights(SendCPP.PASSWORD.ordinal())), isRefresh);
                    if (viewModel.getDateServer != null)
                        viewModel.getDateServer.observe(MainActivity.this, callbackServer -> {
                            Log.d("TAG_TEST", "GET DATA SERVER :" + callbackServer.toString());
                            if (callbackServer.getState() == CallbackRequest.SUCCESSFUL.ordinal()) {
                                if (Preference.getValueString(MainActivity.this, LocalData.KEY_KEY) != null && Preference.getValueString(MainActivity.this, LocalData.KEY_KEY).length() == 20 || binding.MainRootKey.KeyEtKey.getText().toString() != null && binding.MainRootKey.KeyEtKey.getText().toString().length() == 20) {
                                    viewModel.initGetDateKey(key == null ? Preference.getValueString(MainActivity.this, LocalData.KEY_KEY).trim() : key.trim(), Tools.getUuidDevice(MainActivity.this).trim(), isRefresh);
                                } else {
                                    PropertiesActivity.pageKey(binding);
                                }
                            } else {
                                if (callbackServer.getState() == CallbackRequest.DOWN.ordinal()) {
                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_server_down);
                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_server_dwon));
                                } else if (callbackServer.getState() == CallbackRequest.MAINTENANCE.ordinal()) {
                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_maintenance);
                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_maintenance));
                                } else if (callbackServer.getState() == CallbackRequest.UPDATE.ordinal()) {
                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_update);
                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_update_app));
                                } else if (callbackServer.getState() == CallbackRequest.EXCEPTION.ordinal()) {
                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_error);
                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_exception));
                                } else if (callbackServer.getState() == CallbackRequest.ERROR.ordinal()) {
                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_error);
                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_error));
                                }
                                PropertiesActivity.pageLog(binding);
                            }

                            //-- get data Key
                            if (viewModel.getDateKey != null)
                                viewModel.getDateKey.observe(MainActivity.this, callbackKey -> {
                                    Log.d("TAG_TEST", "GET DATA KEY :" + callbackKey.toString());
                                    if (callbackKey.getState() == CallbackRequest.SUCCESSFUL.ordinal()) {
                                        binding.MainRootKey.KeyBtnLogin.setVisibility(View.VISIBLE);
                                        binding.MainRootKey.KeyPbLoading.setVisibility(View.INVISIBLE);
                                        viewModel.initGetDateProtection(isRefresh);
                                    } else {
                                        if (callbackKey.getState() == CallbackRequest.USED.ordinal()) {
                                            binding.MainRootKey.KeyTvError.setText(getResources().getString(R.string.Text_key_used));
                                        } else if (callbackKey.getState() == CallbackRequest.EXPIRATION.ordinal()) {
                                            binding.MainRootKey.KeyTvError.setText(getResources().getString(R.string.Text_key_expired));
                                        } else if (callbackKey.getState() == CallbackRequest.EXCEPTION.ordinal()) {
                                            binding.MainRootKey.KeyTvError.setText(getResources().getString(R.string.Text_exception));
                                        } else if (callbackKey.getState() == CallbackRequest.ERROR.ordinal()) {
                                            binding.MainRootKey.KeyTvError.setText(getResources().getString(R.string.Text_key_exist));
                                        } else if (callbackKey.getState() == CallbackRequest.DONE_KEY.ordinal()) {
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                        }
                                        binding.MainRootKey.KeyBtnLogin.setVisibility(View.VISIBLE);
                                        binding.MainRootKey.KeyPbLoading.setVisibility(View.INVISIBLE);
                                        PropertiesActivity.pageKey(binding);
                                    }

                                    //-- get data Protection
                                    if (viewModel.getDateProtection != null)
                                        viewModel.getDateProtection.observe(MainActivity.this, callbackProtection -> {
                                            Log.d("TAG_TEST", "GET DATA PROTECTION :" + callbackProtection.toString());
                                            if (callbackProtection.getState() == CallbackRequest.SUCCESSFUL.ordinal()) {
                                                viewModel.initGetDateDownload(callbackServer.getModelServer().getLinkFiles(), isRefresh);
                                            } else {
                                                if (callbackProtection.getState() == CallbackRequest.EXCEPTION.ordinal()) {
                                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_error);
                                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_exception));
                                                } else if (callbackProtection.getState() == CallbackRequest.ERROR.ordinal()) {
                                                    binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_error);
                                                    binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_error));
                                                }
                                                PropertiesActivity.pageLog(binding);
                                            }

                                            //-- get data Download
                                            if (viewModel.getDateDownload != null)
                                                viewModel.getDateDownload.observe(MainActivity.this, isDownload -> {
                                                    Log.d("TAG_TEST", "GET DATA DOWNLOAD ");
                                                    if (isDownload) {
                                                        PropertiesActivity.pageHome(binding);
                                                        setDate(callbackServer.getModelServer(), callbackKey.getModeKey(), callbackProtection.getModeProtection());
                                                    } else {
                                                        binding.MainRootLog.LogImgStateLog.setImageResource(R.drawable.ic_error);
                                                        binding.MainRootLog.LogTvStateLog.setText(getResources().getString(R.string.Text_error_download));
                                                        PropertiesActivity.pageLog(binding);
                                                    }
                                                });
                                        });
                                });
                        });
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
                Tools.permissionRoot(MainActivity.this);
                binding.MainRootSplash.SplashBtnContinue.setVisibility(View.VISIBLE);
                binding.MainRootSplash.SplashPbLoading.setVisibility(View.GONE);
            }
        }).check();
    }

    private void setDate(ModelServer modelServer, ModelKey modelKey, List<ModelProtection> modelProtection) {
        if (modelServer != null) {
            binding.MainRootSetting.SettingImgGithub.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkGithub()));
            binding.MainRootSetting.SettingImgInstagram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkInstagram()));
            binding.MainRootSetting.SettingImgTelegram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTelegram()));
            binding.MainRootSetting.SettingImgTiktok.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTiktok()));
            binding.MainRootSetting.SettingImgWeb.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkWeb()));

            binding.MainRootLog.LogImgGithub.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkGithub()));
            binding.MainRootLog.LogImgInstagram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkInstagram()));
            binding.MainRootLog.LogImgTelegram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTelegram()));
            binding.MainRootLog.LogImgTiktok.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTiktok()));
            binding.MainRootLog.LogImgWeb.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkWeb()));

            binding.MainRootKey.KeyImgGithub.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkGithub()));
            binding.MainRootKey.KeyImgInstagram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkInstagram()));
            binding.MainRootKey.KeyImgTelegram.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTelegram()));
            binding.MainRootKey.KeyImgTiktok.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkTiktok()));
            binding.MainRootKey.KeyImgWeb.setOnClickListener(v -> Tools.goToURL(MainActivity.this, modelServer.getLinkWeb()));

            binding.MainRootSetting.SettingTvVersion.setText(modelServer.getVersion());
            binding.MainRootSetting.SettingTvNews.setText(Tools.Dec(modelServer.getNews()));
        }

        if (modelKey != null) {
            binding.MainRootSetting.SettingTvInfoDevice.setText(modelKey.getNameDevise());
            binding.MainRootSetting.SettingTvUidDevice.setText(modelKey.getUserUid());
            binding.MainRootSetting.SettingTvKey.setText(modelKey.getKey());
            binding.MainRootSetting.SettingTvDateUse.setText(Tools.millisToDate(modelKey.getDateUse()));
            binding.MainRootSetting.SettingTvDateExpiration.setText(Tools.millisToDate(modelKey.getDateExpiration()));
            binding.MainRootSetting.SettingImgCopyKey.setOnClickListener(v -> Tools.copyText(MainActivity.this, modelKey.getKey()));
            if (modelKey.isPremium())
                binding.MainRootHome.LogImgStateBilling.setImageResource(R.drawable.ic_premium);
            else
                binding.MainRootHome.LogImgStateBilling.setImageResource(R.drawable.ic_free);
            dateExpiration = modelKey.getDateExpiration();
        }

        if (modelProtection != null) {
            adapterProtection.UpdateData(modelProtection);
            binding.MainRootHome.HomeRvProtection.setAdapter(adapterProtection);
            binding.MainRootHome.HomeRvProtection.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            binding.MainRootHome.HomeRvProtection.setHasFixedSize(true);
        }
    }

    //---------------  Check Filed  ---------------//
    private void isInfo() {
        if (!Objects.requireNonNull(binding.MainRootKey.KeyEtKey.getText()).toString().isEmpty()) {
            binding.MainRootKey.KeyBtnLogin.setEnabled(isKey);
        } else {
            binding.MainRootKey.KeyBtnLogin.setEnabled(false);
        }
    }

    private void threadFinished() {
        thread.interrupt();
    }

    private void threadStarted() {
        thread.start();
    }
}