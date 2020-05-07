package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView resulttext;
    TextView scoretext;
    ArrayList<Integer> options=new ArrayList<>();
    int locationOfCorrect;
    int score=0;
    int numberOfQuestions=0;
    TextView counter;
    Button playagain;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView questiontext;

    public void playagain(View view){
        score=0;
        numberOfQuestions=0;
        scoretext.setText(Integer.toString(score)+"-"+Integer.toString(numberOfQuestions));
        counter.setText("30s");
        newQuestion();
        playagain.setVisibility(View.INVISIBLE);
        CountDownTimer countDownTimer = new CountDownTimer(30100,1000){
            @Override
            public void onTick(long l) {
                updateTimer((int) l/1000);
            }
            @Override
            public void onFinish(){
                resulttext.setText("Done");
                playagain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void result(View view){

        if(Integer.toString(locationOfCorrect).equals(view.getTag().toString())){
            score++;
            resulttext.setText("Correct !");
        }
        else
            resulttext.setText("Wrong :(");
        numberOfQuestions++;
        scoretext.setText(Integer.toString(score)+"-"+Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void updateTimer(int secondsLeft){
        String sec=Integer.toString(secondsLeft);
        counter.setText(sec+"s");
    }

    public void newQuestion(){
        Random rand = new Random();
        options.clear();

        int a=rand.nextInt(21);
        int b=rand.nextInt(21);

        locationOfCorrect=rand.nextInt(4);
        for(int i=1;i<=4;i++){
            if(i==locationOfCorrect)
                options.add(a+b);
            else {
                int wrong=rand.nextInt(41);
                while(wrong==a+b){
                    wrong=rand.nextInt(40);
                }
                options.add(wrong);
            }
        }
        questiontext.setText(Integer.toString(a)+"+"+Integer.toString(b));

        button1.setText(Integer.toString(options.get(0)));
        button2.setText(Integer.toString(options.get(1)));
        button3.setText(Integer.toString(options.get(2)));
        button4.setText(Integer.toString(options.get(3)));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.option1);
        button2=findViewById(R.id.option2);
        button3=findViewById(R.id.option3);
        button4=findViewById(R.id.option4);
        playagain=findViewById(R.id.playAgain);

        counter=findViewById(R.id.counterTimer);
        questiontext =findViewById(R.id.questionText);
        resulttext=findViewById(R.id.resultText);
        scoretext=findViewById(R.id.scoreText);

        playagain(findViewById(R.id.scoreText)); // function calling.
    }
}
