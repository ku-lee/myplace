package com.myplace.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "memberId", nullable = false)
    private String memberId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cryptPassword", column = @Column(name = "password")),
            @AttributeOverride(name = "salt", column = @Column(name = "salt"))
    })
    private CryptPassword password;

    @Builder
    public Member(String memberId, String plainPassword) throws Exception{
        if(StringUtils.isEmpty(memberId)) throw new IllegalArgumentException("memberId is Empty");
        if(StringUtils.isEmpty(plainPassword)) throw new IllegalArgumentException("password is Empty");

        this.memberId = memberId;
        this.password = new CryptPassword(plainPassword);
    }

    public boolean passwordValiate(String plainPassword) throws Exception{
        if(StringUtils.isEmpty(plainPassword)) throw new IllegalArgumentException("password is Empty");

        return password.validate(plainPassword);
    }
}
