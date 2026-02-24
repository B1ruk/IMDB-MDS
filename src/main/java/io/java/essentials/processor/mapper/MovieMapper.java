package io.java.essentials.processor.mapper;

import io.java.essentials.model.Movie;

public interface MovieMapper {
    Movie map(String rawData);
}
