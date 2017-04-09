package com.modanisa.flickr.views.customviews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.modanisa.flickr.R;
import com.modanisa.flickr.models.APIUrls;
import com.modanisa.flickr.models.PhotoModel;
import com.modanisa.flickr.views.activities.FullImageActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public class PhotoItem extends LinearLayout {

    private LayoutInflater layoutInflater;
    private Context context;
    private ImageView flickrImage;
    private TextView imageTitle;

    public PhotoItem(Context context) {
        super(context);
        this.context = context;
        initialization();
    }

    private void initialization() {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_grid_cell, this, true);

        flickrImage = (ImageView)findViewById(R.id.grid_cell_iv_image);

        imageTitle = (TextView) findViewById(R.id.grid_cell_tv_title);
    }

    public void setPhotoCellData(final PhotoModel photoModel) {

        //Constructing the display url
        String url = "https://farm" + photoModel.getFarm() + ".staticflickr.com/" + photoModel.getServer() + "/" + photoModel.getId()
                + "_" + photoModel.getSecret() + "_m" + ".jpg";

        //Constructing the full sharable url to display the full size image
        final String fullImageUrl = "https://farm" + photoModel.getFarm() + ".staticflickr.com/" + photoModel.getServer() + "/" + photoModel.getId()
                + "_" + photoModel.getSecret() + ".jpg";

        flickrImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullImageIntent = new Intent(context, FullImageActivity.class);
                fullImageIntent.putExtra("image_url", fullImageUrl);
                fullImageIntent.putExtra("image_title", photoModel.getTitle());
                context.startActivity(fullImageIntent);
            }
        });


        Picasso.with(context).load(url).placeholder(R.drawable.ic_picture_4x3).resize(300,300).into(flickrImage);

        imageTitle.setText(photoModel.getTitle());
    }

}
