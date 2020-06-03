package com.rupesh.audiohubapp.activities;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.PlayerListAdapter;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.presenter.PlayerPresenter;

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
    private EditText comment;
    private Button postComment;

    private File file;
    private Project project;

    private PlayerPresenter playerPresenter;

    private MediaPlayer mediaPlayer;

    private RecyclerView recyclerView;
    private PlayerListAdapter playerListAdapter;


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
        comment = findViewById(R.id.player_activity_comment);
        postComment = findViewById(R.id.player_activity_post);


        initUI();
        songName.setText(file.getName());
        playerPresenter.playPauseFile();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.inputComment(comment.getText().toString());
                comment.getText().clear();
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
        String timeStamp = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timeStamp += min + ":";
        if(sec < 10) timeStamp += "0";
        timeStamp += sec;

        return timeStamp;
    }


    public void initUI() {
        recyclerView = findViewById(R.id.recycleListComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerListAdapter = new PlayerListAdapter(playerPresenter.queryData());
        recyclerView.setAdapter(playerListAdapter);
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

    @Override
    public void onStart() {
        super.onStart();
        playerListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        playerListAdapter.stopListening();
    }
}

