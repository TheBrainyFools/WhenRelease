package com.the_brainy_fools.wr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.database.DatabaseHelper;
import com.the_brainy_fools.wr.model.MainModel;
import com.the_brainy_fools.wr.R;

import java.util.ArrayList;

public class MainFollowAdapter extends RecyclerView.Adapter<MainFollowAdapter.Main> {
    private MainModel mainM;
    private Context context;
    private ArrayList<MainModel> data = new ArrayList<>();

    public MainFollowAdapter(Context context, ArrayList<MainModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Main onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_row, parent, false);

        return new Main(rootView);
    }

    @Override
    public void onBindViewHolder(Main holder, int position) {
        mainM = data.get(position);

        Picasso.with(context).load(mainM.getPoster()).error(R.drawable.ic_launcher).into(holder.poster);
        holder.title.setText(mainM.getTitle());
        holder.genre.setText(mainM.getGenres());
        holder.date.setText(mainM.getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Main extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        TextView title, genre, date;

        public Main(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.main_card_poster);
            title = (TextView) itemView.findViewById(R.id.main_card_title);
            genre = (TextView) itemView.findViewById(R.id.main_card_genre);
            date = (TextView) itemView.findViewById(R.id.main_card_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            mainM = data.get(position);

            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.update().unfollow(mainM.getID());
            data.remove(position);
            notifyItemRemoved(position);
        }
    }
}
