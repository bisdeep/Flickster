package com.bisdeep.flickster.adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bisdeep.flickster.DetailActivity;
        import com.bisdeep.flickster.R;
        import com.bisdeep.flickster.models.Movie;
        import com.bumptech.glide.Glide;

        import org.parceler.Parcels;

        import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d("smile", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("smile", "onBindViewHolder:" + position);
        Movie movie = movies.get(position);
        //bind the movie data into the view Holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        TextView releaseDate;
        RatingBar ratingBar;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            container = itemView.findViewById(R.id.container);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind(final Movie movie) {//put data inside different objects
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverView());
            ratingBar.setRating((float)movie.getVoteAverage());//setRating onlu takes a float
            releaseDate.setText("Released: " + movie.getReleaseDate());
            String imageUrl = movie.getPosterPath();


            //Reference the backdrop path if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            Glide.with(context).load(imageUrl).into(ivPoster);
            //add click listener on whole row
            container.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {//This is how we load the new activity from a click in movies adapter
                                                 //Navigate to detail activity on tap
                                                 Intent i = new Intent(context, DetailActivity.class);
                                                 i.putExtra("movie", Parcels.wrap(movie));
                                                 context.startActivity(i);

                                                // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
            );//*****semicolon as this is the end of line 78.****
/*
             container.setOnClickListener(view) => {
                Intent j = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));
                context.startActivity(i);
            });
        }*/
        }
    }
}
