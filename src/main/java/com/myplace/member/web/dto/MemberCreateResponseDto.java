package com.myplace.member.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myplace.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(value = NON_NULL)
public class MemberCreateResponseDto {
    private final String code;

    private final String message;

    @Builder
    public MemberCreateResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MemberCreateResponseDto make(Member member, String code, String message){
        return MemberCreateResponseDto.builder()
                                        .code(code)
                                        .message(message)
                                        .build();
    }
}
