package com.example.GoodPlace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverSearchItem {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;
    private int mapx;
    private int mapy;

}
