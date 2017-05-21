package com.the_brainy_fools.wr.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.PreferenceManager;
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
import com.daimajia.swipe.SwipeLayout;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.MainActivity;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.database.DatabaseHelper;
import com.the_brainy_fools.wr.fragment.MovieSoonDetailFragment;
import com.the_brainy_fools.wr.model.MovieCinemaModel;

import java.util.ArrayList;

public class MovieCinemaAdapter extends RecyclerView.Adapter<MovieCinemaAdapter.Movie> {
    private Activity activity;
    private MovieCinemaModel movieCM;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<MovieCinemaModel> data = new ArrayList<>();

    private ArrayList<Integer> watchedID = new ArrayList<>();
    private ArrayList<Integer> favouriteID = new ArrayList<>();

    private DatabaseHelper databaseHelper;

    public MovieCinemaAdapter(Activity activity, Context context, ArrayList<MovieCinemaModel> data) {
        this.activity = activity;
        this.context = context;
        this.data = data;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    @Override
    public Movie onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_cinema_row, parent, false);

        databaseHelper = new DatabaseHelper(context);

        if (watchedID.size() == 0)
            watchedID.addAll(databaseHelper.query().getWatched());

        if (favouriteID.size() == 0)
            favouriteID.addAll(databaseHelper.query().getFavourite());

        return new Movie(rootView);
    }

    @Override
    public void onBindViewHolder(Movie holder, int position) {
        movieCM = data.get(position);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        Picasso.with(context).load(movieCM.getPoster()).error(R.drawable.ic_splash).into(holder.poster);
        holder.title.setText(movieCM.getTitle());
        holder.genre.setText(movieCM.getGenres());
        holder.date.setText(movieCM.getDate());

        if (watchedID.size() != 0 && watchedID.contains(movieCM.getID()))
            holder.watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unwatched_single_black_24dp));
        else
            holder.watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watched_single_black_24dp));

        if (favouriteID.size() != 0 && favouriteID.contains(movieCM.getID()))
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite_black_24dp));
        else
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfavourite_black_24dp));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Movie extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        RelativeLayout bottom;
        TextView title, genre, date, popularity;
        ImageView poster;
        ImageButton watched, favourite;

        public Movie(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.movie_cinema_card_swipe);
            bottom = (RelativeLayout) itemView.findViewById(R.id.movie_cinema_card_bottom);
            poster = (ImageView) itemView.findViewById(R.id.movie_cinema_card_poster);
            title = (TextView) itemView.findViewById(R.id.movie_cinema_card_title);
            genre = (TextView) itemView.findViewById(R.id.movie_cinema_card_genre);
            date = (TextView) itemView.findViewById(R.id.movie_cinema_card_date);
            watched = (ImageButton) itemView.findViewById(R.id.movie_cinema_card_watched);
            favourite = (ImageButton) itemView.findViewById(R.id.movie_cinema_card_favourite);

            if (preferences.getBoolean("preferences_tapTargetView_recyclerView_genre", true)) {
                TapTargetView.showFor(activity, TapTarget.forView(genre, "Swipe!", "Swipe this list and you will see hidden menu :)")
                        .outerCircleColor(R.color.colorPrimary)
                        .targetCircleColor(R.color.colorBackground)
                        .textColor(R.color.colorBackground)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                );

                editor.putBoolean("preferences_tapTargetView_recyclerView_genre", Boolean.FALSE).apply();
            }

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {}

                @Override
                public void onOpen(SwipeLayout layout) {
                    if (preferences.getBoolean("preferences_tapTargetView_recyclerView_swiped", true)) {
                        TapTargetView.showFor(activity, TapTarget.forView(watched, "Yay!", "Now you can rapidly add this movie to one of the sections!")
                                .outerCircleColor(R.color.colorPrimary)
                                .textColor(R.color.colorBackground)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .cancelable(true)
                                .tintTarget(true)
                                .transparentTarget(true)
                        );

                        editor.putBoolean("preferences_tapTargetView_recyclerView_swiped", Boolean.FALSE).apply();
                    }
                }

                @Override
                public void onStartClose(SwipeLayout layout) {}

                @Override
                public void onClose(SwipeLayout layout) {}

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {}

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {}
            });

            bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieCM = data.get(getLayoutPosition());
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new MovieSoonDetailFragment(movieCM.getID())).addToBackStack(null).commit();
                }
            });

            watched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieCM = data.get(position);

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(watched);

                    if (watchedID.size() !=0 && watchedID.contains(movieCM.getID())) {
                        databaseHelper.update().unwatched(movieCM.getID());
                        watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watched_single_black_24dp));
                        watchedID.remove(watchedID.indexOf(movieCM.getID()));
                    } else {
                        databaseHelper.update().watched(movieCM.getID(), movieCM.getPoster(), movieCM.getTitle(), movieCM.getGenres());
                        watched.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unwatched_single_black_24dp));
                        watchedID.add(movieCM.getID());
                    }

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(watched);
                }
            });

            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    movieCM = data.get(position);

                    YoYo.with(Techniques.FlipOutX).duration(750).playOn(favourite);

                    if (favouriteID.size() !=0 && favouriteID.contains(movieCM.getID())) {
                        databaseHelper.update().unfavourite(movieCM.getID());
                        favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite_black_24dp));
                        favouriteID.remove(favouriteID.indexOf(movieCM.getID()));
                    } else {
                        databaseHelper.update().favourite(movieCM.getID(), movieCM.getPoster(), movieCM.getTitle(), movieCM.getGenres());
                        favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unfavourite_black_24dp));
                        favouriteID.add(movieCM.getID());
                    }

                    YoYo.with(Techniques.FlipInX).duration(750).playOn(favourite);
                }
            });
        }
    }
}
