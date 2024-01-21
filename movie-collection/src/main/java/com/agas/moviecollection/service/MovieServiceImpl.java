package com.agas.moviecollection.service;

import com.agas.moviecollection.config.Constants;
import com.agas.moviecollection.domain.Movie;
import com.agas.moviecollection.dto.request.MovieRequest;
import com.agas.moviecollection.dto.response.MovieResponse;
import com.agas.moviecollection.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Map<String, Object> getAll() {
        Map<String,Object> result = new HashMap<>();
        List<MovieResponse> listMovie = new ArrayList<>();

        for (Movie dataMovie: movieRepository.findAll()) {
            if (dataMovie.getDeleted().equals(false)) {
                MovieResponse response = convertMovieToResponseDTO(dataMovie);
                listMovie.add(response);
            }
        }
        String message = "";
        if (!listMovie.isEmpty()) {
            message = Constants.success_string;
        } else {
            message = Constants.empty_data_string;
        }

        result.put(Constants.response , HttpStatus.OK);
        result.put("message", message);
        result.put("data", listMovie);
        result.put("total", listMovie.size());
        return result;
    }

    @Override
    public MovieResponse createMovie(MovieRequest movieRequest) {
        log.info("Processing add Movie {} {} => ");
        validateSummaryLength(movieRequest.getSummary());
        Movie movie = Movie.builder()
                .title(movieRequest.getTitle())
                .director(movieRequest.getDirector())
                .summary(movieRequest.getSummary())
                .genres(movieRequest.getGenres())
                .deleted(false)
                .build();
        Movie saveMovie = movieRepository.save(movie);

        MovieResponse response = convertMovieToResponseDTO(saveMovie);
        return response;
    }

    @Override
    public Map<String, Object> updateMovieById(Integer id, MovieRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Movie dataMovie = movieRepository.findByIdTrue(id);
            Movie movieIndex = convertToEntity(request);
            validateSummaryLength(movieIndex.getSummary());

            dataMovie.setTitle(movieIndex.getTitle());
            dataMovie.setDirector(movieIndex.getDirector());
            dataMovie.setSummary(movieIndex.getSummary());
            dataMovie.setGenres(movieIndex.getGenres());

            Movie resultInput = movieRepository.save(dataMovie);

            MovieResponse response =   convertMovieToResponseDTO(resultInput);

            result.put(Constants.response, HttpStatus.OK);
            result.put("message", Constants.success_string);
            result.put("Data", response);
        } catch (Exception e) {
            result.put("error_code", HttpStatus.NOT_FOUND);
            result.put("message", Constants.movie_notfound);
        }
        return result;
    }

    @Override
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Movie data = movieRepository.findByIdTrue(id);
            data.setDeleted(true);

            Movie resultInput = movieRepository.save(data);
            MovieResponse response = convertMovieToResponseDTO(resultInput);
            result.put("Data", response);
            result.put("response", HttpStatus.OK);
            result.put("message", "Data Has been deleted");
        } catch (Exception e) {
            result.put(Constants.response, HttpStatus.OK);
            result.put(Constants.response, HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Override
    public Map<String, Object> findMovieById(Integer id) {
        Map<String,Object> result = new HashMap<>();
        String message = "";
        try {
            Movie movieData = movieRepository.findByIdTrue(id);
            MovieResponse responseMovie = convertMovieToResponseDTO(movieData);
            message = Constants.success_string;
            result.put("message", message);
            result.put(Constants.response, HttpStatus.OK);
            result.put("Data", responseMovie);

        } catch (Exception e) {
            result.put("error_code",HttpStatus.NOT_FOUND);
            result.put("message",Constants.movie_notfound);
        }
        return result;
    }

    @Override
    public Map<String, Object> findMovieByGenres(String genres) {
        Map<String,Object> result = new HashMap<>();
        String message = "";
        try {
            List<Movie> moviesData =  movieRepository.findByGenre(genres);

            if (!moviesData.isEmpty()) {
                List<MovieResponse> responseMovies = new ArrayList<>();
                for (Movie movieData : moviesData) {
                    responseMovies.add(convertMovieToResponseDTO(movieData));
                }

                message = Constants.success_string;
                result.put("message", message);
                result.put(Constants.response, HttpStatus.OK);
                result.put("Data", responseMovies);
            } else {
                result.put("message", Constants.movie_notfound);
                result.put(Constants.response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            result.put("error_code", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("message", Constants.movie_genre_notfound);
        }

        return result;
    }

    @Transactional
    private void validateSummaryLength(String summary) {
        int maxSummaryLength = 100;
        if (summary.length() > maxSummaryLength) {
            throw new IllegalArgumentException("Summary length exceeds the maximum allowed length is 100 character");
        }
    }

    private MovieResponse convertMovieToResponseDTO(Movie movie) {
        MovieResponse dtoResponse = new MovieResponse();
        LocalDateTime localDateCreateTime = movie.getCreatedAt().atZone(ZoneId.of("Asia/Bangkok")).toLocalDateTime();
        LocalDateTime localDateUpdateTime = movie.getModifiedAt().atZone(ZoneId.of("Asia/Bangkok")).toLocalDateTime();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createDate = localDateCreateTime.format(outputFormatter);
        String updateDate = localDateUpdateTime.format(outputFormatter);
        dtoResponse.setId(movie.getId());
        dtoResponse.setTitle(movie.getTitle());
        dtoResponse.setDirector(movie.getDirector());
        dtoResponse.setSummary(movie.getSummary());
        dtoResponse.setGenres(movie.getGenres());
        dtoResponse.setCreatedAt(createDate);
        dtoResponse.setModifiedAt(updateDate);
        return dtoResponse;
    }
    private Movie convertToEntity(MovieRequest movieRequest) {
        return modelMapper.map(movieRequest,Movie.class);
    }
}
