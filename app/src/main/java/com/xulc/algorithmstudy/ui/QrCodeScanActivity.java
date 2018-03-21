package com.xulc.algorithmstudy.ui;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xulc.algorithmstudy.R;
import com.xulc.myzxing.zxing.IActivityZxing;
import com.xulc.myzxing.zxing.camera.CameraManager;
import com.xulc.myzxing.zxing.decoding.CaptureActivityHandler;
import com.xulc.myzxing.zxing.decoding.InactivityTimer;
import com.xulc.myzxing.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;


/**
 * Date：2018/3/21
 * Desc：二维码扫描
 * Created by xuliangchun.
 */

public class QrCodeScanActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener, SurfaceHolder.Callback, IActivityZxing {
    private long VIBRATE_DURATION = 200L;

    private CaptureActivityHandler handler;

    private Boolean hasSurface = false;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet = null;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private Boolean playBeep;
    private float BEEP_VOLUME = 0.30f;
    private Boolean vibrate;
    private SensorManager sensorManager;
    private SurfaceView surfaceView;
    private ViewfinderView viewFinderView;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //当前光照强度
            if (event.values[0]<100){
                viewFinderView.setSensationDarkLight(true);
            }else{
                viewFinderView.setSensationDarkLight(false);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);
        viewFinderView = (ViewfinderView) findViewById(R.id.viewFinderView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        CameraManager.init(getApplicationContext());
        inactivityTimer = new InactivityTimer(this);

        //第一步：获取 SensorManager 的实例
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //第二步：获取 Sensor 传感器类型
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //第四步：注册 SensorEventListener
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        //分为6.0以上权限检查 6.0以下直接按照权限通过
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestRuntimePermission(new String[]{Manifest.permission.CAMERA},this);
        }else{
            onGranted();
        }
    }

    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler!=null){
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        //传感器使用完毕，释放资源
        if(sensorManager!=null){
            sensorManager.unregisterListener(listener);
        }
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e) {
            return;
        } catch (RuntimeException e) {
            return;
        }

        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }

        }
    }
    private MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mediaPlayer.seekTo(0);
        }
    };

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void handleDecode(Result result, Bitmap bitmap) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return viewFinderView;
    }

    @Override
    public void drawViewfinder() {
        viewFinderView.drawViewfinder();
    }


}
