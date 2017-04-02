package com.the_brainy_fools.wr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.the_brainy_fools.wr.R;

public class SerialFragment extends Fragment {
    private FragmentTabHost tabHost;

    public SerialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.serial_tabHost);

        tabHost.addTab(tabHost.newTabSpec("AT").setIndicator("Airing today"), SerialAiringTodayFragment.class, null);

        return tabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }
}
