package io.java.essentials.model;

import java.util.List;

public record SearchQuery(String releaseYear, Double rating, List<String> generes) {

    @Override
    public String toString() {
        return "SearchQuery[" +
                "releaseYear=" + releaseYear + ", " +
                "rating=" + rating + ", " +
                "generes=" + generes + ']';
    }

}
