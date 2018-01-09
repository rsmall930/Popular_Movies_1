package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mMovieNameText;
    private ImageView mMovieImage;
    private TextView mOverview;
    private TextView mRelease;
    private TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieNameText = (TextView) findViewById(R.id.detail_movie_name);
        mMovieImage = (ImageView) findViewById(R.id.detail_thumbnail);
        mOverview = (TextView) findViewById(R.id.detail_overview);
        mRelease = (TextView) findViewById(R.id.detail_release_date);
        mRating = (TextView) findViewById(R.id.user_rating);

        String movieDetails;
        Intent intentThatStarted = getIntent();

        if(intentThatStarted != null){
            if(intentThatStarted.hasExtra(Intent.EXTRA_TEXT)){
                Context context = getApplicationContext();
                movieDetails = intentThatStarted.getStringExtra(Intent.EXTRA_TEXT);

                int index = movieDetails.indexOf("*");
                String[] t = movieDetails.split("\\*");


                String movieURL = movieDetails.substring(0,index);
               // Log.d("DETAILVIEW",movieURL);
                Picasso.with(this).load(movieURL).into(mMovieImage);

                //String title = movieURL.substring(index,movieURL.length());
                String title = t[1];
                mMovieNameText.setText(title);

                String overview = t[2];
                mOverview.setText(overview);

                String release = t[3];
                mRelease.setText("Release Date: " + release);

                String rating = t[4];
                mRating.setText("Rating: " + rating + "/10");

            }
        }



    }
}
