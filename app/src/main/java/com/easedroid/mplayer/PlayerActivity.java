package com.easedroid.mplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.easedroid.mplayer.widgets.StrongPlayerView;

public class PlayerActivity extends Activity {

    private static final String TAG = "PlayerActivity";
    private FrameLayout containerView;
    private StrongPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        containerView = (FrameLayout) findViewById(R.id.id_surface_container);
        playerView = new StrongPlayerView();
        playerView.initView(this, containerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayerVideo(playerView);
    }

    private void startPlayerVideo(final StrongPlayerView player) {
        String playUrl = getPlayUrl();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("bin.jing", "mp --->" + mp.hashCode());
            }
        });
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
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
        player.setPlayList(new String[]{
                "/storage/sdcard/vod/demo.mp4",
                "/storage/sdcard/vod/demo_1.mp4",
                "/storage/sdcard/vod/demo_2.mp4"
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.stop();
        }
    }

    private String getPlayUrl() {
        return "/sdcard/vod/demo.mp4";
    }
}
