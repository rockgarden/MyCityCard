package com.eastcom.mycitycard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eastcom.mycitycard.models.CardInfo;

/**
 * Created by rockgarden on 15/10/31.
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {
    FloatingActionButton fabBtn;
    CoordinatorLayout root;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_coordinator);

        Intent intent=getIntent();
        //Bundle data=intent.getBundleExtra("data");
        //CardInfo cardInfo= (CardInfo) intent.getSerializableExtra("cardInfo");
        CardInfo cardInfo= (CardInfo) intent.getParcelableExtra("cardInfo");

        TextView textView= (TextView) findViewById(R.id.textView);
        String string=String.format(
                "Card Info name=%s number=%s"
                ,cardInfo.getCardName()
                ,cardInfo.getCardNumber());
        textView.setText(string);

        final TextInputLayout textInput=(TextInputLayout)findViewById(R.id.textInput);
        textInput.setHint("请输入确认信息");
        final EditText editText=textInput.getEditText();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                if (editText == null) throw new AssertionError();
                i.putExtra("data",editText.getText().toString());
                setResult(1,i);
                finish();
            }
        });

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
        collapsingToolbarLayout.setTitle("My Card");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabBtn=(FloatingActionButton)findViewById(R.id.fabBtn);
        root=(CoordinatorLayout)findViewById(R.id.root);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Snackbar snackbar = Snackbar.make(root, "Ok", Snackbar.LENGTH_INDEFINITE);;
                Snackbar.make(root, "appear and rise", Snackbar.LENGTH_LONG)
                        .setAction("I know", new View.OnClickListener() {
                            @Override
                            public void onClick(View v){

                            }
                        })
                        .show();
            }
        });
    }
}
