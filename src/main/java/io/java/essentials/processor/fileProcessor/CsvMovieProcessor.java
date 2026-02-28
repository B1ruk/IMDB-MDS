package io.java.essentials.processor.fileProcessor;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.mapper.MovieMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvMovieProcessor extends FileMovieProcessor{
    private String fileName;
    private MovieMapper movieMapper;

    public CsvMovieProcessor(MovieMapper movieMapper,String fileName) {
        super(movieMapper);
        this.fileName=fileName;
        this.movieMapper=movieMapper;
    }

    @Override
    public List<Movie> loadMovies() {
        Path path = Paths.get(System.getProperty("user.home"),fileName);
        try {
            return Files.readAllLines(path)
                    .stream()
                    .skip(1)
                    .map(line -> movieMapper.map(line))
                    .toList();


//            List<Movie> movies = new ArrayList<>();
//            List<String> movieLines = Files.readAllLines(path);
//            for(int i=1;i<movieLines.size();i++) {
//                var line=movieLines.get(i);
//                var movie=movieMapper.map(line);
//                movies.add(movie);
//            }
//
//            return movies;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
