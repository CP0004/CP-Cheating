package com.cp.jo.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.listener.CallbackReadFilesUrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class ReadFilesUrl extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final String urlFiles;
    private final String pathFiles;
    private final String nameFiles;
    private final CallbackReadFilesUrl callbackReadFilesUrl;
    private boolean isLink = true;
    private StringBuilder text;

    public ReadFilesUrl(Context context, String urlFiles, String pathFiles, String nameFiles, CallbackReadFilesUrl callbackReadFilesUrl) {
        this.context = context;
        this.urlFiles = urlFiles;
        this.pathFiles = pathFiles;
        this.nameFiles = nameFiles;
        this.callbackReadFilesUrl = callbackReadFilesUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        getData(urlFiles, pathFiles, nameFiles);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //-- Callback  SUCCESSFUL or ERROR (INVALID Link)
        if (isLink)
            callbackReadFilesUrl.onCallbackReadFilesUrl(CallbackRequest.SUCCESSFUL.ordinal(), null, null);
        else
            callbackReadFilesUrl.onCallbackReadFilesUrl(CallbackRequest.INVALID.ordinal(), null, null);
    }

    //-- Get Data Files Url Online
    private void getData(String urls, String path, String name) throws RuntimeException {

        text = new StringBuilder();
        HttpURLConnection connection;
        InputStream inputStream;
        OutputStream outputStream;

        try {

            URL url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            //-- Check Url OK or Not
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                isLink = false;
                return;
            }

            inputStream = connection.getInputStream();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                outputStream = Files.newOutputStream(new File(path, name).toPath());
            else
                outputStream = new FileOutputStream(new File(path, name));


            byte[] data = new byte[4096];
            int total = 0;
            int count = 0;
            //Read and Write Data
            while ((count = inputStream.read(data)) != -1) {
                total += count;

                text.append(data);
                outputStream.write(data, 0, count);
            }

            inputStream.close();
            outputStream.close();

            String commandMove = "cp -r " + LocalData.STATIC_LOCAL_PATH_DOWNLOAD + name + " " + LocalData.STATIC_LOCAL_PATH_EXECUTE + name;
            String commandChmod = "chmod 777 " + LocalData.STATIC_LOCAL_PATH_EXECUTE + name;
            Tools.commandShell(context, commandMove);
            Tools.commandShell(context, commandChmod);

            String commandRmMove = "rm " + LocalData.STATIC_LOCAL_PATH_DOWNLOAD + name;
            Tools.commandShell(context, commandRmMove);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
