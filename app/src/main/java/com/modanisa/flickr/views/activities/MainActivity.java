package com.modanisa.flickr.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.modanisa.flickr.R;
import com.modanisa.flickr.adapters.PhotosAdapter;
import com.modanisa.flickr.controllers.APIResponseCallback;
import com.modanisa.flickr.controllers.NetworkManager;
import com.modanisa.flickr.models.PhotoModel;
import com.modanisa.flickr.models.PhotosResponseModel;
import com.modanisa.flickr.utils.AnimationUtils;
import com.modanisa.flickr.utils.ConnectionUtils;
import com.modanisa.flickr.utils.MessagesUtils;

import java.util.ArrayList;

import retrofit2.Response;

public class MainActivity extends Activity implements APIResponseCallback, View.OnClickListener{

    private GridView photosGrid;
    private PhotosAdapter photosAdapter;
    private ArrayList<PhotoModel> photoModelArrayList = new ArrayList<>();
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private int page = 1, perpage = 10;
    private LinearLayout noConnectionLayout;
    private Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    // Initialize all views and views actions
    private void initialization(){
        initializeToolbar();
        photosGrid = (GridView)findViewById(R.id.main_activity_photos_grid);
        progressBar = (ProgressBar)findViewById(R.id.main_activity_progress_bar);

        noConnectionLayout = (LinearLayout)findViewById(R.id.main_activity_ll_no_connection);

        tryAgain = (Button)findViewById(R.id.main_activity_btn_retry);
        tryAgain.setOnClickListener(this);

        photosAdapter = new PhotosAdapter(this, photoModelArrayList);
        photosGrid.setAdapter(photosAdapter);

        photosGrid.setOnScrollListener(new AbsListView.OnScrollListener() {

            public int totalItem;
            public int currentVisibleItemCount;
            public int currentFirstVisibleItem;
            public int currentScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount && this.currentScrollState == SCROLL_STATE_IDLE) {

                    getPhotos(page, perpage);
                }
            }
        });

        photosGrid.setLayoutAnimation(AnimationUtils.animateGridCells());

        getPhotos(page, perpage);
    }

    // Fire the network call method to get all photos
    private void getPhotos(int page, int perpage){

        if (ConnectionUtils.isInternetConnected(this)){
            progressBar.setVisibility(View.VISIBLE);
            noConnectionLayout.setVisibility(View.GONE);
            photosGrid.setVisibility(View.VISIBLE);
            NetworkManager.getInstance(this).getPhotos(page, perpage, this);
        }else{
            noConnectionLayout.setVisibility(View.VISIBLE);
            photosGrid.setVisibility(View.GONE);
        }
    }

    private void initializeToolbar(){
        toolbar = (Toolbar)findViewById(R.id.main_activity_toolbar);
        toolbar.setTitle(getString(R.string.main_activity_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onSuccess(Response response) {
        page++;
        progressBar.setVisibility(View.GONE);
        PhotosResponseModel photosResponseModel = (PhotosResponseModel)response.body();

        // Adding photos to Arraylist to use in adapter
        if (photosResponseModel !=null && photosResponseModel.getPhotos().getPhoto()!=null && photosResponseModel.getPhotos().getPhoto().length>0){
            for (int i=0; i<photosResponseModel.getPhotos().getPhoto().length; i++){
                if (!photoModelArrayList.contains(photosResponseModel.getPhotos().getPhoto()[i])){
                    photoModelArrayList.add(photosResponseModel.getPhotos().getPhoto()[i]);
                }
            }
            if(photosGrid.getAdapter()==null)
                photosGrid.setAdapter(photosAdapter);
            else{
                photosAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailure(Response response) {
        progressBar.setVisibility(View.GONE);
        PhotosResponseModel photosResponseModel = (PhotosResponseModel)response.body();

        MessagesUtils.showToast(this, photosResponseModel.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_activity_btn_retry:
                getPhotos(page, perpage);
                break;
        }
    }
}
