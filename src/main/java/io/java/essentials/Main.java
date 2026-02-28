package io.java.essentials;

import io.java.essentials.model.Movie;
import io.java.essentials.model.SearchQuery;
import io.java.essentials.processor.MovieProcessor;
import io.java.essentials.processor.fileProcessor.CsvMovieProcessor;
import io.java.essentials.processor.fileProcessor.JsonMovieProcessor;
import io.java.essentials.processor.mapper.CsvMovieMapper;
import io.java.essentials.processor.mapper.JsonMovieMapper;
import io.java.essentials.search.MovieSearch;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        MovieProcessor movieProcessor = movieProcessorFactory("imdb_top_1000.csv");
        MovieProcessor movieJsonProcessor = movieProcessorFactory("imdb_top_1000_json_file.json");
//        MovieProcessor movieXlProcessor = movieProcessorFactory("imdb_top_1000_json_file.xls");

        var movieSearch=new MovieSearch(movieProcessor);

        var dramaQuery=new SearchQuery("2000",7.5,List.of("Drama"));
        var comedyQuery=new SearchQuery(null,null,List.of("Comedy"));

        var dramaMovies=movieSearch.searchMovie(dramaQuery);
        var comedyMovies=movieSearch.searchMovie(comedyQuery);

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
        List<Movie> movies = movieProcessor.loadMovies();

        //use case 1 : Top 10 Highest rated movies of 2007
        List<Movie> highestRatedMovies2007 = movies
                .stream()
                .filter(movie -> movie.releasedYear().equals("2007"))
                .filter(movie -> movie.genre().contains("Drama"))
                .sorted((movie1, movie2) -> Double.compare(Double.parseDouble(movie2.imdbRating()), Double.parseDouble(movie1.imdbRating())))
                .limit(10)
                .toList();

        //use case 2 : List of movies by leading actor
        Map<String, List<Movie>> moviesByLeadingActor = movies.stream()
                .collect(Collectors.groupingBy(movie -> movie.star1()));


        //use case 3 rated movie by leading /supporting actor
        var actor = "Morgan Freeman";
        List<Movie> topFiveRatedByActor = movies.stream()
                .filter(movie -> movie.star1().equals(actor) || movie.star2().equals(actor) || movie.star3().equals(actor) || movie.star4().equals(actor))
                .sorted((movie1, movie2) -> Double.compare(Double.parseDouble(movie2.imdbRating()), Double.parseDouble(movie1.imdbRating())))
                .limit(5)
                .toList();


        //use case 4 total box office by an actor/leading
        double totalBoxOfficeByActor = movies.stream()
                .filter(movie -> movie.star1().equals(actor) || movie.star2().equals(actor) || movie.star3().equals(actor) || movie.star4().equals(actor))
                .map(movie -> movie.gross())
                .map(movieTotalGross -> {
                    var transformedData = movieTotalGross.replace("\"", "")
                            .replace(",", "");
                    return transformedData;
                })
                .mapToDouble(movieTotalGross -> Double.parseDouble(movieTotalGross))
                .sum();


        movies.forEach(movie -> System.out.println(movie.seriesTitle() + "...." + movie.releasedYear()));
//        for (Movie movie : movieProcessor.loadMovies()) {
//            System.out.println(movie.seriesTitle() + "...." + movie.releasedYear());
//        }
    }
}
