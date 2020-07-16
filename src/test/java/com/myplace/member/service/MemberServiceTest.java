package com.myplace.member.service;

import com.myplace.member.domain.Member;
import com.myplace.member.domain.repository.MemberRepository;
import com.myplace.member.exception.InvalidPasswordException;
import com.myplace.member.exception.MemberIdDuplicateException;
import com.myplace.member.exception.NotExistingMemberException;
import com.myplace.member.web.dto.MemberCreateRequestDto;
import com.myplace.member.web.dto.MemberLoginRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService service;

    @Mock
    MemberRepository repository;

    @Mock
    Member member;

    @Nested
    @DisplayName("로그인 테스트")
    class LoginTest {

        @DisplayName("로그인 테스트")
        @Test
        public void login() throws Exception {
            //given
            given(repository.findByMemberId(anyString())).willReturn(Optional.of(new Member("test", "1234")));

            service.login(new MemberLoginRequestDto("test", "1234"));
        }

        @DisplayName("로그인 테스트 : 아이디 누락")
        @Test
        public void login_empty_id() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    service.login(new MemberLoginRequestDto(null, "1234")));
        }

        @DisplayName("로그인 테스트 : 존재하지 않는 id")
        @Test
        public void login_not_existing_id() throws Exception {
            //given
            given(repository.findByMemberId(anyString())).willReturn(Optional.empty());

            assertThatExceptionOfType(NotExistingMemberException.class).isThrownBy(()->
                    service.login(new MemberLoginRequestDto("test", "1234")));

        }

        @DisplayName("로그인 테스트 : 비밀번호 불일치")
        @Test
        public void login_invalid_pw() throws Exception {
            //given
            given(repository.findByMemberId(anyString())).willReturn(Optional.of(new Member("test", "1234")));

            assertThatExceptionOfType(InvalidPasswordException.class).isThrownBy(()->
                    service.login(new MemberLoginRequestDto("test", "1235")));
      }
    }

    @Nested
    @DisplayName("가입 테스트")
    class CreateTest {

        @DisplayName("가입 테스트")
        @Test
        public void crate_test() throws Exception {
            given(repository.findByMemberId(anyString())).willReturn(Optional.empty());

            service.create(new MemberCreateRequestDto("test", "1234"));
        }

        @DisplayName("가입 테스트 : 아이디 누락")
        @Test
        public void crate_test_empty_id() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                        new MemberCreateRequestDto(null, "1234"));
        }

        @DisplayName("가입 테스트 : 비밀번호 누락")
        @Test
        public void crate_test_empty_pw() throws Exception {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new MemberCreateRequestDto("test", null));
        }

        @DisplayName("가입 테스트 : 이미 존재하는 아이디")
        @Test
        public void crate_test_duplicate_id() throws Exception {
            given(repository.findByMemberId(anyString())).willReturn(Optional.of(new Member("test", "1234")));

            assertThatExceptionOfType(MemberIdDuplicateException.class).isThrownBy(()->
                    service.create(new MemberCreateRequestDto("test", "1234")));

        }
    }
}