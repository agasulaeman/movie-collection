package com.agas.moviecollection.controller;

import com.agas.moviecollection.dto.request.MovieRequest;
import com.agas.moviecollection.dto.response.MovieResponse;
import com.agas.moviecollection.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;

    @PostMapping("/create")
    public MovieResponse add(@RequestBody MovieRequest request) {
        return movieService.createMovie(request);
    }

    @PutMapping(value = "/update/{id}")
    public Map<String, Object> update(@PathVariable(value = "id") Integer id, @RequestBody MovieRequest request) {
        return movieService.updateMovieById(id, request);
    }

    @GetMapping("/getAll")
    public Map<String, Object> getAll() {
        return movieService.getAll();
    }

    @GetMapping("/findById/{id}")
    public Map<String, Object> findMovieById(@RequestParam("id") Integer id) {
        return movieService.findMovieById(id);
    }

    @GetMapping("/findByGenre/{genres}")
    public Map<String, Object> findMovieByGenres(@RequestParam("genres") String genres) {
        return movieService.findMovieByGenres(genres);
    }
    @GetMapping("/findByTitle/{title}")
    public Map<String, Object> findMovieByTitles(@RequestParam("title") String title) {
        return movieService.findMovieByTittle(title);
    }

    @DeleteMapping("delete/{id}")
    public Map<String, Object> delete(@RequestParam("id") Integer id) {
        return movieService.delete(id);
    }
}
