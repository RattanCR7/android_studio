package com.example.basicprasesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    MediaPlayer mediaPlayer;
    public void translate(View view){
        Button buttonpressed = (Button) view;
        String tag = buttonpressed.getTag().toString();

        MediaPlayer mediaPlayer=MediaPlayer.create(this,getResources().getIdentifier(tag,"raw",getPackageName()));

        mediaPlayer.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
