package com.app.henry.firedroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.henry.firedroid.utils.MyTaskManager;
import com.app.henry.firedroid.utils.interfaces.HandleTask;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, HandleTask{

    private FirebaseAuth firebaseAuth;
    private TextView tvWelcome;

    private Button btnDownloadImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth        = FirebaseAuth.getInstance();
        tvWelcome           = (TextView) findViewById(R.id.tvwelcome);
        btnDownloadImage    = (Button) findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(this);

        if(firebaseAuth.getCurrentUser() != null){
            String userName = firebaseAuth.getCurrentUser().getEmail().split("@")[0];
            getSupportActionBar().setTitle(userName);
            tvWelcome.setText("Welcome "+userName+"!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setTitle("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDownloadImage:
                downloadImage();
                break;
        }
    }

    private void downloadImage(){
        MyTaskManager taskManager = new MyTaskManager(this,this);
        taskManager.execute("https://lh3.googleusercontent.com/-whXBCDVxIto/Vz2Rsyz-UjI/AAAAAAAAiJc/UjvR-M2b9tY5SyKFkDY6Q_MbusEINRXkQ/w1024-h1024/Firebase_16-logo.png");
    }

    @Override
    public void afterDownload(Bitmap b) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(b);
    }
}
