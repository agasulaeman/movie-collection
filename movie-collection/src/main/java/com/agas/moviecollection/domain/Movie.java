package com.agas.moviecollection.domain;

import com.agas.moviecollection.domain.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movie_table_collection")
@SuperBuilder
@Getter
@Setter
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false,length = 50)
    private Integer id;

    @Column(name = "title",nullable = false,length = 200)
    private String title;

    @Column(name = "director",nullable = false,length = 100)
    private String director;

    @Column(name = "summary",nullable = false,length = 100)
    private String summary;

    @Column(name = "genres",nullable = false,length = 50)
    private String genres;
}
