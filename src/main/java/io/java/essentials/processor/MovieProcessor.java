package io.java.essentials.processor;

import io.java.essentials.model.Movie;

import java.util.List;

public interface MovieProcessor {
    public List<Movie> loadMovies();
}
