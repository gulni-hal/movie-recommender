package com.mr.movie_recommender.movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/movie")
public class MovieController {
    private final MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String director,
                                 @RequestParam(required = false) String genre,
                                 @RequestParam(required = false) int year
                                 ) {
        if (name != null && director != null) {
            return movieService.getMovieAndDirector(name, director);
        }
        else if (name != null) {
            return movieService.getMoviesByName(name);
        }
        else if (director != null) {
            return movieService.getMoviesByDirector(director);
        }
        else if (genre != null) {
            return movieService.getMoviesByGenre(genre);
        }
        else if (year != 0) {
            return movieService.getMoviesByYear(year);
        }
        else{
            return movieService.getMovies();
        }

    }
}
