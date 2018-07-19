package com.example.ashwinshankar.batterypoolpartnerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.value;
import static android.R.interpolator.cycle;


public class FullBattery extends AppCompatActivity {

    EditText mbatteryID;
    Button mStart, mCancel;
    Integer cycle_count, battery_count;
    private DatabaseReference mDatabase;
    private DatabaseReference mReservedBatteries;
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
                mReservedBatteries = mDatabase.child("batterylocations").child("YS Traders");

                mReservedBatteries.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cycle_count = dataSnapshot.child(batteryID).child("cycles").getValue(Integer.class);
                        cycle_count++;
                        dataSnapshot.getRef().child(batteryID).child("cycles").setValue(cycle_count);
                        battery_count=dataSnapshot.child("count").getValue(Integer.class);
                        battery_count++;
                        dataSnapshot.getRef().child("count").setValue(battery_count);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

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
