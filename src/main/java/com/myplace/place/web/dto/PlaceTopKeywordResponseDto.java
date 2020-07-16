package com.myplace.place.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myplace.place.domain.Keyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(value = NON_NULL)
public class PlaceTopKeywordResponseDto {
    private final List<Keyword> keywords;

    @Builder
    public PlaceTopKeywordResponseDto(List<Keyword> keywords) {
        this.keywords = keywords;
    }
}
