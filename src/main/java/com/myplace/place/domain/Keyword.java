package com.myplace.place.domain;

import lombok.Getter;

@Getter
public class Keyword {
    private String keyword;
    private int hits;

    public Keyword(String keyword, int hits) {
        this.keyword = keyword;
        this.hits = hits;
    }
}
