package com.eastcom.mycitycard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.litesuits.common.assist.MyTimer;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity implements MyTimer.OnTickListener{

    private TextView loadTimer;

    private int i = 3;
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

    private void initView(){
        loadTimer = (TextView) findViewById(R.id.loadTimer);
        myTimerTV = (TextView) findViewById(R.id.myLoadTimer);
    }

    @Override
    public void onTick(long timestampInMilliseconds) {
        myTimerTV.setText(timestampInMilliseconds + "");
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case 0:
                    loadTimer.setText("over");
                    stopTime();
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                    break;
                default:
                    loadTimer.setText(msg.arg1 + "秒");
                    startTimer();
            }
        }
    };

    public void startTimer(){
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

    public void stopTime(){
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
