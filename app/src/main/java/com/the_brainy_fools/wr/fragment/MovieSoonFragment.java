package com.the_brainy_fools.wr.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.the_brainy_fools.wr.adapter.MovieSoonAdapter;
import com.the_brainy_fools.wr.model.MovieSoonModel;
import com.the_brainy_fools.wr.tools.KeyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MovieSoonFragment extends Fragment {
    private MovieSoonAdapter movieSA;
    private MovieSoonModel movieSM;
    private ArrayList<MovieSoonModel> data = new ArrayList<>();

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
        View rootView = inflater.inflate(R.layout.fragment_movie_soon, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.movie_soon_RV);
        final LinearLayoutManager linearLM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLM);
        movieSA = new MovieSoonAdapter(getContext(), data);
        recyclerView.setAdapter(movieSA);

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

    private void parseNewData(final int page) {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading_text), true, false);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = format.format(calendar.getTime());

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, KeyHelper.API_BASE + "movie/upcoming?" + KeyHelper.API_KEY + "&language=" + Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry() + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                movieSM = new MovieSoonModel();

                                movieSM.setID(object.getInt("id"));
                                movieSM.setPoster("http://image.tmdb.org/t/p/w92" + object.getString("poster_path"));
                                movieSM.setTitle(object.getString("title"));
                                movieSM.setGenres(object.getJSONArray("genre_ids").toString());

                                String date = object.getString("release_date");
                                movieSM.setDate(date);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                Date dateMill = dateFormat.parse(date + " " + PreferenceManager.getDefaultSharedPreferences(getContext()).getString("preferences_hour", "00") + ":00:00");
                                movieSM.setDateMill(dateMill.getTime());

                                movieSM.setPopularity(object.getString("popularity"));

                                data.add(movieSM);
                            }

                            movieSA.notifyDataSetChanged();
                            loading = false;
                            progressDialog.dismiss();
                        } catch (JSONException | ParseException e) {
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