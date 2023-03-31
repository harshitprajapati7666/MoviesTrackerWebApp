package com.harshitprajapati.moviestrackerwebapp;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private TmdbService tmdbService;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies/{tmdbId}")
    public String showMovieDetails(@PathVariable String tmdbId, Model model) throws IOException, JSONException {
        Movie movie = tmdbService.getMovieDetails(tmdbId);
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @GetMapping("/watchlist")
    public String showWatchlist(Model model) {
        // retrieve the user's watchlist from the database
        List<Movie> watchlist = movieRepository.findAll();
        model.addAttribute("watchlist", watchlist);
        return "watchlist";
    }

    @PostMapping("/watchlist/add")
    public String addMovieToWatchlist(@ModelAttribute("movie") Movie movie) {
        // save the movie to the database
        movieRepository.save(movie);
        return "redirect:/watchlist";
    }
}
