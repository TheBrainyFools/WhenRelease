package com.the_brainy_fools.wr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.the_brainy_fools.wr.adapter.MainFollowAdapter;
import com.the_brainy_fools.wr.database.DatabaseHelper;
import com.the_brainy_fools.wr.model.MainModel;
import com.the_brainy_fools.wr.receiver.AlarmHelper;
import com.the_brainy_fools.wr.R;

import java.util.ArrayList;

public class MainFollowedFragment extends Fragment {
    private View rootView;

    private RecyclerView recyclerView;
    private MainFollowAdapter mainA;
    private ArrayList<MainModel> data = new ArrayList<>();

    public DatabaseHelper databaseHelper;

    public MainFollowedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());
        data.addAll(databaseHelper.query().getFollowed(DatabaseHelper.FOLLOW_ID, null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_followed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_followed_RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainA = new MainFollowAdapter(getContext(), data);
        recyclerView.setAdapter(mainA);

        AlarmHelper.getInstance().init(getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (data.size() != 0) {
            mainA.notifyDataSetChanged();
            rootView.findViewById(R.id.main_followed_body_empty).setVisibility(View.GONE);
        }
        else
            rootView.findViewById(R.id.main_followed_body_empty).setVisibility(View.VISIBLE);
    }
}