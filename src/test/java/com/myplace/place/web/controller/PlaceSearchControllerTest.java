package com.myplace.place.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myplace.place.domain.Keyword;
import com.myplace.place.exception.PlaceAPIException;
import com.myplace.place.service.PlaceService;
import com.myplace.place.web.dto.PlaceSearchRequestDto;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import com.myplace.place.web.dto.PlaceTopKeywordResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceSearchController.class)
@Import(HttpEncodingAutoConfiguration.class)
public class PlaceSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceService placeService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Nested
    @DisplayName("검색 테스트")
    public class LoginTest {
        @DisplayName("검색 테스트")
        @Test
        public void test_search() throws Exception {
            PlaceSearchResponseDto r = makeResponse();

            //given
            given(placeService.search(any(PlaceSearchRequestDto.class))).willReturn(makeResponse());

            mvc.perform(get("/v1/api/place/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("keyword", "test")
                    .param("api", "kakao")
            ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.place_info").exists())
                    .andExpect(jsonPath("$.search_info").exists());
        }

        @DisplayName("검색 테스트 : 키워드 누락")
        @Test
        public void test_search_empty_keyword() throws Exception {
            //given
            given(placeService.search(any(PlaceSearchRequestDto.class))).willReturn(makeResponse());

            mvc.perform(get("/v1/api/place/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("api", "kakao")
            ).andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("검색 테스트 : api exception 발생")
        @Test
        public void test_search_api_exception() throws Exception {
            //given
            given(placeService.search(any(PlaceSearchRequestDto.class))).willThrow(PlaceAPIException.class);

            mvc.perform(get("/v1/api/place/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("keyword", "test")
                    .param("api", "kakao")
            ).andDo(print())
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("인기 검색어 테스트")
    public class KeywordTest {
        @DisplayName("검색 테스트")
        @Test
        public void test_search() throws Exception {
            given(placeService.topKeyword()).willReturn(makeKeywordResponse());
            mvc.perform(get("/v1/api/place/keywords")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.keywords").exists())
                    .andExpect(jsonPath("$.keywords[0].keyword").isString())
                    .andExpect(jsonPath("$.keywords[0].hits").isNumber());
        }
    }

    private PlaceSearchResponseDto makeResponse() throws JsonProcessingException {
        String result = "{\"search_info\":{\"total_count\":446,\"pageable_count\":45,\"is_end\":true,\"same_name\":{\"region\":[],\"keyword\":\"카카오\",\"selected_region\":\"\"}},\"place_info\":[{\"id\":\"1044004404\",\"place_name\":\"카카오프렌즈 롯데영플라자명동점\",\"category_name\":\"가정,생활 > 문구,사무용품 > 디자인문구 > 카카오프렌즈\",\"category_group_code\":\"\",\"category_group_name\":\"\",\"phone\":\"02-2118-5150\",\"address_name\":\"서울 중구 남대문로2가 123\",\"road_address_name\":\"서울 중구 남대문로 67\",\"x\":126.981682046205,\"y\":37.5635252637636,\"place_url\":\"http://place.map.kakao.com/1044004404\",\"distance\":\"\"}]}";

        return mapper.readValue(result, PlaceSearchResponseDto.class);
    }

    private PlaceTopKeywordResponseDto makeKeywordResponse() throws JsonProcessingException {
        return new PlaceTopKeywordResponseDto(Arrays.asList(new Keyword("kkb",1)));
//        String result = "{\"keywords\":[{\"keyword\":\"카카오\",\"hits\":1}]}";
//        return mapper.readValue(result, PlaceTopKeywordResponseDto.class);
    }

}
