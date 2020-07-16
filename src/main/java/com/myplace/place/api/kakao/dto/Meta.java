package com.myplace.place.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(value = NON_NULL)
public class Meta {
    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
    private RegionInfo same_name;
    private Integer size;
    private String sort;

    @Getter
    @JsonInclude(value = NON_NULL)
    public class RegionInfo{
        String[] region;
        String keyword;
        String selected_region;
    }
}
