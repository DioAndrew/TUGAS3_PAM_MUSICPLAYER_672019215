package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayer extends AppCompatActivity {

    TextView txt_judul, txt_durasi, txt_total_durasi;
    ImageButton btn_mundur, btn_maju, btn_mulai;
    SeekBar seekBar;
    ArrayList<AudioModel> songs;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = Media_Player.getInstance();
    Handler sycHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        txt_judul = findViewById(R.id.judul);
        txt_durasi = findViewById(R.id.durasi_berjalan);
        txt_total_durasi = findViewById(R.id.total_durasi);
        btn_mundur = findViewById(R.id.btn_mundur);
        btn_maju = findViewById(R.id.btn_maju);
        btn_mulai = findViewById(R.id.btn_mulai);
        seekBar = findViewById(R.id.seekbar_control);

        txt_judul.setSelected(true);

        songs = (ArrayList<AudioModel>) getIntent().getSerializableExtra("songs");

        setResourcesMusic();

        MusicPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    txt_durasi.setText(covertDuration(mediaPlayer.getCurrentPosition()+""));
                }
               sycHandler.postDelayed(this,100);
            }

        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }


    void setResourcesMusic(){
        currentSong = songs.get(Media_Player.index);

        txt_judul.setText(currentSong.getTitle());

        txt_total_durasi.setText(covertDuration(currentSong.getDuration()));

        btn_mulai.setOnClickListener(view -> PlayPause());
        btn_maju.setOnClickListener(view -> next());
        btn_mundur.setOnClickListener(view -> previous());

        play();

    }


    private void play(){
        mediaPlayer.reset();
        try {
            btn_mulai.setImageResource(R.drawable.ic_pause);
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());

        }
        catch (Exception ex){

            ex.printStackTrace();
        }
    }

    private void PlayPause(){
        if (mediaPlayer.isPlaying()){
            btn_mulai.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
        }
        else {
            btn_mulai.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }

    }

    private void next(){

        if (Media_Player.index == songs.size()-1){
            return;
        }
        Media_Player.index += 1;
        mediaPlayer.reset();
        setResourcesMusic();
    }


    private void previous(){
        if (Media_Player.index == 0){
            return;
        }
        Media_Player.index -= 1;
        mediaPlayer.reset();
        setResourcesMusic();
    }


    public static String covertDuration(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}