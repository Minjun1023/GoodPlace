package com.example.GoodPlace.dto;

import com.example.GoodPlace.entity.Store;
import lombok.Getter;

@Getter
public class StoreListResponseDto {
    private Long id;
    private String name;
    private String address;

    // Store 엔티티를 받아서 DTO로 변환하는 생성자
    public StoreListResponseDto(Store entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }
}
