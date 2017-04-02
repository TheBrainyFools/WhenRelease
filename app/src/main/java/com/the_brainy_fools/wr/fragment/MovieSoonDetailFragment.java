package com.the_brainy_fools.wr.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.the_brainy_fools.wr.R;
import com.the_brainy_fools.wr.tools.KeyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

@SuppressLint("ValidFragment")
public class MovieSoonDetailFragment extends Fragment {
    private int ID;
    private String IMDB_ID;
    private boolean parseDescriptionFromIMDB = false;
    private ImageView poster;
    private TextView title, original, year, genre, date, tag, description, budget, popularity, runtime, status, productionCompanies, productionCountries;

    public MovieSoonDetailFragment(int ID) {
        this.ID = ID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movie_soon_detail, container, false);

        poster = (ImageView) rootView.findViewById(R.id.MSDPoster);
        title = (TextView) rootView.findViewById(R.id.MSDTitle);
        original = (TextView) rootView.findViewById(R.id.MSDOriginal);
        year = (TextView) rootView.findViewById(R.id.MSDYear);
        genre = (TextView) rootView.findViewById(R.id.MSDGenre);
        date = (TextView) rootView.findViewById(R.id.MSDDate);
        tag = (TextView) rootView.findViewById(R.id.MSDTag);
        description = (TextView) rootView.findViewById(R.id.MSDDescription);
        budget = (TextView) rootView.findViewById(R.id.MSDBudget);
        popularity = (TextView) rootView.findViewById(R.id.MSDPopularity);
        runtime = (TextView) rootView.findViewById(R.id.MSDRuntime);
        status = (TextView) rootView.findViewById(R.id.MSDStatus);
        productionCompanies = (TextView) rootView.findViewById(R.id.MSDProductionCompanies);
        productionCountries = (TextView) rootView.findViewById(R.id.MSDProductionCountries);

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading_text), true, false);

        JsonObjectRequest parseID = new JsonObjectRequest(Request.Method.GET, KeyHelper.MOVIE_ID_URL_REQUEST + ID + "?" + KeyHelper.API_KEY + "&language=" + Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            IMDB_ID = response.getString("imdb_id");
                            parse(IMDB_ID);

                            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + response.getString("poster_path")).error(R.drawable.ic_launcher).into(poster);

                            status.setText(response.getString("status"));
                            title.setText(response.getString("title"));

                            JSONArray genresArray = response.getJSONArray("genres");
                            String genres = genresArray.getJSONObject(0).getString("name") + " ";
                            for (int i = 1; i < genresArray.length(); i++)
                                genres += genresArray.getJSONObject(i).getString("name") + " ";
                            genre.setText(genres);

                            tag.setText((!response.getString("tagline").isEmpty()) ? response.getString("tagline") : "N/A");

                            if (!response.getString("overview").equalsIgnoreCase("null"))
                                description.setText(response.getString("overview"));
                            else
                                parseDescriptionFromIMDB = true;

                            budget.setText((response.getInt("budget") != 0) ? "$" + response.getString("budget") : "N/A");

                            JSONArray productionCompaniesArray = response.getJSONArray("production_companies");
                            String productionsCompanies = productionCompaniesArray.getJSONObject(0).getString("name") + " ";
                            for (int i = 1; i < productionCompaniesArray.length(); i++)
                                productionsCompanies += productionCompaniesArray.getJSONObject(i).getString("name") + " ";
                            productionCompanies.setText(productionsCompanies);

                            JSONArray productionCountriesArray = response.getJSONArray("production_countries");
                            String productionsCountries = productionCountriesArray.getJSONObject(0).getString("name") + " ";
                            for (int i = 1; i < productionCountriesArray.length(); i++)
                                productionsCountries += productionCountriesArray.getJSONObject(i).getString("name") + " ";
                            productionCountries.setText(productionsCountries);

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
                }
        );
        Volley.newRequestQueue(getContext()).add(parseID);

        return rootView;
    }

    public void parse(String IMDB_ID) {
        JsonObjectRequest parseData = new JsonObjectRequest(Request.Method.GET, "http://www.omdbapi.com/?i=" + IMDB_ID + "&plot=full&r=json&tomatoes=true",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //mOriginal = response.getString("Title");
                            original.setText(response.getString("Title"));
                            year.setText(response.getString("Year"));
                            date.setText(response.getString("Released"));
                            runtime.setText(response.getString("Runtime"));
                            popularity.setText(response.getString("imdbRating"));

                            if (parseDescriptionFromIMDB)
                                description.setText(response.getString("Plot"));

                            /**
                             Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + response.getString("poster_path")).error(R.drawable.ic_launcher).into(poster);
                             if (response.getString("imdb_id") != "")
                             IMDB_ID = response.getString("imdb_id");
                             else {
                             budget.setText("$" + response.getString("budget"));
                             popularity.setText(response.getString("popularity"));
                             runtime.setText(response.getString("runtime"));
                             status.setText(response.getString("status"));
                             title.setText(response.getString("title"));
                             original.setText(response.getString("original_title"));
                             genre.setText(response.getJSONArray("genres").getJSONObject(0).getString("name"));
                             date.setText(response.getString("release_date"));
                             tag.setText(response.getString("tagline"));
                             description.setText(response.getString("overview"));
                             productionCompanies.setText(response.getJSONArray("production_companies").getJSONObject(0).getString("name"));
                             productionCountries.setText(response.getJSONArray("production_countries").getJSONObject(0).getString("name"));
                             }
                             */
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
                }
        );
        Volley.newRequestQueue(getContext()).add(parseData);
    }
}
