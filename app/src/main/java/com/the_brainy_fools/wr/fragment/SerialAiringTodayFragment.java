package com.the_brainy_fools.wr.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.adapter.SerialAiringAdapter;
import com.the_brainy_fools.wr.model.SerialAiringModel;
import com.the_brainy_fools.wr.tools.KeyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SerialAiringTodayFragment extends Fragment {
    private SerialAiringAdapter serialAA;
    private SerialAiringModel serialAM;
    private ArrayList<SerialAiringModel> data = new ArrayList<>();

    private int page = 1;
    private boolean loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseNewData(page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_serial_airing_today, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.serial_airing_RV);
        final LinearLayoutManager linearLM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLM);
        serialAA = new SerialAiringAdapter(getContext(), data);
        recyclerView.setAdapter(serialAA);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (!loading) {
                        if ((linearLM.getChildCount() + linearLM.findFirstVisibleItemPosition()) >= linearLM.getItemCount()) {
                            loading = true;
                            page += 1;
                            parseNewData(page);
                        }
                    }
                }
            }
        });

        return rootView;
    }

    private void parseNewData(int page) {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading_text), true, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, KeyHelper.SERIAL_AIRING_URL_REQUEST + "&language=" + Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry() + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                serialAM = new SerialAiringModel();
                                serialAM.setID(object.getInt("id"));
                                serialAM.setPoster("http://image.tmdb.org/t/p/w92" + object.getString("poster_path"));
                                serialAM.setTitle(object.getString("name"));
                                serialAM.setGenres(object.getJSONArray("genre_ids").toString());
                                serialAM.setPopularity(object.getString("popularity"));
                                serialAM.setVoteAverage(object.getString("vote_average"));

                                data.add(serialAM);
                            }

                            serialAA.notifyDataSetChanged();
                            loading = false;
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.error_network, Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }
}