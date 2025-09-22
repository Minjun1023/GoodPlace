package com.example.GoodPlace.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreSaveRequestDto {
    private String name;    // 등록할 맛집 이름
    private String address; // 등록할 맛집 주소

    @Builder
    public StoreSaveRequestDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
