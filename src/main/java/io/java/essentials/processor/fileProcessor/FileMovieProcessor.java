package io.java.essentials.processor.fileProcessor;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.mapper.MovieMapper;

import java.util.Collections;
import java.util.List;

public abstract class FileMovieProcessor implements MovieProcessor {

    private MovieMapper movieMapper;

    public FileMovieProcessor(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> loadMovies() {
        return Collections.emptyList();
    }
}
