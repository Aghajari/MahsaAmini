package com.mahsaamini.iran;

import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MainActivity extends AppCompatActivity {

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StyledPlayerView playerView = findViewById(R.id.video);
        player = new ExoPlayer.Builder(this).build();
        player.setMediaItem(MediaItem.fromUri(Uri.parse("file:///android_asset/opiran.mp4")));
        playerView.setPlayer(player);
        player.prepare();

        IranView iran = findViewById(R.id.iran);
        TextView tv = findViewById(R.id.tv);

        playerView.getLayoutParams().width = iran.getSize();
        playerView.getLayoutParams().height = iran.getSize();
        ((ViewGroup.MarginLayoutParams) tv.getLayoutParams()).topMargin = iran.getSize() / 2;

        playerView.requestLayout();

        iran.setOnClickListener(v -> {
            iran.setOnClickListener(null);

            iran.start(() -> {
                playerView.post(player::play);

                // DO NOT USE OBJECT ANIMATOR (REFLECTION) HERE
                ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
                View shadow = findViewById(R.id.shadow);
                animator.addUpdateListener(a -> shadow.setAlpha((float) a.getAnimatedValue()));
                animator.setDuration(1000);
                animator.start();

                String text = "#MahsaAmini";
                ValueAnimator textAnimator = ValueAnimator.ofInt(0, text.length());
                textAnimator.setDuration(text.length() * 220);
                textAnimator.addUpdateListener(a -> tv.setText(text.substring(0, (int) a.getAnimatedValue())));
                textAnimator.start();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}