package com.example.GoodPlace.domain.store.dto;

import com.example.GoodPlace.domain.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreListResponseDto {
    private Long id;    // 맛집 고유 ID
    private String name;    // 맛집 이름
    private String address; // 맛집 주소
    private Double latitude;    // 위도
    private Double longitude;   // 경도

    // Store 엔티티를 받아서 DTO로 변환하는 생성자
    public StoreListResponseDto(Store entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
