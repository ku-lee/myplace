package com.myplace.place.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@Getter
@JsonInclude(value = NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoSearchLocationResponse {
    private Meta meta;

    private List<Documents> documents;

    public PlaceSearchResponseDto toDto(int currentPage){
        return PlaceSearchResponseDto.builder()
                                        .search_info(meta)
                                        .place_info(documents)
                                        .current_page(currentPage)
                                        .build();
    }
}
