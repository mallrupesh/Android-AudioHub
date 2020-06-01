package com.rupesh.audiohubapp.activities;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.presenter.PlayerPresenter;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private TextView songName;
    private TextView startTimer;
    private TextView totalTimer;
    private ImageButton play;
    private ImageButton next;
    private ImageButton previous;
    private SeekBar seekBar;

    private File file;
    private Project project;

    private PlayerPresenter playerPresenter;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        file = (File) getIntent().getSerializableExtra("file");
        project = (Project) getIntent().getSerializableExtra("project");
        playerPresenter = new PlayerPresenter(this);

        // Setup the tool bar
        mToolbar = findViewById(R.id.player_appBar);
        setupToolBar();

        songName = findViewById(R.id.player_sheet_fileName);
        startTimer = findViewById(R.id.player_sheet_timer);
        totalTimer = findViewById(R.id.player_sheet_timerTotal);
        play = findViewById(R.id.player_sheet_play);
        next = findViewById(R.id.player_sheet_forward);
        previous = findViewById(R.id.player_sheet_backward);
        seekBar = findViewById(R.id.player_sheet_seek_bar);

       // Log.d("File_", file.getFileId());
       // Log.d("Project_", project.getProjectName());


        songName.setText(file.getName());
        playerPresenter.playPauseFile();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
    }

    public void initPlayer(String url){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException IOe) {
            IOe.printStackTrace();
        }
        String totalTime = createTimeStamp(mediaPlayer.getDuration());
        totalTimer.setText(totalTime);
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        play.setImageResource(R.drawable.ic_pause);

        updateSeekBar();
    }

    public void updateSeekBar() {
       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser) {
                   mediaPlayer.seekTo(progress);
                   seekBar.setProgress(progress);
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });

       new Thread(new Runnable() {
           @Override
           public void run() {
                while (mediaPlayer != null) {
                    try {
                        if(mediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(100);
                        }
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
           }
       }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentPosition = msg.what;
            seekBar.setProgress(currentPosition);
            String currentTime = createTimeStamp(currentPosition);
            startTimer.setText(currentTime);
        }
    };

    private void play() {
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            play.setImageResource(R.drawable.ic_pause);
        } else {
            pause();
        }
    }

    private void pause() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            play.setImageResource(R.drawable.ic_play);
        }
    }

    public String createTimeStamp(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timeLabel += min + ":";
        if(sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public void setupToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Audio Player");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public File getFile() {
        return file;
    }

    public Project getProject() {
        return project;
    }
}

/*    public void playPause(String url) {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                AsyncTask<String, String, String> player = new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        try {
                            mediaPlayer.setDataSource(strings[0]);
                            mediaPlayer.prepare();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return  "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        audioFileLength = mediaPlayer.getDuration();
                        realTimeLength = audioFileLength;
                        if(!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                            play.setImageResource(R.drawable.ic_pause);
                        } else {
                            mediaPlayer.pause();
                            play.setImageResource(R.drawable.ic_play);
                        }

                        updateSeekBar();
                    }
                };

                player.execute(url);
            }
        });
    }*/

