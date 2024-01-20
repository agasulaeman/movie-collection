package com.agas.moviecollection.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    private Integer id;
    private String title;
    private String director;
    private String summary;
    private String genres;
    private String createdAt;
    private String modifiedAt;
}
