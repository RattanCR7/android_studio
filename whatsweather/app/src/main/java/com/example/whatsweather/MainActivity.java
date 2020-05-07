package com.example.whatsweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView weather;
    EditText editText;
    public class DownloadTask extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection connections=null;
            try{
                url=new URL(urls[0]);
                connections=(HttpURLConnection) url.openConnection();
                InputStream in =connections.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject=new JSONObject(s);
                String weatherinfo = jsonObject.getString("weather");
                JSONArray arr=new JSONArray(weatherinfo);
                String message="";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonpart=arr.getJSONObject(i);
                    String main="";
                    String description="";
                    main+=jsonpart.getString("main");
                    description+=jsonpart.getString("description");
                    if(!main.equals("") && !description.equals("")){
                        message+=main+ " : " + description +"\r\n";
                    }
                }
                if(!message.equals("")) {
                    weather.setText(message);
                }else{
                    Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void checkweather(View view){
        DownloadTask task=new DownloadTask();
        task.execute("https://openweathermap.org/data/2.5/weather?q="+ editText.getText().toString() +"&appid=439d4b804bc8187953eb36d2a8c26a02");
        InputMethodManager mgr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.entercity);
        weather=findViewById(R.id.weather);

    }
}
