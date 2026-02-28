package io.java.essentials.model;

import java.util.List;
import java.util.Objects;

public final class SearchQuery {
    private final String releaseYear;
    private final Double rating;
    private final List<String> generes;

    public SearchQuery(String releaseYear, Double rating, List<String> generes) {
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.generes = generes;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public Double getRating() {
        return rating;
    }

    public List<String> getGeneres() {
        return generes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SearchQuery) obj;
        return Objects.equals(this.releaseYear, that.releaseYear) &&
                Objects.equals(this.rating, that.rating) &&
                Objects.equals(this.generes, that.generes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseYear, rating, generes);
    }

    @Override
    public String toString() {
        return "SearchQuery[" +
                "releaseYear=" + releaseYear + ", " +
                "rating=" + rating + ", " +
                "generes=" + generes + ']';
    }

}
