package timer.fityfor.me.hkvideoplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timer.fityfor.me.hkvideoplayer.adapters.VideoAdapter;
import timer.fityfor.me.hkvideoplayer.application.MyApplication;
import timer.fityfor.me.hkvideoplayer.controllers.DataController;
import timer.fityfor.me.hkvideoplayer.controllers.NetworkController;
import timer.fityfor.me.hkvideoplayer.controllers.ViewController;
import timer.fityfor.me.hkvideoplayer.download.DownloadVideo;
import timer.fityfor.me.hkvideoplayer.entities.MyList;
import timer.fityfor.me.hkvideoplayer.entities.MyVideo;
import timer.fityfor.me.hkvideoplayer.interfaces.OnNetworkCallListener;
import timer.fityfor.me.hkvideoplayer.interfaces.RecyclerViewOnItemClicked;

import static timer.fityfor.me.hkvideoplayer.api.HttpClient.BASE_URL;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnItemClicked, MediaPlayer.OnCompletionListener, View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_video_layout)
    LinearLayout llVideoLayout;
    @BindView(R.id.rl_activity_main)
    RelativeLayout rlActivityMain;

    private VideoAdapter adapter;
    private boolean isVideoVisible = false;
    private int nextTrack;
    private int stopPosition;
    private MyList<MyVideo> videos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkPer();
        getVideos();
        initAdaoter();
        initListeners();
        initMediaController();
    }

    private void checkPer(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //File write logic here

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
        }else{
            finish();
        }
    }

    private void initListeners() {
        videoView.setOnCompletionListener(this);

        llVideoLayout.setOnClickListener(this);
    }

    private void showVolumeControl() {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewController.getViewController().setContext(this);
        ViewController.getViewController().setMainActivity(this);
        if (isVideoVisible) {
            videoView.seekTo(stopPosition);
            videoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition();
        videoView.pause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void getVideos() {
        NetworkController.getVideos(new OnNetworkCallListener() {
            @Override
            public void onSuccess() {
                setAdapter();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void initMediaController() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    private void initAdaoter() {
        adapter = new VideoAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {
        adapter.setData();
    }

    private void playVideo(int position) {
        new DownloadVideo().execute(DataController.getInstance().getVideosData().get(position).getVideoUrl());

        videos = DataController.getInstance().getVideosData();
        HttpProxyCacheServer proxy = MyApplication.getProxy(this);
        String proxyUrl = proxy.getProxyUrl(BASE_URL + videos.get(position).getVideoUrl());
        videoView.setVideoPath(proxyUrl);
        videoView.start();
        nextTrack = position;


    }

    @Override
    public void onItemClicked(View view, int position) {
        showVideo(position);
    }

    private void showVideo(int position) {
        isVideoVisible = true;
        llVideoLayout.setVisibility(View.VISIBLE);
        playVideo(position);
        ViewController.getViewController().toFullScreen(true, rlActivityMain);
    }

    private void closeVideo() {
        isVideoVisible = false;
        videoView.stopPlayback();
        ViewController.getViewController().toFullScreen(false, rlActivityMain);
        llVideoLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isVideoVisible) {
            closeVideo();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (videos != null) {
            nextTrack++;
            if (videos.size() - 1 >= nextTrack) {
                playVideo(nextTrack);
            } else {
                closeVideo();
                getVideos();
            }
        }
    }

    @Override
    public void onClick(View view) {
        showVolumeControl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView = null;
        }
    }
}
