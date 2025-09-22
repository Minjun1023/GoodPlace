package com.example.GoodPlace.controller;

import com.example.GoodPlace.config.auth.LoginUser;
import com.example.GoodPlace.domain.user.dto.SessionUser;
import com.example.GoodPlace.domain.store.dto.StoreListResponseDto;
import com.example.GoodPlace.domain.store.dto.StoreSaveRequestDto;
import com.example.GoodPlace.domain.store.dto.StoreUpdateRequestDto;
import com.example.GoodPlace.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreApiController {

    private final StoreService storeService;

    // 맛집 등록 API
    @PostMapping("/api/v1/stores")
    public ResponseEntity<Long> saveStore(@RequestBody StoreSaveRequestDto requestDto, @LoginUser SessionUser user) {
        if (user == null || user.getEmail() == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(requestDto, user.getEmail()));
    }

    // 내가 등록한 맛집 조회 API
    @GetMapping("/api/v1/stores")
    public ResponseEntity<List<StoreListResponseDto>> getStores(@LoginUser SessionUser user) {
            if (user == null || user.getEmail() == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
            return ResponseEntity.ok(storeService.findAllByUser(user.getEmail()));
    }

    // 맛집 상세 조회
    @GetMapping("/api/v1/stores/{id}")
    public ResponseEntity<StoreListResponseDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    // 맛집 정보 업데이트
    @PutMapping("/api/v1/stores/{id}")
    public ResponseEntity<Long> updateStore(@PathVariable Long id, @RequestBody StoreUpdateRequestDto requestDto, @LoginUser SessionUser user) {
        if (user == null || user.getEmail() == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        return ResponseEntity.ok(storeService.update(id, requestDto, user.getEmail()));
    }

    // 맛집 정보 삭제
    @DeleteMapping("/api/v1/stores/{id}")
    public ResponseEntity<Long> deleteStore(@PathVariable Long id, @LoginUser SessionUser user) {
        if (user == null || user.getEmail() == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        storeService.delete(id, user.getEmail());
        return ResponseEntity.ok(id);
    }
}
