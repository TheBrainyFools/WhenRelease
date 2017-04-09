package com.the_brainy_fools.wr.tools;

public class KeyHelper {
    public static final String API_BASE = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "api_key=fe497b618e596d47a41279dafb0d1cbf";

    public static final String MOVIE_SOON_URL_REQUEST = API_BASE + "discover/movie/?sort_by=popularity.desc&" + API_KEY;
    public static final String MOVIE_ID_URL_REQUEST = API_BASE + "movie/";
    public static final String MOVIE_CINEMA_URL_REQUEST = API_BASE + "movie/now_playing?" + API_KEY;
    public static final String SERIAL_AIRING_URL_REQUEST = API_BASE + "tv/airing_today?" + API_KEY;
}
