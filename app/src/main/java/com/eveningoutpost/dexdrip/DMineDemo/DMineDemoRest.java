package com.eveningoutpost.dexdrip.DMineDemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eveningoutpost.dexdrip.Models.BgReading;
import com.eveningoutpost.dexdrip.Models.TransmitterData;
import com.eveningoutpost.dexdrip.ShareModels.DexcomShareInterface;
import com.eveningoutpost.dexdrip.ShareModels.ShareAuthenticationBody;
import com.eveningoutpost.dexdrip.ShareModels.ShareGlucose;
import com.eveningoutpost.dexdrip.ShareModels.ShareUploadPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

/**
 * Created by stephenblack on 6/1/15.
 */
public class DMineDemoRest {

    public static Gson gson = new GsonBuilder().create();

    public void postData(TransmitterData transmitterData) {
        try{
            demoInterface().sendDemoData(transmitterData.transmitter_id, transmitterData, new Callback<Response>() {
                @Override
                public void success(Response t, Response response) {
                    Log.e("Response", String.valueOf(response.getStatus()));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("RETROFIT ERROR: ", "" + retrofitError.toString());
                }
            });
        }
        catch (Exception ex) {
            Log.e("Unrecognized Error: ",ex.getMessage());
            Log.e("Unrecognized Error: ",ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }


    private DemoInterface demoInterface() {
        return adapter().build().create(DemoInterface.class);
    }

    private RestAdapter.Builder adapter() {
        RestAdapter.Builder adapterBuilder = new RestAdapter.Builder();
        adapterBuilder
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("RESTDEMO"))
//                .setEndpoint("http://xdrip-demo.herokuapp.com")
                .setEndpoint("http://192.168.0.20:3000")
                .setRequestInterceptor(interceptor)
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()));
        return adapterBuilder;
    }

    RequestInterceptor interceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestInterceptor.RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
        }
    };
}
