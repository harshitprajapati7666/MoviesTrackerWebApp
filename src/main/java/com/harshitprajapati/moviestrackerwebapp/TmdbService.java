package com.harshitprajapati.moviestrackerwebapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TmdbService {

    private static final String API_KEY = "653c651be796647265c2f790d54aa1c0";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private final OkHttpClient httpClient = new OkHttpClient();

    public Movie getMovieDetails(String tmdbId) throws IOException, JSONException {
        String url = BASE_URL + "/movie/" + tmdbId + "?api_key=" + API_KEY;
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);

            Movie movie = new Movie();
            movie.setTmdbId(tmdbId);
            movie.setTitle(jsonObject.getString("title"));
            movie.setOverview(jsonObject.getString("overview"));
            movie.setPosterUrl(IMAGE_BASE_URL + jsonObject.getString("poster_path"));

            return movie;
        }
    }
}