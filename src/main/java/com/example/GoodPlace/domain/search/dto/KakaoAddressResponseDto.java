package com.example.GoodPlace.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoAddressResponseDto {
    private List<KakaoAddressDocumentDto> documents;
}
