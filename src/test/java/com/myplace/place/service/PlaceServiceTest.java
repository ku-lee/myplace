package com.myplace.place.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myplace.place.api.kakao.dto.Documents;
import com.myplace.place.api.kakao.dto.KakaoSearchLocationResponse;
import com.myplace.place.api.kakao.dto.Meta;
import com.myplace.place.domain.Keywords;
import com.myplace.place.web.dto.PlaceSearchRequestDto;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import com.myplace.place.web.dto.PlaceTopKeywordResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @InjectMocks
    PlaceService service;

    @Mock
    KakaoPlaceService kakaoPlaceService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Nested
    @DisplayName("검색 테스트")
    public class SearchTest{

        @DisplayName("검색 테스트")
        @Test
        public void test_search() throws Exception {
            //given
            KakaoSearchLocationResponse kko_response = makeResponse();
            given(kakaoPlaceService.search(any(PlaceSearchRequestDto.class))).willReturn(kko_response);

            //when
            PlaceSearchResponseDto response = service.search(new PlaceSearchRequestDto("test", "kakao", "1", "false"));

            //then
            assertThat(response.getPlace_info()).isNotNull();
            assertThat(response.getSearch_info()).isNotNull();

            verifyAllKakaoResponse(kko_response, response);
        }

        private void verifyAllKakaoResponse(KakaoSearchLocationResponse kko_response, PlaceSearchResponseDto response) {
            List<Documents> kko_documents = kko_response.getDocuments();
            List<Documents> places = response.getPlace_info();

            Meta kko_meta = kko_response.getMeta();
            Meta meta = response.getSearch_info();

            assertAll(() -> assertThat(kko_documents).containsExactlyInAnyOrderElementsOf(places),
                    () -> assertThat(kko_meta.getIs_end()).isEqualTo(meta.getIs_end()),
                    () -> assertThat(kko_meta.getPageable_count()).isEqualTo(meta.getPageable_count()),
                    () -> assertThat(kko_meta.getSame_name()).isEqualTo(meta.getSame_name()),
                    () -> assertThat(kko_meta.getSize()).isEqualTo(meta.getSize()),
                    () -> assertThat(kko_meta.getSort()).isEqualTo(meta.getSort()),
                    () -> assertThat(kko_meta.getTotal_count()).isEqualTo(meta.getTotal_count()));

        }
    }

    @Nested
    @DisplayName("인기 키워드 테스트")
    public class KeywordTest{

        @BeforeEach
        public void init(){
            //ReflectionTestUtils.setField();
        }

        @DisplayName("인기키워드 테스트")
        @Test
        public void test_keyword() throws Exception {
            //given
            Keywords keywords = new Keywords();
            keywords.increaseCount("test1");

            ReflectionTestUtils.setField(service, "keywords", keywords);

            //when
            PlaceTopKeywordResponseDto response = service.topKeyword();

            //then
            assertThat(response.getKeywords()).isNotEmpty();
            assertThat(response.getKeywords().get(0).getKeyword()).isEqualTo("test1");
            assertThat(response.getKeywords().get(0).getHits()).isEqualTo(1);
        }

    }
    private KakaoSearchLocationResponse makeResponse() throws JsonProcessingException {
        String result = "{\"documents\":[{\"address_name\":\"경기 이천시 마장면 이평리 477-7\",\"category_group_code\":\"\",\"category_group_name\":\"\",\"category_name\":\"스포츠,레저 > 스포츠용품 > 스키,보드용품\",\"distance\":\"\",\"id\":\"1584875571\",\"phone\":\"031-637-2829\",\"place_name\":\"AUGMENT SKI Test Center\",\"place_url\":\"http://place.map.kakao.com/1584875571\",\"road_address_name\":\"경기 이천시 마장면 지산로 58\",\"x\":\"127.366573090717\",\"y\":\"37.2188264269501\"}],\"meta\":{\"is_end\":false,\"pageable_count\":34,\"same_name\":{\"keyword\":\"test\",\"region\":[],\"selected_region\":\"\"},\"total_count\":34}}";

        return mapper.readValue(result, KakaoSearchLocationResponse.class);
    }
}