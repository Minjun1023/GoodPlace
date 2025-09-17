package com.example.GoodPlace.controller;

import com.example.GoodPlace.Repository.StoreRepository;
import com.example.GoodPlace.dto.SessionUser;
import com.example.GoodPlace.dto.StoreListResponseDto;
import com.example.GoodPlace.dto.StoreSaveRequestDto;
import com.example.GoodPlace.dto.StoreUpdateRequestDto;
import com.example.GoodPlace.entity.Store;
import com.example.GoodPlace.service.StoreService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreApiController {

    private final StoreService storeService;
    private final HttpSession httpSession;
    private final StoreRepository storeRepository;

    // 맛집 등록 API
    @PostMapping("/api/v1/stores")
    public ResponseEntity<Long> saveStore(@RequestBody StoreSaveRequestDto requestDto) {
        // 현재 로그인된 사용자 정보를 세션에서 가져옴
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user == null) {
            // 로그인되어 있지 않으면 권한 없음(401) 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long storeId = storeService.save(requestDto, user.getEmail());

        // 성공적으로 생성되었음을 알리는 201 Created 상태 코드와 함께 가게 ID를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(storeId);
    }

    // 내가 등록한 맛집 조회 API
    @GetMapping("/api/v1/stores")
    public ResponseEntity<List<StoreListResponseDto>> getStores() {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<StoreListResponseDto> stores = storeService.findAllByUser(user.getEmail());

            return ResponseEntity.ok(stores);
    }

    // 맛집 정보 업데이트
    @PutMapping("/api/v1/stores/{id}")
    public ResponseEntity<Long> updateStore(@PathVariable Long id, @RequestBody StoreUpdateRequestDto requestDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 서비스 메소드에 사용자 이메일 전달
        storeService.update(id, requestDto, user.getEmail());
        return ResponseEntity.ok(id);
    }

    // 맛집 정보 삭제
    @DeleteMapping("/api/v1/stores/{id}")
    public ResponseEntity<Long> deleteStore(@PathVariable Long id) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 서비스 메소드에 사용자 이메일 전달
        storeService.delete(id, user.getEmail());
        return ResponseEntity.ok(id);
    }

    // 맛집 상세 조회
    @GetMapping("/api/v1/stores/{id}")
    public ResponseEntity<StoreListResponseDto> getStoreById(@PathVariable Long id) {
        StoreListResponseDto responseDto = storeService.findById(id);
        return ResponseEntity.ok(responseDto);
    }
}
