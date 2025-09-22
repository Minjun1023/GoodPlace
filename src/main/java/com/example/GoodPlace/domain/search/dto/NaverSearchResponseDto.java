package com.example.GoodPlace.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverSearchResponseDto {
    private String lastBuildDate;   // 검색 결과 생성한 시간
    private int total;  // 총 검색 결과 개수
    private int start;  // 검색 시작 위치
    private int display;    // 한 번에 표시할 검색 결과 개수
    private List<NaverSearchItem> items;    // 실제 검색 결과 리스트
}
