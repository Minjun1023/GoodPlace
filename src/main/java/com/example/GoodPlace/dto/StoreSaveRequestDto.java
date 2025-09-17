package com.example.GoodPlace.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreSaveRequestDto {
    private String name;
    private String address;

    @Builder
    public StoreSaveRequestDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
