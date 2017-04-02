package com.the_brainy_fools.wr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.model.MovieCinemaModel;

import java.util.ArrayList;

public class MovieCinemaAdapter extends RecyclerView.Adapter<MovieCinemaAdapter.Movie> {
    private MovieCinemaModel movieCM;
    private Context context;
    private ArrayList<MovieCinemaModel> data = new ArrayList<>();

    public MovieCinemaAdapter(Context context, ArrayList<MovieCinemaModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Movie onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_cinema_row, parent, false);

        return new Movie(rootView);
    }

    @Override
    public void onBindViewHolder(Movie holder, int position) {
        movieCM = data.get(position);

        Picasso.with(context).load(movieCM.getPoster()).error(R.drawable.ic_launcher).into(holder.poster);
        holder.title.setText(movieCM.getTitle());
        holder.genre.setText(movieCM.getGenres());
        holder.date.setText(movieCM.getDate());
        holder.popularity.setText(movieCM.getPopularity());
        holder.voteAverage.setText(movieCM.getVoteAverage());

        holder.follow.setImageDrawable(new IconicsDrawable(context).icon(GoogleMaterial.Icon.gmd_visibility));
        holder.watched.setImageDrawable(new IconicsDrawable(context).icon(GoogleMaterial.Icon.gmd_done));
        holder.favourite.setImageDrawable(new IconicsDrawable(context).icon(GoogleMaterial.Icon.gmd_favorite_border));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Movie extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, genre, date, popularity, voteAverage;
        ImageView poster, follow, watched, favourite;

        public Movie(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.movie_cinema_card_poster);
            title = (TextView) itemView.findViewById(R.id.movie_cinema_card_title);
            genre = (TextView) itemView.findViewById(R.id.movie_cinema_card_genre);
            date = (TextView) itemView.findViewById(R.id.movie_cinema_card_date);
            popularity = (TextView) itemView.findViewById(R.id.movie_cinema_card_popularity);
            voteAverage = (TextView) itemView.findViewById(R.id.movie_cinema_card_voteAverage);

            follow = (ImageView) itemView.findViewById(R.id.movie_cinema_follow);
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieCM = data.get(position);

                    //@TODO:
                    // new animation

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(follow);

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(follow);
                }
            });

            watched = (ImageView) itemView.findViewById(R.id.movie_cinema_watched);
            favourite = (ImageView) itemView.findViewById(R.id.movie_cinema_favourite);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
        }
    }
}
