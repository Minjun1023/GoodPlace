package com.example.GoodPlace.service;

import ch.qos.logback.core.rolling.helper.MonoTypedConverter;
import com.example.GoodPlace.dto.NaverSearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NaverApiService {

    private final WebClient webClient;
    private final String naverClientId;
    private final String naverClientSecret;

    // application.yml에 있는 네이버 API 키 값을 주입받음
    public NaverApiService(WebClient.Builder webClientBuilder,
                           @Value("${spring.security.oauth2.client.registration.naver.client-id}") String naverClinetId,
                           @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String naverClientSecret) {
        this.webClient = webClientBuilder.baseUrl("https://openapi.naver.com").build();
        this.naverClientId = naverClinetId;
        this.naverClientSecret = naverClientSecret;
    }

    public Mono<NaverSearchResponseDto> searchLocal(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/local.json")
                        .queryParam("query", query)
                        .queryParam("display", 5)
                        .build())
                .header("X-Naver-Client-Id", naverClientId)
                .header("X-Naver-Client-Secret", naverClientSecret)
                .retrieve()
                .bodyToMono(NaverSearchResponseDto.class);
    }

}
