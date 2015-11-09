package com.eastcom.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eastcom.mycitycard.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private EditText textInput;
    private TextView tvServiceOutput;
    private TextView tv;
    private ImageView iv;
    private Intent appServiceIntent;
    private Intent myServiceIntent;
    private MyService.Binder myBinder=null;
    private IMyAidlInterface appBinder=null;
    private MyReceiver receiver=null;

    public static void main(){
        MyThread myThreadA=new MyThread("A");
        MyThread myThreadB=new MyThread("B");
        myThreadA.start();//使用run()不用迸发
        myThreadB.start();
        MyRunnable myRunnableA=new MyRunnable("A");
        MyRunnable myRunnableB=new MyRunnable("B");
        Thread t1=new Thread(myRunnableA);
        Thread t2=new Thread(myRunnableB);
        t1.start();
        t2.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.mainView);

        tv=new TextView(this);
        tv.setText(String.format("TaskID:%d\nCurrent Activity:%s", getTaskId(), toString()));
        linearLayout.addView(tv);

        iv=new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);
        linearLayout.addView(iv);
        //addContentView(iv, new ViewGroup.LayoutParams(-1, -1));

        findViewById(R.id.btnStartAppService).setOnClickListener(this);
        findViewById(R.id.btnEndAppService).setOnClickListener(this);
        findViewById(R.id.btnBindAppService).setOnClickListener(this);
        findViewById(R.id.btnUnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        findViewById(R.id.btnStartOtherApp).setOnClickListener(this);
        findViewById(R.id.btnStartSelf).setOnClickListener(this);
        findViewById(R.id.btnTestRecyclerView).setOnClickListener(this);
        findViewById(R.id.btnSendMessage).setOnClickListener(this);
        findViewById(R.id.btnRegisterReceiver).setOnClickListener(this);
        findViewById(R.id.btnCanelReceiver).setOnClickListener(this);
        findViewById(R.id.btnLoadFragment).setOnClickListener(this);
        findViewById(R.id.btnStartTabs).setOnClickListener(this);

        textInput=(EditText)findViewById(R.id.textInput);
        textInput.setHint("please input data");
        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 20) {
                    textInput.setError("data length must < 20 ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvServiceOutput=(TextView)findViewById(R.id.textViewServiceOutput);

        appServiceIntent=new Intent();
        appServiceIntent.setComponent(new ComponentName("com.eastcom.mycitycard", "com.eastcom.mycitycard.services.AppService"));

        myServiceIntent=new Intent();
        myServiceIntent.setClass(this, MyService.class);
        myServiceIntent.putExtra("data", textInput.getText().toString());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnStartAppService:
                startService(appServiceIntent);
                startService(myServiceIntent);
                break;
            case R.id.btnEndAppService:
                stopService(appServiceIntent);
                stopService(myServiceIntent);
                break;
            case R.id.btnBindAppService:
                // TODO one activity bind only one service?!
                //bindService(appServiceIntent, this, Context.BIND_AUTO_CREATE);//appServiceIntent<->appBinder
                bindService(myServiceIntent, this, Context.BIND_AUTO_CREATE);//myServiceIntent<->myBinder
                break;
            case R.id.btnUnbindAppService:
                unbindService(this);
                appBinder=null;
                myBinder=null;
                break;
            case R.id.btnSync:
                if(appBinder!=null){
                    try {
                        appBinder.setData(textInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if(myBinder!=null){
                    myBinder.setData(textInput.getText().toString());
                }
                break;
            case R.id.btnStartOtherApp:
                //startActivity(new Intent("android.intent.action.MAIN"));//可选择启动所有的APP
                try {
                    startActivity(new Intent("com.eastcom.mycitycard.CoordinatorLayout", Uri.parse("app://hello")));
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,"Could not start the specified Activity",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnStartSelf:
                startActivity(new Intent(MainActivity.this,MainActivity.class));//选择启动self
                break;
            case R.id.btnTestRecyclerView:
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
                break;
            case R.id.btnSendMessage:
                //Intent intent = new Intent(this,MyReceiver.class);
                Intent intentA= new Intent(MyReceiver.ACTION);
                intentA.putExtra("code", "no body");
                //sendBroadcast(intentA);
                sendOrderedBroadcast(intentA,null);
                break;
            case R.id.btnRegisterReceiver:
                if(receiver==null){
                    receiver=new MyReceiver();
                    registerReceiver(receiver,new IntentFilter(MyReceiver.ACTION));
                }
                break;
            case R.id.btnCanelReceiver:
                unregisterReceiver(receiver);
                receiver=null;
                break;
            case R.id.btnLoadFragment:
                startActivity(new Intent(MainActivity.this,FragmentActivity.class));
                break;
            case R.id.btnStartTabs:
                startActivity(new Intent(MainActivity.this,TabsActivity.class));
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("service Connected");
        System.out.println(service);
        //appBinder= IMyAidlInterface.Stub.asInterface(service); //传递正确的对象,service只能实例一次
        myBinder= (MyService.Binder) service;
        myBinder.getService().setCallback(new MyService.Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",data);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            tvServiceOutput.setText(msg.getData().getString("data"));
        }
    };

}
