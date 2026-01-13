package com.mr.movie_recommender.movie;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findAll().stream()
                .filter(movie -> director.equals(movie.getDirector()))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByName(String searchText) {
        return movieRepository.findAll().stream()
                .filter(movie ->movie.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByGenre(String searchText) {return movieRepository.findAll().stream()
            .filter(movie -> movie.getGenre().toLowerCase().contains(searchText.toLowerCase()))
            .collect(Collectors.toList());}

    public List<Movie> getMoviesByYear(int searchYear) {return movieRepository.findAll().stream()
            .filter(movie -> movie.getReleaseDate().equals(searchYear))
            .collect(Collectors.toList());}

    public List<Movie> getMovieAndDirector(String movieName, String director) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.equals(movie.getName())&& director.equals(movie.getDirector()))
                .collect(Collectors.toList());
    }

    public Movie movieAdd(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie movieUpdate(Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findByName(updatedMovie.getName());
        if (existingMovie.isPresent()) {
            Movie movieUpdated = existingMovie.get();
            movieUpdated.setName(updatedMovie.getName());
            movieUpdated.setDirector(updatedMovie.getDirector());
            movieUpdated.setReleaseDate(updatedMovie.getReleaseDate());
            movieUpdated.setGenre(updatedMovie.getGenre());

            Movie movie = movieRepository.save(movieUpdated);
            return movie;
        }
        return null;
    }
@Transactional
    public void  movieDelete(String movieName) {
        movieRepository.deleteByName(movieName);
    }
}
