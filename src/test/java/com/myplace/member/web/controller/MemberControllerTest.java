package com.myplace.member.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myplace.member.service.MemberService;
import com.myplace.member.web.dto.MemberCreateRequestDto;
import com.myplace.member.web.dto.MemberLoginRequestDto;
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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import(HttpEncodingAutoConfiguration.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Nested
    @DisplayName("가입테스트")
    public class createTest{
        @DisplayName("가입 테스트 (정상)")
        @Test
        public void test_create() throws Exception {
            String content = mapper.writeValueAsString(new MemberCreateRequestDto("test", "1234"));
            mvc.perform(post("/v1/api/member/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
                    .andExpect(status().isOk());

        }

        @DisplayName("가입 테스트 : id 누락")
        @Test
        public void test_create_empty_id() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->new MemberCreateRequestDto( null, "1234"));
        }

        @DisplayName("가입 테스트 : pw 누락")
        @Test
        public void test_create_empty_pw() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->new MemberCreateRequestDto("test", null));
        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    public class LoginTest {
        @DisplayName("로그인 테스트")
        @Test
        public void test_login() throws Exception {
            //given);
            String content = mapper.writeValueAsString(new MemberLoginRequestDto("test", "1234"));
            mvc.perform(post("/v1/api/member/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
                    .andExpect(status().isOk());
        }

        @DisplayName("로그인 테스트 : id 누락")
        @Test
        public void test_login_empty_id() throws Exception {

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new MemberLoginRequestDto(null, "1234"));
        }

        @DisplayName("로그인 테스트 : pw 누락")
        @Test
        public void test_login_empty_pw() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new MemberLoginRequestDto("test", null));
        }
    }
}