package com.myplace.member.service;

import com.myplace.member.domain.Member;
import com.myplace.member.domain.repository.MemberRepository;
import com.myplace.member.exception.InvalidPasswordException;
import com.myplace.member.exception.MemberIdDuplicateException;
import com.myplace.member.exception.NotExistingMemberException;
import com.myplace.member.web.dto.MemberCreateRequestDto;
import com.myplace.member.web.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository repository;

    @Transactional
    public void login(final MemberLoginRequestDto request) throws Exception {
        Member member = repository.findByMemberId(request.getMemberId())
                .orElseThrow(NotExistingMemberException::new);;

        if(!member.passwordValiate(request.getPassword())){
            throw new InvalidPasswordException();
        }
    }

    @Transactional
    public void create(final MemberCreateRequestDto request) throws Exception {
        Member member = request.toEntity();

        if(repository.findByMemberId(member.getMemberId()).isPresent()){
            throw new MemberIdDuplicateException();
        }

        repository.save(member);
    }
}
