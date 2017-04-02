package com.the_brainy_fools.wr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.database.DatabaseHelper;
import com.the_brainy_fools.wr.model.SerialAiringModel;

import java.util.ArrayList;

public class SerialAiringAdapter extends RecyclerSwipeAdapter<SerialAiringAdapter.Serial> {
    private SerialAiringModel serialAA;
    private Context context;
    private ArrayList<SerialAiringModel> data = new ArrayList<>();
    private ArrayList<Integer> followedID = new ArrayList<>();

    private DatabaseHelper databaseHelper;

    public SerialAiringAdapter(Context context, ArrayList<SerialAiringModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Serial onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_serial_airing_today_row, parent, false);
        return new Serial(rootView);
    }

    @Override
    public void onBindViewHolder(Serial holder, int position) {
        serialAA = data.get(position);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                Toast.makeText(context, "Swiped", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(context).load(serialAA.getPoster()).error(R.drawable.ic_launcher).into(holder.poster);
        holder.title.setText(serialAA.getTitle());
        holder.genre.setText(serialAA.getGenres());
        holder.popularity.setText(serialAA.getPopularity());
        holder.voteAverage.setText(serialAA.getVoteAverage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.serial_airing_card_swipe;
    }


    public class Serial extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView title, genre, popularity, voteAverage;
        ImageView poster;

        public Serial(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.serial_airing_card_swipe);
            poster = (ImageView) itemView.findViewById(R.id.serial_airing_card_poster);
            title = (TextView) itemView.findViewById(R.id.serial_airing_card_title);
            genre = (TextView) itemView.findViewById(R.id.serial_airing_card_genre);
            popularity = (TextView) itemView.findViewById(R.id.serial_airing_card_popularity);
            voteAverage = (TextView) itemView.findViewById(R.id.serial_airing_card_voteAverage);
        }
    }
}