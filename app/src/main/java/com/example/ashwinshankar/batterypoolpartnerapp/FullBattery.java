package com.example.ashwinshankar.batterypoolpartnerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FullBattery extends AppCompatActivity {

    EditText mbatteryID;
    Button mStart, mCancel;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_full_battery);

        mbatteryID = (EditText) findViewById(R.id.editText);

        mStart = (Button) findViewById(R.id.button);
        mCancel = (Button) findViewById(R.id.button2);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String batteryID = mbatteryID.getText().toString();
                mDatabase.child("batterylocations").child("YS Traders").child(batteryID).child("status").setValue(0);//0 charged, 1 reserved, 2 uncharged, 3 in scooter
                Intent mIntent = new Intent(FullBattery.this, MainActivity.class);
                startActivity(mIntent);
                finish();
                return;
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(FullBattery.this, MainActivity.class);
                startActivity(mIntent);
                finish();
                return;

            }
        });
    }
}
