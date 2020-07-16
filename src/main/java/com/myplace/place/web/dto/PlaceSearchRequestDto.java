package com.myplace.place.web.dto;

import lombok.Getter;

@Getter
public class PlaceSearchRequestDto {
    private String keyword;
    private String api;
    private int page;
    private boolean isCountable;

    public PlaceSearchRequestDto(String keyword, String api, String page, String increase) {
        this.keyword = keyword;
        this.api = api;
        this.page = Integer.parseInt(page);
        this.isCountable = "true".equals(increase);
    }

}
