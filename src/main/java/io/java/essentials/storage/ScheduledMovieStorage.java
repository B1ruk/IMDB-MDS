package io.java.essentials.storage;

import io.java.essentials.processor.MovieProcessor;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//main

public class ScheduledMovieStorage implements MovieStorage {
    private static Logger LOGGER = Logger.getLogger(ScheduledMovieStorage.class.getSimpleName());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    @Override
    public void pollMovieRecords(MovieProcessor processor) {
        var thread = new Thread(() -> {
            processor.loadMovies();
            System.out.println("Loaded movies at " + Thread.currentThread().getName());
        });

        thread.start();
        thread.run();

        //using executor service
        var executor = Executors.newFixedThreadPool(5);
        executor.submit(() -> {
            processor.loadMovies();
            System.out.println("Loaded movies using executor service " + Thread.currentThread().getName());
        });


//Chaining CompletableFuture to load and process movie records asynchronously
        CompletableFuture.supplyAsync(() -> processor.loadMovies())
                .thenApply(movieRecord -> {
                    LOGGER.info("Processing movie records using compltableFuture" + Thread.currentThread().getName());
                    return movieRecord.stream()
                            .limit(10)
                            .toList();
                })
                .thenAccept(movies -> {
                    LOGGER.info("CompletableFuture thenAccept" + Thread.currentThread().getName());
                    LOGGER.info("Loaded " + movies.size() + " movies");
                })
                .exceptionally(ex -> {
                    LOGGER.log(Level.SEVERE, "Failed to load movies", ex);
                    return null;
                })
                .join();


        //Using scheduled executor service to poll movie records every 5 seconds
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            LOGGER.log(Level.INFO, "processing movie records {0}", LocalDateTime.now());
            LOGGER.log(Level.INFO, "processing movie records {0}", Thread.currentThread().getName());
            processor.loadMovies();
        }, 0, 5, TimeUnit.SECONDS);
    }
}
