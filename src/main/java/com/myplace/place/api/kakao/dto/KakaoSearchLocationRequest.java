package com.myplace.place.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(value = NON_NULL)
public class KakaoSearchLocationRequest {
    private String query;
    private String category_group_code;
    private Double x;
    private Double y;
    private Integer radius;
    private String rect;
    private Integer page;
    private Integer size;
    private String sort;

    @Builder
    public KakaoSearchLocationRequest(String query, String category_group_code, Double x, Double y, Integer radius, String rect, Integer page, Integer size, String sort) {
        this.query = query;
        this.category_group_code = category_group_code;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rect = rect;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
