package io.java.essentials.storage;

import io.java.essentials.processor.MovieProcessor;

public interface MovieStorage {
    void pollMovieRecords(MovieProcessor rocessor);
}
