package com.example.ashwinshankar.batterypoolpartnerapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
    Spinner staticSpinnerFullBattery;
    Dialog dialog_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_full_battery);

        staticSpinnerFullBattery = (Spinner) findViewById(R.id.static_spinner_full_battery);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.batteryfull, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinnerFullBattery.setAdapter(adapter);

        mStart = (Button) findViewById(R.id.button);
        mCancel = (Button) findViewById(R.id.button2);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String batteryID = String.valueOf(staticSpinnerFullBattery.getSelectedItem());

                    mDatabase.child("batterylocations").child("YS Traders").child(batteryID).child("status").setValue(0);//0 charged, 1 reserved, 2 uncharged, 3 in scooter
                    mReservedBatteries = mDatabase.child("batterylocations").child("YS Traders");

                    mReservedBatteries.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            cycle_count = dataSnapshot.child(batteryID).child("cycles").getValue(Integer.class);
                            cycle_count++;
                            dataSnapshot.getRef().child(batteryID).child("cycles").setValue(cycle_count);
                            battery_count = dataSnapshot.child("count").getValue(Integer.class);
                            battery_count++;
                            dataSnapshot.getRef().child("count").setValue(battery_count);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    dialog_payment = new Dialog(FullBattery.this);
                    dialog_payment.setContentView(R.layout.dialogbox_payment);
                    dialog_payment.setTitle("BatteryPool");

                    Button reserve = (Button) dialog_payment.findViewById(R.id.reserve_button);
                    reserve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mIntent = new Intent(FullBattery.this, MainActivity.class);
                            startActivity(mIntent);
                        }
                    });

                    dialog_payment.show();

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
