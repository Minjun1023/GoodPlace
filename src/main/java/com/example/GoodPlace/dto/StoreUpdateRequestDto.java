package com.example.GoodPlace.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreUpdateRequestDto {
    private String name;
    private String address;

    @Builder
    public StoreUpdateRequestDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
