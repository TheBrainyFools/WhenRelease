package com.vible_team.wr.model;

public class SerialAiringModel {
    int ID;
    String poster, title, genres, popularity, voteAverage;

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

        genres = genres.replace("10759", " action & adventure");
        genres = genres.replace("16", " animation");
        genres = genres.replace("35", " comedy");
        genres = genres.replace("99", " documentary");
        genres = genres.replace("18", " drama");
        genres = genres.replace("10751", " family");
        genres = genres.replace("10762", " kids");
        genres = genres.replace("9648", " mystery");
        genres = genres.replace("10763", " news");
        genres = genres.replace("10764", " reality");
        genres = genres.replace("10765", " Sci-Fi & fantasy");
        genres = genres.replace("10766", " soap");
        genres = genres.replace("10767", " talk");
        genres = genres.replace("10768", " war & politics");
        genres = genres.replace("37", " western");
        genres = genres.replace("28", " action");
        genres = genres.replace("12", " adventure");
        genres = genres.replace("80", " crime");
        genres = genres.replace("14", " fantasy");
        genres = genres.replace("10769", " foreign");
        genres = genres.replace("36", " history");
        genres = genres.replace("27", " horror");
        genres = genres.replace("10402", " music");
        genres = genres.replace("10749", " romance");
        genres = genres.replace("878", " science fiction");
        genres = genres.replace("10770", " TV movie");
        genres = genres.replace("53", " thriller");
        genres = genres.replace("10752", " war");

        genres = genres.trim();

        this.genres = genres;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
