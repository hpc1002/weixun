package com.zhijin.drawerapp.liveVideo;

import android.content.Context;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.utils.SoftInputUtils;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.base.BaseActivity;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoLivePlayActivity extends BaseActivity {


    @BindView(R.id.player_view)
    IjkPlayerView playerView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private boolean mIsFocus;
    private String mVideoPath;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_video_live_play);
    }

    @Override
    protected void setListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerView.sendDanmaku(etContent.getText().toString(), false);
                etContent.setText("");
                _closeSoftInput();
            }
        });
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    playerView.editVideo();
                }
                mIsFocus = isFocus;
            }
        });
    }

    private void _closeSoftInput() {
        etContent.clearFocus();
        SoftInputUtils.closeSoftInput(this);
        playerView.recoverFromEditVideo();
    }

    @Override
    protected void processLogic() {
        mVideoPath = getIntent().getStringExtra("stream_addr");
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        if (mVideoPath != null) {
//            playerView.setVideoPath(mVideoPath);
            playerView.init()
                    .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                    .setSkipTip(1000 * 60 * 1)
                    .enableDanmaku()
                    .setVideoSource(null, mVideoPath, mVideoPath, null, null)
                    .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
        }
        playerView.start();
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (_isHideSoftInput(view, (int) ev.getX(), (int) ev.getY())) {
            _closeSoftInput();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean _isHideSoftInput(View view, int x, int y) {
        if (view == null || !(view instanceof EditText) || !mIsFocus) {
            return false;
        }
        return x < llLayout.getLeft() ||
                x > llLayout.getRight() ||
                y < llLayout.getTop();
    }
}
