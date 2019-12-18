package com.sunlands.www.ijktester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private IjkMediaPlayer mPlayer;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        mUrl =  getIntent().getExtras().getString("url");

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(callback);
    }

    private void createPlayer() {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    Log.v("onPrepared", "");
                }
            });

            mPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer mp) {
                    Log.v("onCompletion", "");
                }
            });

            mPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer mp, int what, int extra) {
                    Log.e("onError", "" + what + ", " + extra);
                    return true;
                }
            });

            mPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                    Log.v("onInfo", "" + what + ", " + extra);
                    return true;
                }
            });

            try {
                // mPlayer.setDataSource("http://1257236654.vod2.myqcloud.com/cc26eeabvodcq1257236654/d72b2edd5285890796669277602/f0.mp4?t=5df316d9&us=711c9327&sign=53473b47371eb03a85295d6bc6466a15");
                mPlayer.setDataSource(mUrl);
                Log.v("mUrl", mUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mPlayer.prepareAsync();
        }
    }

    private void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

        IjkMediaPlayer.native_profileEnd();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            createPlayer();
            mPlayer.setDisplay(surfaceView.getHolder());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (surfaceView != null) {
                surfaceView.getHolder().removeCallback(callback);
                surfaceView = null;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }
}
