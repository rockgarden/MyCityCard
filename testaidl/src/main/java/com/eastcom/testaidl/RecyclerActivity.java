package com.eastcom.testaidl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerCellView;
    private RecyclerView recyclerCardlView;
    private Adapter_Card adapter_card;
    private List<RecyclerCardData> cardDatas = new ArrayList<RecyclerCardData>();

    private String[] names = { "朱茵", "张柏芝", "张敏", "巩俐", "黄圣依", "赵薇", "莫文蔚", "如花" };

    private String[] pics = { "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        getSupportActionBar().setTitle("cell+card");
        cardDatas.add(new RecyclerCardData("朱茵", "p1"));
//        recyclerCardlView =new RecyclerView(this);

        recyclerCellView =(RecyclerView) findViewById(R.id.cell);
        recyclerCellView.setLayoutManager(new LinearLayoutManager(this));
        recyclerCellView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerCellView.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerCellView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        recyclerCellView.setAdapter(new Adapter_Cell());

        recyclerCardlView =(RecyclerView) findViewById(R.id.card);
        recyclerCardlView.setLayoutManager(new LinearLayoutManager(this));
        recyclerCardlView.setItemAnimator(new DefaultItemAnimator());
        recyclerCardlView.setHasFixedSize(true);
        adapter_card = new Adapter_Card(this, cardDatas);
        recyclerCardlView.setAdapter(adapter_card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            // 当点击actionbar上的添加按钮时，向adapter中添加一个新数据并通知刷新
            case R.id.action_add:
                if (adapter_card.getItemCount() != names.length) {
                    cardDatas.add(new RecyclerCardData(names[adapter_card.getItemCount()], pics[adapter_card.getItemCount()]));
                    recyclerCardlView.scrollToPosition(adapter_card.getItemCount() - 1);
                    adapter_card.notifyDataSetChanged();
                }
                return true;
            // 当点击actionbar上的删除按钮时，向adapter中移除最后一个数据并通知刷新
            case R.id.action_remove:
                if (adapter_card.getItemCount() != 0) {
                    cardDatas.remove(adapter_card.getItemCount()-1);
                    recyclerCardlView.scrollToPosition(adapter_card.getItemCount() - 1);
                    adapter_card.notifyDataSetChanged();
                }
                return true;
            case R.id.action_settings:
                return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
