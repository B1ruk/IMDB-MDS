package io.java.essentials.processor.fileProcessor;

import com.google.gson.Gson;
import io.java.essentials.model.Movie;
import io.java.essentials.processor.mapper.MovieMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonMovieProcessor extends FileMovieProcessor {
    private String fileName;
    private MovieMapper movieMapper;

    public JsonMovieProcessor(MovieMapper movieMapper, String fileName) {
        super(movieMapper);
        this.fileName = fileName;
        this.movieMapper=movieMapper;
    }

    @Override
    public List<Movie> loadMovies() {
        Path path = Paths.get(System.getProperty("user.home"), fileName);

        try (var reader = Files.newBufferedReader(path)) {
            var movieRecords = new Gson().fromJson(reader, ArrayList.class);
            List<Movie> movieList = new ArrayList<>();

            for (var element : movieRecords) {
                var movie = movieMapper.map(element);
                movieList.add(movie);
            }
            return movieList;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse file" + fileName);
        }
    }
}
