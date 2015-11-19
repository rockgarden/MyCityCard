package com.eastcom.mycitycard.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eastcom.mycitycard.R;
import com.litesuits.android.log.Log;
import com.litesuits.common.assist.MyTimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity implements MyTimer.OnTickListener {

    private TextView loadTimer;
    private TextView visionInfo;

    private int i = 5;
    private Timer timer = null;
    private TimerTask task = null;
    private MyTimer myTimer;
    private TextView myTimerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
        startTimer();
        myTimer = new MyTimer.Builder()
                .listener(this)
                .looper(Looper.getMainLooper())
                .timerIntervalInSeconds(1)
                .build();
        myTimer.start();

    }

    private void initView() {
        loadTimer = (TextView) findViewById(R.id.loadTimer);
        visionInfo = (TextView) findViewById(R.id.visionInfo);
        myTimerTV = (TextView) findViewById(R.id.myLoadTimer);
    }

    @Override
    public void onTick(long timestampInMilliseconds) {
        myTimerTV.setText(timestampInMilliseconds + "");
    }

    public void checkVision(String url){
        new AsyncTask<String,Float,String>(){
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    URLConnection connection = url.openConnection();
                    long total=connection.getContentLength();
                    InputStream inputStream=connection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    String line;
                    StringBuilder stringBuilder=new StringBuilder();
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line);
                        publishProgress((float)stringBuilder.toString().length()/total);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    return stringBuilder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPreExecute() {
                Log.i("vision check start");
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                visionInfo.setText(s);
                super.onPostExecute(s);
            }
            @Override
            protected void onProgressUpdate(Float... values) {
                Log.i(values[0]);
                super.onProgressUpdate(values);
            }
            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {

                case 0:
                    loadTimer.setText("over");
                    stopTime();
                    startActivity(new Intent(LoadingActivity.this, MainActivity_A.class));
                    break;
                case 3:
                    loadTimer.setText(msg.arg1 + "秒");
                    checkVision("http://www.baidu.com");
                    startTimer();
                    break;
                default:
                    loadTimer.setText(msg.arg1 + "秒");
                    startTimer();
            }
        }
    };

    public void startTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                i--;
                Message message = mHandler.obtainMessage();
                message.arg1 = i;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000);
    }

    public void stopTime() {
        timer.cancel();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_loading, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
