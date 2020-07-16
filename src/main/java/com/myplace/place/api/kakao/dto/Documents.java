package com.myplace.place.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(value = NON_NULL)
public class Documents {
    String id;
    String place_name;
    String category_name;
    String category_group_code;
    String category_group_name;
    String phone;
    String address_name;
    String road_address_name;
    Double x;
    Double y;
    String place_url;
    String distance;
}
