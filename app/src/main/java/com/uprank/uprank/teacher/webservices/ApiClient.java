package com.uprank.uprank.teacher.webservices;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static final String BASE_URL = "http://saiinfra.co.in/drline.saiinfra.co.in/uprank/app_api/teacher_api/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.header("Accept", "application/json");
                    requestBuilder.header("Content-Type", "application/json");
                    requestBuilder.header("Content-Type", "application/x-www-form-urlencoded");
                    requestBuilder.method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            httpClient.addInterceptor(logging);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }
}


