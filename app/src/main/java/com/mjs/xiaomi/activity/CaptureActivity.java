package com.mjs.xiaomi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mjs.xiaomi.R;
import com.mjs.xiaomi.bean.Book;
import com.mjs.xiaomi.moudle.book.GetBookAction;
import com.mjs.xiaomi.moudle.book.GetBookTask;
import com.mjs.xiaomi.net.BaseRequest;
import com.mjs.xiaomi.net.HttpCallback;
import com.mjs.xiaomi.zxing.camera.CameraManager;
import com.mjs.xiaomi.zxing.decoding.CaptureActivityHandler;
import com.mjs.xiaomi.zxing.decoding.InactivityTimer;
import com.mjs.xiaomi.zxing.view.ViewfinderView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Vector;

/**
 * Created by dafei on 2017/9/8.
 */
public class CaptureActivity extends Activity implements SurfaceHolder.Callback {
    ViewfinderView viewfinderView;
    private boolean hasSurface;
    InactivityTimer inactivityTimer;
    private CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean playBeep;
    private MediaPlayer mediaPlayer;
    private boolean vibrate;
    private static final float BEEP_VOLUME = 0.10f;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    private ImageView iv_close, iv_light;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_capture);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_content);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_light = (ImageView) findViewById(R.id.iv_light);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
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
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
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
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            GetBookTask task = new GetBookTask("1220562");
            task.enqueue(new HttpCallback() {

                @Override
                public void onResponse(BaseRequest request, Object data) {
                    Book book = (Book) data;
                    if (book != null) {
                        QMUIBottomSheet sheet = new QMUIBottomSheet(CaptureActivity.this);
                        View view = LayoutInflater.from(CaptureActivity.this).inflate(R.layout.dialog_book, null, false);
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_img);
                        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                        TextView tv_auth = (TextView) view.findViewById(R.id.tv_auth);
                        tv_title.setText(book.getTitle());
                        tv_auth.setText(book.getAuthor().get(0));
                        sheet.setContentView(view);
                        sheet.show();
                    } else {
                        QMUIBottomSheet sheet = new QMUIBottomSheet(CaptureActivity.this);
                        sheet.setContentView(R.layout.dialog_text);
                        sheet.show();
                    }
                }

                @Override
                public void onFailure(BaseRequest request, Exception e, int code, String message) {

                }
            });
//            GetBookAction.getBook(resultString);
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString(INTENT_EXTRA_KEY_QR_SCAN, resultString);
//            System.out.println("sssssssssssssssss scan 0 = " + resultString);
//            // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
////            bundle.putParcelable("bitmap", barcode);
////            Logger.d("saomiao",resultString);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
        }
//        CaptureActivity.this.finish();
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
}
