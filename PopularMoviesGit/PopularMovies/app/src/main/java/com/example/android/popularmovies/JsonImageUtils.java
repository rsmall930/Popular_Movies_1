package com.example.android.popularmovies;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ryan on 12/26/2017.
 */

public class JsonImageUtils {
    public static String[] getImageDetailsFromJson(Context context, String MovieJsonString)
            throws JSONException{

        final String MDB_PAGE = "page";

        //Result Array
        final String MDB_RESULT = "results";

        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_ID = "id";
        final String MDB_MESSAGE = "status_message";

        final String MDB_OVERVIEW = "overview";
        final String MDB_RELEASE = "release_date";
        final String MDB_RATING = "vote_average";
        final String MDB_MOVIE_TITLE = "original_title";

        String [] parsedMovieData = null;
        String [] TitleArray;


        JSONObject ImageJson = new JSONObject(MovieJsonString);

        if(ImageJson.has(MDB_MESSAGE)){
            //Error Connecting
            Log.d("MSG","ERROR");
            return null;
        }

        JSONArray ImageArray = ImageJson.getJSONArray(MDB_RESULT);

        parsedMovieData = new String[ImageArray.length()];
        TitleArray = new String[ImageArray.length()];
        for(int i = 0; i < ImageArray.length();i++){

            String posterPath;

            String MovieOverview;
            String releaseDate;
            int voteAverage = 0;
            String title;


            JSONObject MovieObject = ImageArray.getJSONObject(i);

            //Getting the path for the poster
            posterPath = MovieObject.getString(MDB_POSTER_PATH);
            String imageFull = "http://image.tmdb.org/t/p/w500";
            imageFull = imageFull + posterPath;

            MovieOverview = MovieObject.getString(MDB_OVERVIEW);

            releaseDate = MovieObject.getString(MDB_RELEASE);

            voteAverage = MovieObject.getInt(MDB_RATING);

            title = MovieObject.getString(MDB_MOVIE_TITLE);

            //Putting the poster in array
            parsedMovieData[i] = imageFull + "*" + title + "*" + MovieOverview + "*" + releaseDate + "*" + voteAverage;
            TitleArray[i] = title;


        }

    return parsedMovieData;


    }

}
