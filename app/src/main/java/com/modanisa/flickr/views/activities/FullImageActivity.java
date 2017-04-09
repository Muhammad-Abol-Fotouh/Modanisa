package com.modanisa.flickr.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.modanisa.flickr.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public class FullImageActivity extends Activity implements View.OnClickListener{

    private ImageView flickrImage, closeImage;
    private String imageUrl, imageTitle;
    private TextView imageTitleTV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        initialization();
    }

    // Initialize all views and views actions
    private void initialization(){
        imageTitleTV = (TextView) findViewById(R.id.full_image_activity_tv_title);

        progressBar = (ProgressBar)findViewById(R.id.full_image_activity_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        flickrImage = (ImageView)findViewById(R.id.full_image_activity_iv);
        closeImage = (ImageView)findViewById(R.id.full_image_activity_iv_close);
        closeImage.setOnClickListener(this);

        if (getIntent().getExtras()!=null){
            if (getIntent().hasExtra("image_url")){
                imageUrl = getIntent().getStringExtra("image_url");
                Picasso.with(this).load(imageUrl).placeholder(R.drawable.ic_picture_4x3).into(flickrImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });

            }

            if (getIntent().hasExtra("image_title")){
                imageTitle = getIntent().getStringExtra("image_title");
                if (imageTitle.equals("") || imageTitle.equals(null)){
                    imageTitleTV.setVisibility(View.GONE);
                }else{
                    imageTitleTV.setText(imageTitle);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.full_image_activity_iv_close:
                onBackPressed();
                break;
        }
    }
}
