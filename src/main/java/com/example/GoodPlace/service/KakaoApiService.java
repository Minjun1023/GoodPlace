package com.example.GoodPlace.service;

import com.example.GoodPlace.domain.search.dto.KakaoAddressResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoApiService {

    private final WebClient webClient;
    private final String kakaoRestApiKey;

    public KakaoApiService(WebClient.Builder webClientBuilder,
                           @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoRestApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com").build();
        this.kakaoRestApiKey = kakaoRestApiKey;
    }

    public Mono<KakaoAddressResponseDto> geocode(String address) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/address.json")
                        .queryParam("query", address)
                        .build())
                .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                .retrieve()
                .bodyToMono(KakaoAddressResponseDto.class);

    }


}
