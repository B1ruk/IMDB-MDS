package io.java.essentials;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.fileProcessor.CsvMovieProcessor;
import io.java.essentials.processor.fileProcessor.FileMovieProcessor;
import io.java.essentials.processor.fileProcessor.JsonMovieProcessor;
import io.java.essentials.processor.mapper.CsvMovieMapper;
import io.java.essentials.processor.mapper.JsonMovieMapper;
import io.java.essentials.processor.mapper.MovieMapper;

public class Main {
    public static void main(String[] args) {

        MovieMapper csvMovieMapper = new CsvMovieMapper();
        MovieMapper jsonMovieMapper = new JsonMovieMapper();

        MovieProcessor movieProcessor=new CsvMovieProcessor(csvMovieMapper,"imdb_top_1000.csv");
        MovieProcessor movieJsonProcessor=new JsonMovieProcessor(jsonMovieMapper,"imdb_top_1000_json_file.json");


        for (Movie movie : movieProcessor.loadMovies()) {
            System.out.println(movie.seriesTitle()+"...."+movie.releasedYear());
        }

        for (Movie movie : movieJsonProcessor.loadMovies()) {
            System.out.println(movie.seriesTitle()+"*******"+movie.releasedYear());
        }
    }
}
