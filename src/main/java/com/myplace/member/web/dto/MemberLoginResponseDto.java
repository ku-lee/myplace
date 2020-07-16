package com.myplace.member.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myplace.member.domain.Member;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(value = NON_NULL)
public class MemberLoginResponseDto {
    private final String code;

    private final String message;

    @Builder
    public MemberLoginResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MemberLoginResponseDto make(Member member, String code, String message){
        return MemberLoginResponseDto.builder()
                                        .code(code)
                                        .message(message)
                                        .build();
    }
}
