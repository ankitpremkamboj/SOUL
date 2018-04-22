package com.soul.app.retrofit;


import com.soul.app.retrofit.*;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by rahil on 9/9/15.
 */
public class ApiClient {

    private static ApiClient instance;
    com.soul.app.retrofit.Apis apis;


    public ApiClient() {
        //code for retrofit 2.0

        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader(ApiConstants.APP_VERSION, "")
                        .addHeader(ApiConstants.AUTHENTICATION_KEY, "")
                        /*.addHeader("Accept", "Application/JSON")*/
                        .build();
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES)
                .addNetworkInterceptor(HEADER_INTERCEPTOR)
                .build();

        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();

        apis = retrofit.create(com.soul.app.retrofit.Apis.class);

    }

    public static ApiClient getClient() {
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    public com.soul.app.retrofit.Apis getApis() {
        return apis;
    }
}
