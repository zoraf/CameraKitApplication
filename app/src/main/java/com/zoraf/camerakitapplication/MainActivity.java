package com.zoraf.camerakitapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String BROADCAST_PC_TAKEN_ACTION = "com.unipos.tc.picktiretaken";

    private CameraView cameraView;
    private Button captureBtn;
    public static Bitmap cubitmap;
    public static String unix_time_stamp_name;
    public static String subPath;
    public static String fileName;

    public static String STATIC_ID, BOL_S, arrive_s;
    public static int STATIC_NEWCODATARECID;
    public static String Type;
    public String root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (CameraView) findViewById(R.id.cameraView);
        captureBtn = (Button) findViewById(R.id.captureBtn);

        cameraView.setJpegQuality(50);
        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP);
        cameraView.setFlash(CameraKit.Constants.FLASH_AUTO);
        cameraView.setMethod(CameraKit.Constants.METHOD_STILL);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bmap = cameraKitImage.getBitmap();

                cubitmap = bmap;
                saveImage(bmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraView.captureImage();
            }
        });


    }


    private BroadcastReceiver imgConfBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            saveImage(cubitmap);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        try {
            unregisterReceiver(imgConfBroadcastReceiver);
        } catch (IllegalArgumentException e) {

        }

        super.onDestroy();
    }

    public void saveImage(Bitmap bitmap) {
        unix_time_stamp_name = "";
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


        String path = root + "/NTASKER/";
        FileOutputStream outFile = null;

        File file = new File(path, unix_time_stamp_name);

        try {

            outFile = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);

            outFile.flush();

            outFile.close();

            fileName = file.getPath();

            if(CameraKitActivity.Type.equals("EditTask")) {
                Intent intent = new Intent(BROADCAST_PC_TAKEN_ACTION);
                sendBroadcast(intent);
            }

            try {

            } catch (Exception e) {
                saveImagetoDatabse();
            }

        }
        catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.finish();
    }
}