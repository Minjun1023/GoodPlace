package com.example.GoodPlace.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreUpdateRequestDto {
    private String name;    // 수정할 맛집 이름
    private String address; // 수정할 맛집 주소

    @Builder
    public StoreUpdateRequestDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
