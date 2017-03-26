package com.vible_team.wr.model;

public class MovieSoonModel {
    int ID;
    String poster, title, genres, date, popularity;
    long dateMill;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        genres = genres.replaceAll("\\[(.*)\\]", "$1");

        genres = genres.replace("28", " action");
        genres = genres.replace("12", " adventure");
        genres = genres.replace("16", " animation");
        genres = genres.replace("35", " comedy");
        genres = genres.replace("80", " crime");
        genres = genres.replace("99", " documentary");
        genres = genres.replace("18", " drama");
        genres = genres.replace("10751", " family");
        genres = genres.replace("14", " fantasy");
        genres = genres.replace("10769", " foreign");
        genres = genres.replace("36", " history");
        genres = genres.replace("27", " horror");
        genres = genres.replace("10402", " music");
        genres = genres.replace("9648", " mystery");
        genres = genres.replace("10749", " romance");
        genres = genres.replace("878", " science fiction");
        genres = genres.replace("10770", " TV movie");
        genres = genres.replace("53", " thriller");
        genres = genres.replace("10752", " war");
        genres = genres.replace("37", " western");

        genres = genres.trim();

        this.genres = genres;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public long getDateMill() {
        return dateMill;
    }

    public void setDateMill(long dateMill) {
        this.dateMill = dateMill;
    }
}
