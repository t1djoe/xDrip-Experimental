package com.eveningoutpost.dexdrip.DMineDemo;

import com.eveningoutpost.dexdrip.Models.BgReading;
import com.eveningoutpost.dexdrip.Models.TransmitterData;
import com.eveningoutpost.dexdrip.ShareModels.ShareGlucose;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by stephenblack on 6/1/15.
 */
public interface DemoInterface {
    @POST("/transmitters/{transmitter_id}/glucose_readings")
    void sendDemoData(@Path("transmitter_id") String transmitter_id,  @Body TransmitterData transmitterData, Callback<Response> callback);
}
