package com.myplace.member.web.dto;

import com.myplace.member.domain.Member;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class MemberLoginRequestDto {
    private final String memberId;

    private final String password;

    public MemberLoginRequestDto(String memberId, String password) {
        if(StringUtils.isEmpty(memberId)) throw new IllegalArgumentException("memberId is empty");
        if(StringUtils.isEmpty(password)) throw new IllegalArgumentException("password is empty");

        this.memberId = memberId;
        this.password = password;
    }

    public Member toEntity() throws Exception {
        return Member.builder()
                .memberId(memberId)
                .plainPassword(password)
                .build();
    }
}
