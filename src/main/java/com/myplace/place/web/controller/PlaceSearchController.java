package com.myplace.place.web.controller;

import com.myplace.place.service.PlaceService;
import com.myplace.place.web.dto.PlaceSearchRequestDto;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import com.myplace.place.web.dto.PlaceTopKeywordResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/place")
public class PlaceSearchController {

    private final PlaceService placeService;

    @GetMapping("/search")
    public ResponseEntity<PlaceSearchResponseDto> search(@RequestParam("keyword") String keyword,
                                                         @RequestParam("api") String api,
                                                         @RequestParam(value = "page", defaultValue = "1") String page,
                                                         @RequestParam(value = "increase", defaultValue = "false") String increase ) throws Exception {
        log.info("searh request [{}/{}/{}/{}]",
                keyword, api, page, increase);

        PlaceSearchResponseDto response = placeService.search(new PlaceSearchRequestDto(keyword, api, page, increase));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/keywords")
    public ResponseEntity<PlaceTopKeywordResponseDto> keywords(@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        PlaceTopKeywordResponseDto response = placeService.topKeyword();

        return ResponseEntity.ok(response);
    }
}
