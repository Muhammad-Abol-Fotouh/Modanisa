package com.modanisa.flickr.controllers;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.modanisa.flickr.R;
import com.modanisa.flickr.models.APIUrls;
import com.modanisa.flickr.models.PhotosResponseModel;
import com.modanisa.flickr.utils.MessagesUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public class NetworkManager {

    private static Context mContext;
    private static NetworkManager networkManager;
    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private EndpointsInterface endpointsInterface;

    // Private constructor to make the network manager on reachable using the getInstance constructor
    private NetworkManager() {
        initialization();
    }

    // Using singleton
    public static synchronized NetworkManager getInstance(final Context context) {
        mContext = context;

        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    // Initialization of retrofit instance to perform API calls
    private void initialization() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

        builder = new Retrofit.Builder()
                .baseUrl(APIUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.client(httpClient.build()).build();

        endpointsInterface = retrofit.create(EndpointsInterface.class);
    }

    // Retrieve photos from API
    public void getPhotos(int page, int perpage, final APIResponseCallback apiResponseCallback){
        Call<PhotosResponseModel> call = endpointsInterface.getPhotos(APIUrls.METHOD, APIUrls.API_KEY, APIUrls.TAG, page, perpage, APIUrls.FORMAT, "1");

        call.clone().enqueue(new Callback<PhotosResponseModel>() {
            @Override
            public void onResponse(Call<PhotosResponseModel> call, Response<PhotosResponseModel> response) {
                if (response.code()==200){

                    PhotosResponseModel photosResponseModel = (PhotosResponseModel)response.body();

                    if (photosResponseModel.getPhotos()!=null){
                        apiResponseCallback.onSuccess(response);
                    }else{
                        apiResponseCallback.onFailure(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotosResponseModel> call, Throwable t) {
                MessagesUtils.showToast(mContext, mContext.getString(R.string.server_error));
            }

        });
    }

}
