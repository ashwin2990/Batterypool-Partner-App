package com.example.ashwinshankar.batterypoolpartnerapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuestionsSpreadsheetWebService {

    @POST("1FAIpQLSe2vmsviKuCS5plDce363vtz--7tbykYwS3gn1bxuLRtAoirw/formResponse")
    @FormUrlEncoded
    Call<Void> completeQuestionnaire(
            @Field("entry.801012793") String batteryIDinEntry,
            @Field("entry.1682619988") String batteryIDoutEntry,
            @Field("entry.1120772962") String vehicleNoEntry,
            @Field("entry.1732129015") String odometerEntry
    );

}
