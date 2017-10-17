package com.easedroid.mplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = "PlayerActivity";
    private SurfaceView surfaceView;
    private MediaPlayer nextPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        surfaceView = (SurfaceView) findViewById(R.id.id_surface_view);
        initSurface(surfaceView);
    }

    private void initSurface(SurfaceView surfaceView) {
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surface created");
        startPlayerVideo();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surface changed " + i1 + " x " + i2);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surface destroyed");

    }

    private void startPlayerVideo() {
        final MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceView.getHolder());
        String playUrl = getPlayUrl();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    Log.d(TAG, "onCompletion start reset");
                    player.reset();
                    Log.d(TAG, "onCompletion end reset");
                    player.setDataSource(getPlayUrl());
                    player.prepare();
                    player.start();
                    Log.d(TAG, "onCompletion start video");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        player.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    Log.d(TAG, "start video rendering");
                }
                return false;
            }
        });
        if (playUrl == null) return;
        try {
            player.setDataSource(this, Uri.parse(playUrl));
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayUrl() {
        return "/sdcard/vod/demo.mp4";
    }


}
