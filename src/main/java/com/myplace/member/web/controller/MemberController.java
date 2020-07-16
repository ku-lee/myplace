package com.myplace.member.web.controller;

import com.myplace.member.service.MemberService;
import com.myplace.member.web.dto.MemberCreateRequestDto;
import com.myplace.member.web.dto.MemberCreateResponseDto;
import com.myplace.member.web.dto.MemberLoginRequestDto;
import com.myplace.member.web.dto.MemberLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto request) throws Exception{
        MemberLoginResponseDto response = MemberLoginResponseDto.builder()
                                                                    .code("0000")
                                                                    .build();

        memberService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<MemberCreateResponseDto> create(@RequestBody MemberCreateRequestDto request) throws Exception{
        MemberCreateResponseDto response = MemberCreateResponseDto.builder()
                .code("0000")
                .build ();

        memberService.create(request);

        return ResponseEntity.ok(response);
    }
}
