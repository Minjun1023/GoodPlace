package com.example.GoodPlace.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAddressDocumentDto {
    @JsonProperty("address_name")
    private String addressName; // 전체 주소 문자열
    @JsonProperty("y")
    private String y;   // 위도 (latitude)
    @JsonProperty("x")
    private String x;   // 경도 (longitude)
}
