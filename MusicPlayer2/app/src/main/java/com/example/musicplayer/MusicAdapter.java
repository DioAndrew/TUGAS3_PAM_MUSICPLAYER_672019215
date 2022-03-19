package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {


    ArrayList<AudioModel> songs;
    Context context;


    public MusicAdapter(ArrayList<AudioModel> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MusicAdapter.ViewHolder holder, int position) {
        AudioModel songData = songs.get(position);
        holder.judul_lagu.setText(songData.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Media_Player.getInstance().reset();
                Media_Player.index = position;
                Intent intent = new Intent(context,MusicPlayer.class);
                intent.putExtra("songs", songs);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView judul_lagu;
        ImageView logo;

        public  ViewHolder(View itemView){
            super(itemView);

            judul_lagu = itemView.findViewById(R.id.Judul_Lagu);
            logo = itemView.findViewById(R.id.logo);

        }

    }

}
