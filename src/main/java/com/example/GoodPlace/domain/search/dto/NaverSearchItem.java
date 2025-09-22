package com.example.GoodPlace.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverSearchItem {
    private String title;   // 업체, 기관의 이름
    private String link;    // 업체, 기관의 상세 정보 URL
    private String category;    // 업체, 기관의 분류 정보
    private String description; // 업체, 기관에 대한 설명
    private String telephone;   // 전화번호
    private String address;     // 업체, 기관의 지번 주소
    private String roadAddress; // 업체, 기관의 도로명 주소
    private int mapx;   // x 좌표 값(경도, longitude)
    private int mapy;   // y 좌표 값(위도, latitude)
}
