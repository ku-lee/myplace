package com.myplace.place.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myplace.common.api.APIService;
import com.myplace.place.api.kakao.dto.KakaoSearchLocationResponse;
import com.myplace.place.exception.PlaceAPIException;
import com.myplace.place.web.dto.PlaceSearchRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPlaceService {
    private final APIService<String> api;

    @Value("${search.api.kakao:http://dapi.kakao.com/v2/local/search/keyword.json}")
    private String url ;

    @Value("${search.api.kakao.app.key:KakaoAK 8780a40fd8418574a7b0ad17b614a3b2}")
    private String app_key;

    private static final ObjectMapper mapper = new ObjectMapper();

    public KakaoSearchLocationResponse search(final PlaceSearchRequestDto request) throws Exception {
        ResponseEntity<String> response = api.get(makeQueryUrl(request), headers(), String.class);

        if(response.getStatusCode() != HttpStatus.OK) throw new PlaceAPIException();
        KakaoSearchLocationResponse kkoResponse = mapper.readValue(response.getBody(), KakaoSearchLocationResponse.class);

        return kkoResponse;
    }

    private String makeQueryUrl(PlaceSearchRequestDto request){
        return String.format("%s?query=%s&page=%s", url, request.getKeyword(), request.getPage());
    }

    private HttpHeaders headers(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", app_key);

        return httpHeaders;
    }
}
