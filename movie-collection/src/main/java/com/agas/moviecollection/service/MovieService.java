package com.agas.moviecollection.service;

import com.agas.moviecollection.dto.request.MovieRequest;
import com.agas.moviecollection.dto.response.MovieResponse;

import java.util.Map;

public interface MovieService {

    Map<String,Object> getAll();
    MovieResponse createMovie(MovieRequest movieRequest);
    Map<String,Object> updateMovieById (Integer id,MovieRequest request);
    Map<String, Object> delete(Integer id);
    Map<String,Object> findMovieById(Integer id);
    Map<String,Object> findMovieByGenres(String  genres);
    Map<String,Object> findMovieByTittle(String title);

}
