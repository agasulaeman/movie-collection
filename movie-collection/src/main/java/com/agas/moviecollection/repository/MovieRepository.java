package com.agas.moviecollection.repository;

import com.agas.moviecollection.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    @Query(value="SELECT * FROM movie_table_collection mt "
            + "WHERE mt.id = :id "
            + "AND mt.is_deleted = false", nativeQuery= true)
    Movie findByIdTrue(@Param("id") Integer id);

    @Query(value="SELECT * FROM movie_table_collection mt "
            + "WHERE mt.genres = :genres "
            + "AND mt.is_deleted = false", nativeQuery= true)
    List<Movie> findByGenre(@Param("genres") String genres);

    @Query(value="SELECT * FROM movie_table_collection mt "
            + "WHERE mt.title = :title "
            + "AND mt.is_deleted = false", nativeQuery= true)
    List<Movie> findByTitle(@Param("title") String title);
}
