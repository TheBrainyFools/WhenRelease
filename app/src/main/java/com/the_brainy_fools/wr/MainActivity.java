package com.the_brainy_fools.wr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.the_brainy_fools.wr.fragment.MainFragment;
import com.the_brainy_fools.wr.fragment.MovieFragment;
import com.the_brainy_fools.wr.fragment.PreferencesFragment;
import com.the_brainy_fools.wr.fragment.SerialFragment;

import java.util.ArrayList;

public class MainActivity extends Debug {
    private MaterialSearchView searchView;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.main)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_home)),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.movie)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_movie)),
                        new SecondaryDrawerItem()
                                .withName(R.string.serial)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_airplay)),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.donate)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_monetization_on))
                                .withBadge(":)"),
                        new SecondaryDrawerItem()
                                .withName(R.string.rate)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_favorite)),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.settings)
                                .withIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_settings))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                                break;
                            case 2:
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new MovieFragment()).commit();
                                break;
                            case 3:
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new SerialFragment()).commit();
                                break;
                            case 8:
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PreferencesFragment()).commit();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                })
                .build();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getBoolean("preferences_guide", true)) {

            startActivity(new Intent(this, GuideActivity.class));

            new TapTargetSequence(this)
                    .target(
                            TapTarget.forToolbarNavigationIcon(toolbar, "Navigation!", "Navigation will help you to find the right section!")
                                    .outerCircleColor(R.color.colorPrimary)
                                    .targetCircleColor(R.color.colorBackground)
                                    .textColor(R.color.colorBackground)
                                    .dimColor(R.color.black)
                                    .drawShadow(true)
                                    .cancelable(true)
                                    .tintTarget(true)
                    ).start();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("preferences_guide", Boolean.FALSE).apply();
        }

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getResources().getStringArray(R.array.results));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
