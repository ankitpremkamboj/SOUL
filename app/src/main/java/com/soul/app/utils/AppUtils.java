package com.soul.app.utils;

import android.content.Context;

import com.soul.app.models.res.GeneralResp;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by techahead on 15/7/16.
 */
public class AppUtils {

    public static <T extends GeneralResp> void enqueueCall(final Context ctx, final Call<T> call, final Callback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {

                        try {
                            callback.onFailure(call, new Throwable(response.errorBody().string()));
                        } catch (IOException | NullPointerException e) {
                            callback.onFailure(call, new Throwable("Unknown"));
                            e.printStackTrace();
                        }
                    } else
                        callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response Failed Code : " + response.code()));
                }

                if (response.body() != null){}
                    //authenticateUser(ctx, response.body());


            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);

            }
        });
    }

}
