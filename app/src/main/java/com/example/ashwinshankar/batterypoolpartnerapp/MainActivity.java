package com.example.ashwinshankar.batterypoolpartnerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mEmpty, mFull,mSwap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmpty = (Button) findViewById(R.id.empty);
        mFull = (Button) findViewById(R.id.full);


        mEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, EmptyBattery.class);
                startActivity(mIntent);
                finish();
                return;

            }
        });

        mFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, FullBattery.class);
                startActivity(mIntent);
                finish();
                return;

            }
        });

    }
}
