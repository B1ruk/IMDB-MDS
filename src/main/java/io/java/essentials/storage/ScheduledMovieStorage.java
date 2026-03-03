package io.java.essentials.storage;

import io.java.essentials.model.Movie;
import io.java.essentials.processor.MovieProcessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduledMovieStorage implements MovieStorage {
    private static Logger LOGGER = Logger.getLogger(ScheduledMovieStorage.class.getSimpleName());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void pollMovieRecords(MovieProcessor processor) {
        CompletableFuture.supplyAsync(processor::loadMovies)
                .thenAccept(movies -> LOGGER.info("Loaded " + movies.size() + " movies"))
                .exceptionally(ex -> {
                    LOGGER.log(Level.SEVERE, "Failed to load movies", ex);
                    return null;
                })
                        .join();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            LOGGER.log(Level.INFO, "processing movie records {0}", LocalDateTime.now());
            processor.loadMovies();
        }, 5, 3, TimeUnit.SECONDS);
    }
}
