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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraKitView = findViewById(R.id.camera);
//        initCameraKitView();
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
        Log.d(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.btnCapture:
                Log.d(TAG, "onClick: ");
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