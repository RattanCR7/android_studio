package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    SeekBar seekBar;
    TextView textView;
    boolean counterActive=false;
    Button button;

    public void reset(){
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        button.setText("Start");
        counterActive=false;
        countDownTimer.cancel();
    }
    public void start(View view){
        if(counterActive){
            reset();
        }
        else {
            counterActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                public void onTick(long l) {
                    update((int) l / 1000);

                }
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    textView.setText("0:00");
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        }
    }
    public void update(int secleft){
        int min=secleft/60;
        int sec=secleft - (min*60);
        String secondString=Integer.toString(sec);
        if(sec<=9) {
            secondString = "0"+Integer.toString(sec);
        }
        textView.setText(Integer.toString(min) +":"+ secondString);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =findViewById(R.id.startbutton);
        seekBar=(SeekBar)findViewById(R.id.changetimer);
        textView =(TextView) findViewById(R.id.countTextView);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
