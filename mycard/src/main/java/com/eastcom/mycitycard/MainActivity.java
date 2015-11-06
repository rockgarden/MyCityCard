package com.eastcom.mycitycard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eastcom.mycitycard.activitys.CoordinatorLayoutActivity;
import com.eastcom.mycitycard.activitys.SettingsActivity;
import com.eastcom.mycitycard.fragments.cardFragment;
import com.eastcom.mycitycard.fragments.cardFragmentAdapter;
import com.eastcom.mycitycard.models.CardInfo;
import com.eastcom.mycitycard.services.AppService;
import com.eastcom.mycitycard.services.MyBindService;
import com.eastcom.mycitycard.services.MyBindService.MyBinder;
import com.litesuits.android.log.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageLoader loader;
    private ImageView iv_img;

    MyBindService myBindService;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, AppService.class));
        //bindService(new Intent(this, MyService.class), (ServiceConnection) this, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_main_drawerlayout);
        textView= (TextView) findViewById(R.id.textView);

        final TextInputLayout textInput=(TextInputLayout)findViewById(R.id.textInput);
        textInput.setHint("请输入用户名");
        EditText editText=textInput.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 20) {
                    textInput.setError("用户名不能超过20位");
                    textInput.setErrorEnabled(true);
                } else {
                    textInput.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final FloatingActionButton btn= (FloatingActionButton) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoordinatorLayoutActivity.class);

                Bundle bundle = new Bundle();
                //bundle.putString("name", "杭州公交卡");
                //bundle.putString("number", "1111111111111111111");
                //intent.putExtra("data", bundle);
                intent.putExtra("cardInfo", new CardInfo("杭州公交卡", "1111111111111111111"));

                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton btnSetting= (FloatingActionButton) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.ACTION);
                startActivity(intent);
            }
        });

        TabLayout tabs=(TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Tab1"));
        tabs.addTab(tabs.newTab().setText("Tab2"));
        tabs.addTab(tabs.newTab().setText("Tab3"));
        tabs.addTab(tabs.newTab().setText("Tab4"));
        tabs.addTab(tabs.newTab().setText("Tab5"));
        tabs.addTab(tabs.newTab().setText("Tab6"));
        List<String> titles=new ArrayList<>();
        List<Fragment> fragments=new ArrayList<>();
        for(int i=0;i<6;i++){
            String title="Tab"+(i+1);
            tabs.addTab(tabs.newTab().setText(title));
            titles.add(title);
            Fragment fragment=cardFragment.newInstance(title,title);
            fragments.add(fragment);
        }

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        cardFragmentAdapter mAdapter=new cardFragmentAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(mAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(mAdapter);

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.mainMenu);
        //drawerLayout.openDrawer(Gravity.LEFT);
        //drawerLayout.closeDrawer(Gravity.LEFT);

        loader = ImageLoader.getInstance();
        iv_img = (ImageView) this.findViewById(R.id.iv_img);
        String uri = "file:///" + "/myCard/imgCache";
        loader.displayImage(
                "http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg",
                iv_img, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        Log.i("info", "onLoadingStarted");
                    }
                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        Log.i("info", "onLoadingFailed");
                    }
                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        Log.i("info", "onLoadingComplete");
                    }
                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        Log.i("info", "onLoadingCancelled");
                    }
                });

        /*Picasso example
        Picasso.with(this)
                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
                .into(iv_img);
        Picasso.with(this)
                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
                .resize(150, 150).into(iv_img);
        Picasso.with(this)
                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
                .error(R.mipmap.ic_launcher).into(iv_img);
                */
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Intent intent = new Intent();
        intent.setClass(this, MyBindService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            isBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder binder = (MyBinder)service;
            myBindService = binder.getService();
            isBound = true;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        textView.setText("用户确认"+data.getStringExtra("data"));
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
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, AppService.class));
        //unbindService((ServiceConnection) this);
    }
}
