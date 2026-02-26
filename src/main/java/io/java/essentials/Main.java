package io.java.essentials;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.fileProcessor.CsvMovieProcessor;
import io.java.essentials.processor.fileProcessor.JsonMovieProcessor;
import io.java.essentials.processor.mapper.CsvMovieMapper;
import io.java.essentials.processor.mapper.JsonMovieMapper;
import io.java.essentials.processor.mapper.MovieMapper;

public class Main {
    public static void main(String[] args) {

        MovieProcessor movieProcessor = movieProcessorFactory( "imdb_top_1000.csv");
        MovieProcessor movieJsonProcessor = movieProcessorFactory("imdb_top_1000_json_file.json");
//        MovieProcessor movieXlProcessor = movieProcessorFactory("imdb_top_1000_json_file.xls");

        printResult(movieProcessor);
        printResult(movieJsonProcessor);
    }

    public static MovieProcessor movieProcessorFactory(String fileName) {
        String extension = fileName.split("\\.")[1];

        switch (extension) {
            case "csv":
                return new CsvMovieProcessor(new CsvMovieMapper(), fileName);
            case "json":
                return new JsonMovieProcessor(new JsonMovieMapper(), fileName);
        }
        throw new RuntimeException("unable to resolve extension");

    }

    private static void printResult(MovieProcessor movieProcessor) {
        for (Movie movie : movieProcessor.loadMovies()) {
            System.out.println(movie.seriesTitle() + "...." + movie.releasedYear());
        }
    }
}
