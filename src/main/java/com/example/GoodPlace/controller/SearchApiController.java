package com.example.GoodPlace.controller;

import com.example.GoodPlace.domain.search.dto.NaverSearchResponseDto;
import com.example.GoodPlace.service.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class SearchApiController {

    private final NaverApiService naverApiService;

    @GetMapping("/api/v1/search")
    public Mono<ResponseEntity<NaverSearchResponseDto>> search(@RequestParam String query) {
        return naverApiService.searchLocal(query)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
