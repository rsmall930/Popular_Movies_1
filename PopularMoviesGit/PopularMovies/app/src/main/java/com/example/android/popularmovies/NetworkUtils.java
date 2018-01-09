package com.example.android.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Ryan on 12/18/2017.
 */

public class NetworkUtils {

    //ADD OWN API KEY
    final private static String API_KEY = BuildConfig.API_KEY;
    //ADD OWN API KEY

    final private static String ENDING_URL = "&language=en-US&page=1";
    final private static String FOLLOWING_URL = "?api_key=";
    final private static String baseURL =
            "https://api.themoviedb.org/3/movie/";


    final private static String SORT_BY_POP = "popular";
    final private static String SORT_BY_TOP = "top_rated";


    public static URL buildUrl(String Movie){
        if(Movie == "pop"){
            //PUT OWN API KEY BETWEEN BASE URL AND POP
            String sortBypopURL = baseURL +SORT_BY_POP + FOLLOWING_URL + API_KEY + ENDING_URL;
            URL url2 = null;

            try {
                url2 = new URL(sortBypopURL);

            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            return url2;
        }
        if(Movie == "top"){
            //PUT OWN API KEY BETWEEN BASE URL AND TOP
            String sortBypopURL = baseURL + SORT_BY_TOP + FOLLOWING_URL + API_KEY + ENDING_URL ;
            URL url23 = null;

            try {
                url23 = new URL(sortBypopURL);

            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            return url23;
        }

        Uri builtUri = Uri.parse(baseURL)
                        .buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;


    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
