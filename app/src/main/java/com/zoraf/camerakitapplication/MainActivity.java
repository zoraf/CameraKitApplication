package com.zoraf.camerakitapplication;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private CameraKitView mCameraKitView;
    private Button mCaptureImageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraKitView = findViewById(R.id.camera);
        initCameraKitView();
        mCaptureImageButton = findViewById(R.id.btnCapture);
        mCaptureImageButton.setOnClickListener(this);
    }

    private void initCameraKitView() {
        mCameraKitView.setFacing(CameraKit.FACING_BACK);
        mCameraKitView.setFocus(CameraKit.FOCUS_AUTO);
        mCameraKitView.setZoomFactor(1.0f);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCameraKitView.onStart();
    }

    @Override
    protected void onPause() {
        mCameraKitView.onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraKitView.onResume();
    }

    @Override
    protected void onStop() {
        mCameraKitView.onStop();
        super.onStop();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mCameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCapture:
                mCameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] capturedImage) {
                        Log.d(TAG, "onImage: " + Environment.getExternalStorageDirectory());
                        File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                        try {
                            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                            outputStream.write(capturedImage);
                            outputStream.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}

//public class MainActivity extends AppCompatActivity {
//    public static final String BROADCAST_PC_TAKEN_ACTION = "com.unipos.tc.picktiretaken";
//
//    private CameraKitView cameraKitView;
//    private CameraView cameraView;
//    private Button captureBtn;
//    public static Bitmap cubitmap;
//    public static String unix_time_stamp_name;
//    public static String subPath;
//    public static String fileName;
//
//    public static String STATIC_ID, BOL_S, arrive_s;
//    public static int STATIC_NEWCODATARECID;
//    public static String Type;
//    public String root;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        cameraView = (CameraView) findViewById(R.id.cameraView);
//        captureBtn = (Button) findViewById(R.id.captureBtn);
//
//        cameraView.setJpegQuality(50);
//        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP);
//        cameraView.setFlash(CameraKit.Constants.FLASH_AUTO);
//        cameraView.setMethod(CameraKit.Constants.METHOD_STILL);
//
//        cameraView.addCameraKitListener(new CameraKitEventListener() {
//            @Override
//            public void onEvent(CameraKitEvent cameraKitEvent) {
//
//            }
//
//            @Override
//            public void onError(CameraKitError cameraKitError) {
//
//            }
//
//            @Override
//            public void onImage(CameraKitImage cameraKitImage) {
//
//                Bitmap bmap = cameraKitImage.getBitmap();
//
//                cubitmap = bmap;
//                saveImage(bmap);
//            }
//
//            @Override
//            public void onVideo(CameraKitVideo cameraKitVideo) {
//
//            }
//        });
//
//        captureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                cameraView.captureImage();
//            }
//        });
//
//
//    }
//
//
//    private BroadcastReceiver imgConfBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            saveImage(cubitmap);
//        }
//    };
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        cameraView.start();
//    }
//
//    @Override
//    protected void onPause() {
//        cameraView.stop();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//
//        try {
//            unregisterReceiver(imgConfBroadcastReceiver);
//        } catch (IllegalArgumentException e) {
//
//        }
//
//        super.onDestroy();
//    }
//
//    public void saveImage(Bitmap bitmap) {
//        unix_time_stamp_name = "";
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//        String path = root + "/NTASKER/";
//        FileOutputStream outFile = null;
//
//        File file = new File(path, unix_time_stamp_name);
//
//        try {
//
//            outFile = new FileOutputStream(file);
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
//
//            outFile.flush();
//
//            outFile.close();
//
//            fileName = file.getPath();
//
//            if(CameraKitActivity.Type.equals("EditTask")) {
//                Intent intent = new Intent(BROADCAST_PC_TAKEN_ACTION);
//                sendBroadcast(intent);
//            }
//
//            try {
//
//            } catch (Exception e) {
//                saveImagetoDatabse();
//            }
//
//        }
//        catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        this.finish();
//    }
//}