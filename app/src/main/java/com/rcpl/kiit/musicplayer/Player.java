package com.rcpl.kiit.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener{

    Button btplay,btff,btfb,btnxt,btpv;
    TextView textlabel;
    SeekBar sb;
    ArrayList<File> mySongs;
    static MediaPlayer mp;
    int position;
    Uri u;
    Thread updateSeekBar;
    String sname;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btplay=findViewById(R.id.button2);
        btff=findViewById(R.id.button3);
        btfb=findViewById(R.id.button);
        btnxt=findViewById(R.id.button5);
        btpv=findViewById(R.id.button4);
        sb=findViewById(R.id.seekBar);
        textlabel=findViewById(R.id.tv);

        Toolbar toolbar=findViewById(R.id.layout);
        setSupportActionBar(toolbar);

        updateSeekBar=new Thread()
        {
            @Override
            public void run() {

                int totalDuration=mp.getDuration();
                int currentPosition=0;
                sb.setMax(totalDuration);
                while (currentPosition<totalDuration)
                {
                    try {
                        sleep(500);
                        currentPosition=mp.getCurrentPosition();
                        sb.setProgress(currentPosition);

                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        };


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mp.seekTo(seekBar.getProgress());
            }
        });

        //updateSeekBar.start();

        btplay.setOnClickListener(this);
        btff.setOnClickListener(this);
        btfb.setOnClickListener(this);
        btpv.setOnClickListener(this);
        btnxt.setOnClickListener(this);

        if (mp!=null)
        {
            mp.stop();
            mp.release();
        }

        Intent i=getIntent();
        Bundle b=i.getExtras();
        mySongs=(ArrayList)b.getParcelableArrayList("songlist");

        //

        sname=mySongs.get(position).getName().toString();
       // String sName=i.getStringExtra("songname");

       textlabel.setText(sname);
        textlabel.setSelected(true);

        position=b.getInt("pos",0);


        u=Uri.parse(mySongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        updateSeekBar.start();


    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        switch (id)
        {
            case R.id.button2:
                if (mp.isPlaying())
                {
                    //btplay.setBackground(R.drawable.play_icon);
                    mp.pause();
                }


                else {
                    //btplay.setBackground(R.drawable.pause_icon);
                    mp.start();
                    break;
                }

            case R.id.button3:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;

            case R.id.button:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;

            case R.id.button5:
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                u=Uri.parse(mySongs.get(position).toString());
                sname=mySongs.get(position).getName().toString();
                textlabel.setText(sname);
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
            case R.id.button4:
                mp.stop();
                mp.release();
                position=(position-1<0)?mySongs.size()-1:position-1;
                u=Uri.parse(mySongs.get(position).toString());
                sname=mySongs.get(position).getName().toString();
                textlabel.setText(sname);
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
        }

    }
}
