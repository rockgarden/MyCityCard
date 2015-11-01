package com.eastcom.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastcom.mycitycard.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Intent serviceIntent;
    private EditText textInput;
    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=new TextView(this);
        tv.setText("hello context");
        iv=new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);
        addContentView(iv, new ViewGroup.LayoutParams(-1, -1));

        serviceIntent=new Intent();
        serviceIntent.setComponent(new ComponentName("com.eastcom.mycitycard", "com.eastcom.mycitycard.services.AppService"));

        findViewById(R.id.btnStartAppService).setOnClickListener(this);
        findViewById(R.id.btnEndAppService).setOnClickListener(this);
        findViewById(R.id.btnBindAppService).setOnClickListener(this);
        findViewById(R.id.btnUnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        findViewById(R.id.btnStartOtherApp).setOnClickListener(this);

        textInput=(EditText)findViewById(R.id.textInput);
        textInput.setHint("请输入数据");
        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 20) {
                    textInput.setError("数据不能超过20位");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                startService(serviceIntent);
                break;
            case R.id.btnEndAppService:
                stopService(serviceIntent);
                break;
            case R.id.btnBindAppService:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindAppService:
                unbindService(this);
                binder=null;
                break;
            case R.id.btnSync:
                if(binder!=null){
                    try {
                        binder.setData(textInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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
        }
    }

    /**
     * Called when a connection to the Service has been established, with
     * the {@link IBinder} of the communication channel to the
     * Service.
     *
     * @param name    The concrete component name of the service that has
     *                been connected.
     * @param service The IBinder of the Service's communication channel,
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("service Connected");
        System.out.println(service);
        binder= IMyAidlInterface.Stub.asInterface(service); //传递正确的对象
    }

    /**
     * Called when a connection to the Service has been lost.  This typically
     * happens when the process hosting the service has crashed or been killed.
     * This does <em>not</em> remove the ServiceConnection itself -- this
     * binding to the service will remain active, and you will receive a call
     * to {@link #onServiceConnected} when the Service is next running.
     *
     * @param name The concrete component name of the service whose
     *             connection has been lost.
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private IMyAidlInterface binder=null;
}
