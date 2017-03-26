package com.vible_team.wr.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vible_team.wr.R;

public class MainFragment extends Fragment {
    private FragmentTabHost tabHost;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.main_tabHost);

        Resources resources = getContext().getResources();

        tabHost.addTab(tabHost.newTabSpec("FL").setIndicator("", resources.getDrawable(R.drawable.ic_follow_black_24dp)), MainFollowedFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("W").setIndicator("", resources.getDrawable(R.drawable.ic_watched_single_black_24dp)), MainWatchedFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("FV").setIndicator("", resources.getDrawable(R.drawable.ic_unfavourite_black_24dp)), MainFavouriteFragment.class, null);

        return tabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }
}
