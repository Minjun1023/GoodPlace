package com.example.GoodPlace.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverSearchResponseDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverSearchItem> items;
}
