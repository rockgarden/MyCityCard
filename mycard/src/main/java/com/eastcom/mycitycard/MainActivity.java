package com.eastcom.mycitycard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.eastcom.mycitycard.activitys.BaseDrawerActivity;
import com.eastcom.mycitycard.fragments.CardsFragment;
import com.eastcom.mycitycard.fragments.FundsFragment;
import com.eastcom.mycitycard.fragments.MainFragment;
import com.eastcom.mycitycard.fragments.SettingsFragment;
import com.eastcom.mycitycard.services.AppService;
import com.eastcom.mycitycard.services.MyBindService;
import com.eastcom.mycitycard.services.MyBindService.MyBinder;

public class MainActivity extends BaseDrawerActivity {

    private TextView textView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar = null;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;

    MyBindService myBindService;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bindService(new Intent(this, MyService.class), (ServiceConnection) this, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawerLayout();
        setupInit();

    }

    @Override
    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void setupInit() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        Float elevation = getResources().getDimension(R.dimen.elevation_toolbar);

        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragmentClass = MainFragment.class;
                break;
            case R.id.drawer_favorites:
                fragmentClass = FundsFragment.class;
                elevation = 0.0f;
                break;
            case R.id.drawer_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.drawer_cards:
                fragmentClass = CardsFragment.class;
                break;
            default:
                fragmentClass = MainFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("FRAGMENT");
        fragmentTransaction.commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(elevation);

        drawerLayout.closeDrawers();
    }

//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        drawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }



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
            MyBinder binder = (MyBinder) service;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        textView.setText("用户确认" + data.getStringExtra("data"));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_drawerlayout, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, AppService.class));
        //unbindService((ServiceConnection) this);
    }
}
