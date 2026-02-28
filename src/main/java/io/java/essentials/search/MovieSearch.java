package io.java.essentials.search;

import io.java.essentials.model.Movie;
import io.java.essentials.model.SearchQuery;
import io.java.essentials.processor.MovieProcessor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovieSearch {
    private MovieProcessor movieProcessor;

    public MovieSearch(MovieProcessor movieProcessor) {
        this.movieProcessor = movieProcessor;
    }

    public List<Movie> searchMovie(SearchQuery searchQuery) {
        var movies = movieProcessor.loadMovies();

        return movies.stream()
                .filter(movie -> {      //filter by releaseYear
                    if (Objects.nonNull(searchQuery.getReleaseYear())) {
                        return movie.releasedYear().equals(searchQuery.getReleaseYear());
                    }
                    return true;
                })
                .filter(movie -> {      //filter by genere
                    Optional<String> matchByGenere = matchQueryByGenera(searchQuery, movie);
                    return matchByGenere.isPresent();
                })
                .filter(
                        //filter by rating
                        movie -> {
                            if (Objects.nonNull(searchQuery.getRating())) {
                                double rating = Double.parseDouble(movie.imdbRating());
                                return rating>=searchQuery.getRating();
                            }
                            return true;
                        }
                )
                .limit(10)
                .toList();
    }

    private static Optional<String> matchQueryByGenera(SearchQuery searchQuery, Movie movie) {
        if (Objects.isNull(searchQuery.getGeneres()) || searchQuery.getGeneres().isEmpty()) {
            return Optional.empty();
        }

        Optional<String> matchByGenere = searchQuery.getGeneres()
                .stream()
                .filter(genere -> movie.genre().contains(genere))
                .findAny();
        return matchByGenere;
    }
}
