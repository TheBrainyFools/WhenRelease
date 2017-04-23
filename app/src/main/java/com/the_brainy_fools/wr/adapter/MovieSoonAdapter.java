package com.the_brainy_fools.wr.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.MainActivity;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.database.DatabaseHelper;
import com.the_brainy_fools.wr.fragment.MovieSoonDetailFragment;
import com.the_brainy_fools.wr.model.MovieSoonModel;

import java.util.ArrayList;

public class MovieSoonAdapter extends RecyclerSwipeAdapter<MovieSoonAdapter.Movie> {
    private MovieSoonModel movieSM;
    private Context context;
    private ArrayList<MovieSoonModel> data = new ArrayList<>();

    private ArrayList<Integer> followedID = new ArrayList<>();
    private ArrayList<Integer> watchedID = new ArrayList<>();
    private ArrayList<Integer> favouriteID = new ArrayList<>();

    private DatabaseHelper databaseHelper;

    public MovieSoonAdapter(Context context, ArrayList<MovieSoonModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Movie onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_soon_row, parent, false);

        databaseHelper = new DatabaseHelper(context);

        if (followedID.size() == 0)
            followedID.addAll(databaseHelper.query().getFollowed());

        if (watchedID.size() == 0)
            watchedID.addAll(databaseHelper.query().getWatched());

        if (favouriteID.size() == 0)
            favouriteID.addAll(databaseHelper.query().getFavourite());

        return new Movie(rootView);
    }

    @Override
    public void onBindViewHolder(Movie holder, int position) {
        movieSM = data.get(position);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        Picasso.with(context).load(movieSM.getPoster()).error(R.drawable.ic_launcher).into(holder.poster);
        holder.title.setText(movieSM.getTitle());
        holder.genre.setText(movieSM.getGenres());
        holder.date.setText(movieSM.getDate());
        holder.popularity.setText(movieSM.getPopularity());

        if (followedID.size() != 0 && followedID.contains(movieSM.getID()))
            holder.follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_follow_black_24dp));
        else
            holder.follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfollow_black_24dp));

        if (watchedID.size() != 0 && watchedID.contains(movieSM.getID()))
            holder.watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unwatched_single_black_24dp));
        else
            holder.watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watched_single_black_24dp));

        if (favouriteID.size() != 0 && favouriteID.contains(movieSM.getID()))
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfavourite_black_24dp));
        else
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite_black_24dp));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.movie_soon_card_swipe;
    }

    public class Movie extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        RelativeLayout bottom;
        TextView title, genre, date, popularity, voteAverage;
        ImageView poster;
        ImageButton follow, watched, favourite;

        public Movie(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.movie_soon_card_swipe);
            bottom = (RelativeLayout) itemView.findViewById(R.id.movie_soon_card_bottom);
            poster = (ImageView) itemView.findViewById(R.id.movie_soon_card_poster);
            title = (TextView) itemView.findViewById(R.id.movie_soon_card_title);
            genre = (TextView) itemView.findViewById(R.id.movie_soon_card_genre);
            date = (TextView) itemView.findViewById(R.id.movie_soon_card_date);
            popularity = (TextView) itemView.findViewById(R.id.movie_soon_card_popularity);
            follow = (ImageButton) itemView.findViewById(R.id.movie_soon_card_follow);
            watched = (ImageButton) itemView.findViewById(R.id.movie_soon_card_watched);
            favourite = (ImageButton) itemView.findViewById(R.id.movie_soon_card_favourite);

            bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieSM = data.get(getLayoutPosition());
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new MovieSoonDetailFragment(movieSM.getID())).addToBackStack(null).commit();
                }
            });

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieSM = data.get(position);

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(follow);

                    if (followedID.size() != 0 && followedID.contains(movieSM.getID())) {
                        databaseHelper.update().unfollow(movieSM.getID());
                        follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfollow_black_24dp));
                        followedID.remove(followedID.indexOf(movieSM.getID()));
                    } else {
                        databaseHelper.update().follow(movieSM.getID(), movieSM.getPoster(), movieSM.getTitle(), movieSM.getGenres(), movieSM.getDate(), movieSM.getDateMill());
                        follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_follow_black_24dp));
                        followedID.add(movieSM.getID());
                    }

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(follow);
                }
            });

            watched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieSM = data.get(position);

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(watched);

                    if (watchedID.size() !=0 && watchedID.contains(movieSM.getID())) {
                        databaseHelper.update().unwatched(movieSM.getID());
                        watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watched_single_black_24dp));
                        watchedID.remove(watchedID.indexOf(movieSM.getID()));
                    } else {
                        databaseHelper.update().watched(movieSM.getID(), movieSM.getPoster(), movieSM.getTitle(), movieSM.getGenres());
                        watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unwatched_single_black_24dp));
                        watchedID.add(movieSM.getID());
                    }

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(watched);
                }
            });

            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieSM = data.get(position);

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(favourite);

                    if (favouriteID.size() !=0 && favouriteID.contains(movieSM.getID())) {
                        databaseHelper.update().unfavourite(movieSM.getID());
                        favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite_black_24dp));
                        favouriteID.remove(favouriteID.indexOf(movieSM.getID()));
                    } else {
                        databaseHelper.update().favourite(movieSM.getID(), movieSM.getPoster(), movieSM.getTitle(), movieSM.getGenres());
                        favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfavourite_black_24dp));
                        favouriteID.add(movieSM.getID());
                    }

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(favourite);
                }
            });
        }
    }
}
