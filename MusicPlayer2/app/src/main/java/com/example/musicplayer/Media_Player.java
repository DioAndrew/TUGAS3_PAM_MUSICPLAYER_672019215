package com.example.musicplayer;

import android.media.MediaPlayer;

public class Media_Player{

    static MediaPlayer instance;

    public  static MediaPlayer getInstance(){
        if (instance == null){
            instance = new MediaPlayer();
        }

        return instance;
    }

    public static int index = -1;

}
