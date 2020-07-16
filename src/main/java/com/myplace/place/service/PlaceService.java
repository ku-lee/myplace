package com.myplace.place.service;

import com.myplace.place.api.kakao.dto.KakaoSearchLocationResponse;
import com.myplace.place.domain.Keywords;
import com.myplace.place.web.dto.PlaceSearchRequestDto;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import com.myplace.place.web.dto.PlaceTopKeywordResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final KakaoPlaceService kakaoService;

    private Keywords keywords = new Keywords();

    public PlaceSearchResponseDto search(final PlaceSearchRequestDto request) throws Exception {
        PlaceSearchResponseDto response = null;

        if(request.isCountable()) {
            keywords.increaseCount(request.getKeyword());
        }

        String api = request.getApi();
        switch(api) {
            case "kakao":
            default:
                KakaoSearchLocationResponse kkoResponse = kakaoService.search(request);
                response = kkoResponse.toDto(request.getPage());
        }

        return response;
    }

    public PlaceTopKeywordResponseDto topKeyword(){

        return new PlaceTopKeywordResponseDto(keywords.getTopKeyword());
    }
}
