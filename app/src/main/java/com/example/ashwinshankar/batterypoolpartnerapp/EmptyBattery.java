package com.example.ashwinshankar.batterypoolpartnerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.R.attr.name;



public class EmptyBattery extends AppCompatActivity {


    EditText mbatteryIDin;
    EditText mbatteryIDout;
    EditText mOdometer;
    EditText mVehicleNo;
    Button mStart, mCancel;
    private DatabaseReference mDatabase;
    private DatabaseReference mReservedBatteries;
    Integer battery_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_empty_battery);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://docs.google.com/forms/d/e/").build();

        final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);

        mbatteryIDin = (EditText) findViewById(R.id.editText13);
        mbatteryIDout = (EditText) findViewById(R.id.editText14);
        mVehicleNo = (EditText) findViewById(R.id.editText15);
        mOdometer = (EditText) findViewById(R.id.editText17);

        mStart = (Button) findViewById(R.id.button3);
        mCancel = (Button) findViewById(R.id.button4);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final String batteryIDin = mbatteryIDin.getText().toString();
                    final String batteryIDout = mbatteryIDout.getText().toString();
                    final String VehicleNo = mVehicleNo.getText().toString();
                    final String Odometer = mOdometer.getText().toString();
                    mDatabase.child("batterylocations").child("YS Traders").child(batteryIDin).child("status").setValue(2);
                    mDatabase.child("batterylocations").child("YS Traders").child(batteryIDout).child("status").setValue(3);

                    mReservedBatteries = mDatabase.child("batterylocations").child("YS Traders");
                    mReservedBatteries.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            battery_count=dataSnapshot.child("count").getValue(Integer.class);
                            battery_count--;
                            dataSnapshot.getRef().child("count").setValue(battery_count);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.d("XXX", "Got: " + batteryIDin + " " + batteryIDout);
                    Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire(batteryIDin, batteryIDout,VehicleNo, Odometer);
                    completeQuestionnaireCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("XXX", "Submitted. " + response);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("XXX", "Failed", t);
                        }
                    });
                    Intent mIntent = new Intent(EmptyBattery.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();
                    return;
                }

        });



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(EmptyBattery.this, MainActivity.class);
                startActivity(mIntent);
                finish();
                return;

            }
        });
    }



}
