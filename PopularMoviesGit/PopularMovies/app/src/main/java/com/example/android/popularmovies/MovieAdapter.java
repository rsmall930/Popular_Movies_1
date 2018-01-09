package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



/**
 * Created by Ryan on 12/26/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberItems;
    private String[] mImageData;

    public ImageView mMovie1;
    public Context mContext;

   private MovieAdapterOnClickHandler mMovieAdapterOnClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String Movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mMovieAdapterOnClickHandler = clickHandler;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        int pos;
        public MovieViewHolder(View itemView){
            super(itemView);
            mMovie1 = (ImageView) itemView.findViewById(R.id.movie_poster_image);
            itemView.setOnClickListener(this);
            pos = getAdapterPosition();
            Log.d("MOVIEVIEW1",String.valueOf(pos));
            mContext = itemView.getContext();

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String movie = mImageData[adapterPosition];
            Log.d("ONCLICKADAPTER",String.valueOf(adapterPosition));
            mMovieAdapterOnClickHandler.onClick(movie);
        }

    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            mContext = parent.getContext();
            Log.d("SETTINGCONTEXT","T");
            int layoutIdforPoster = R.layout.image_items_list;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(layoutIdforPoster, parent, false);

            MovieViewHolder viewHolder = new MovieViewHolder(view);
            Log.d("MOVIEVIEWHOLDER", "E");
            return viewHolder;

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String[] imagesArray = new String[5];
        String t = mImageData[position];

        imagesArray = t.split("\\*");
       Picasso.with(mContext).load(imagesArray[0]).into(mMovie1);

    }
    @Override
    public int getItemCount() {
        if(mImageData == null){
            return 0;
        }
        return mImageData.length;
    }

    public void setImageData(String[] images){
        mImageData = images;
        notifyDataSetChanged();
    }

}
