package io.java.essentials;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.fileProcessor.FileMovieProcessor;
import io.java.essentials.processor.mapper.CsvMovieMapper;
import io.java.essentials.processor.mapper.MovieMapper;

public class Main {
    public static void main(String[] args) {

        MovieMapper mapper = new CsvMovieMapper();

        MovieProcessor movieProcessor=new FileMovieProcessor(mapper);

        for (Movie movie : movieProcessor.loadMovies()) {
            System.out.println(movie.seriesTitle()+"...."+movie.releasedYear());
        }
    }
}