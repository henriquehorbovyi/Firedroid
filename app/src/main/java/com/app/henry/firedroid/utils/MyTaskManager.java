package com.app.henry.firedroid.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.app.henry.firedroid.utils.interfaces.HandleTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by henry on 14/04/17.
 */
public class MyTaskManager extends AsyncTask<String,String,Bitmap> {

    private Context context;
    private HandleTask handleTask;
    private ProgressDialog progressDialog;

    public MyTaskManager(Context context,HandleTask handleTask){
        this.context    = context;
        this.handleTask = handleTask;
    }

    @Override protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading images...");
        progressDialog.show();
    }

    @Override protected Bitmap doInBackground(String... params) {
        Bitmap image = null;
        try{
            URL imageUrl = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            InputStream stream = httpURLConnection.getInputStream();
            image = BitmapFactory.decodeStream(stream);
        }catch (Exception e){

        }

        return image;
    }

    @Override protected void onProgressUpdate(String... values) {
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progressDialog.setMessage("DONE!");
        handleTask.afterDownload(bitmap);
        progressDialog.dismiss();
    }
}
