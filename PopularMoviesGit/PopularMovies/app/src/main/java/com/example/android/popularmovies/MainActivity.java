package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //My RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);
        mRecyclerView.setHasFixedSize(true);

        mError = (TextView) findViewById(R.id.error_text);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this,2);

       mRecyclerView.setLayoutManager(gridLayoutManager);

       mMovieAdapter = new MovieAdapter(this);

       mRecyclerView.setAdapter(mMovieAdapter);
       loadMovieData();
    }

    @Override
    public void onClick(String Movie) {
        Context context = this;
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,Movie);
        startActivity(intent);

    }

    private void loadMovieData(){

                new MovieQueryTask().execute("pop");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.sort_by,menu);
      return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.menu_sort_popular){
            String text = "pop";
            Context context = MainActivity.this;
            if(isOnline() == true) {
                new MovieQueryTask().execute(text);
            }

        }
        if(menuItemSelected == R.id.menu_sort_rating){
            if(isOnline() == true) {
                new MovieQueryTask().execute("top");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void cannotConnect(){
        TextView textView = (TextView) findViewById(R.id.error_text);
        textView.setText("Cannot Connect, Please check network options");
    }
    public void Connected(){
        TextView textView = (TextView) findViewById(R.id.error_text);
        textView.setText("");
    }

    public class MovieQueryTask extends AsyncTask<String,Void,String[]>{
        TextView error = (TextView) findViewById(R.id.error_text);
        @Override
        protected void onPreExecute() {
            if(isOnline() == false){
                cannotConnect();
            }

            if(isOnline() == true){
                mError.setText("");
            }
        }

        @Override
        protected String[] doInBackground(String... urls) {
            if(isOnline() == true) {
                URL movieRequestUrl = NetworkUtils.buildUrl(urls[0]);

                try {
                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    String[] JsonImageURL = JsonImageUtils.getImageDetailsFromJson(MainActivity.this, jsonMovieResponse);
                    return JsonImageURL;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                cannotConnect();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] images) {
           if(images != null){
               mMovieAdapter.setImageData(images);
               mRecyclerView.setAdapter(mMovieAdapter);
           }
        }
    }
}
