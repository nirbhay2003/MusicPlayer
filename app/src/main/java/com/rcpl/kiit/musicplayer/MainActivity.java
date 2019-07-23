package com.rcpl.kiit.musicplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lis);

        Toolbar toolbar=findViewById(R.id.layout);
        setSupportActionBar(toolbar);

        lv=findViewById(R.id.li);
        final ArrayList<File> mySong=findSongs(Environment.getExternalStorageDirectory());
        items=new String[mySong.size()];
        for (int i=0;i<mySong.size();i++)
        {
            items[i]=mySong.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }

        ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_main,R.id.tx,items);

        lv.setAdapter(adp);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //
                String songName= (String) lv.getItemAtPosition(position).toString();

                Intent obj=new Intent(getApplicationContext(),Player.class);
                obj.putExtra("pos",position);
                obj.putExtra("songlist",mySong);
                //
               // obj.putExtra("songname",songName);

                startActivity(obj);
            }

        });

    }
    public ArrayList<File> findSongs(File root)
    {

        ArrayList<File> a1=new ArrayList<>();
        File[] files=root.listFiles();
        for (File singleFile:files)
        {
            if (singleFile.isDirectory()&&!singleFile.isHidden())
            {
                a1.addAll(findSongs(singleFile));

            }

            else
            {
                if (singleFile.getName().endsWith(".mp3"))
                {
                    a1.add(singleFile);
                }
            }
        }
            return a1;
    }




}
