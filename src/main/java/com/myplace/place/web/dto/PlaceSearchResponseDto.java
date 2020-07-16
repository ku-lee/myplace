package com.myplace.place.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myplace.place.api.kakao.dto.Documents;
import com.myplace.place.api.kakao.dto.Meta;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@Getter
@JsonInclude(value = NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceSearchResponseDto {
    private Meta search_info;
    private List<Documents> place_info;
    private int current_page;

    @Builder
    public PlaceSearchResponseDto(Meta search_info, List<Documents>  place_info, int current_page) {
        this.search_info = search_info;
        this.place_info = place_info;
        this.current_page = current_page;
    }
}
