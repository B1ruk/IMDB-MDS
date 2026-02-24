package io.java.essentials.processor.fileProcessor;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.mapper.MovieMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileMovieProcessor implements MovieProcessor {

    private MovieMapper movieMapper;

    public FileMovieProcessor(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> loadMovies() {
        Path path = Paths.get(System.getProperty("user.home"),"imdb_top_1000.csv");
        try {
            List<Movie> movies = new ArrayList<>();
            List<String> movieLines = Files.readAllLines(path);
            for(int i=1;i<movieLines.size();i++) {
                var line=movieLines.get(i);
                var movie=movieMapper.map(line);
                movies.add(movie);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
