package com.example.GoodPlace.service;

import com.example.GoodPlace.Repository.StoreRepository;
import com.example.GoodPlace.Repository.UserRepository;
import com.example.GoodPlace.dto.StoreListResponseDto;
import com.example.GoodPlace.dto.StoreSaveRequestDto;
import com.example.GoodPlace.dto.StoreUpdateRequestDto;
import com.example.GoodPlace.entity.Store;
import com.example.GoodPlace.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(StoreSaveRequestDto requestDto, String userEmail) {
        // 이메일을 통해 사용자 정보를 찾음
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + userEmail));

        // Store 엔티티 생성
        Store store = Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .user(user) // 사용자 정보 연결
                .build();

        // 데이터베이스에 저장, 생성된 가게의 ID를 반환
        return storeRepository.save(store).getId();
    }

    @Transactional(readOnly = true) // 조회 기능 -> readOnly = true 으로 성능 향상
    public List<StoreListResponseDto> findAllByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + userEmail));

        return storeRepository.findAllByUserOrderByIdDesc(user)
                .stream()
                .map(StoreListResponseDto::new) // 각 Store 엔티티를 StoreListResponseDto로 변환
                .collect(Collectors.toList());
    }

    // 맛집 정보 업데이트
    @Transactional
    public Long update(Long id, StoreUpdateRequestDto requestDto, String userEmail) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다. id=" + id));

        // 현재 로그인한 사용자가 가게의 소유자인지 확인
        if (!store.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("해당 가게를 수정할 권한이 없습니다.");
        }

        store.update(requestDto.getName(), requestDto.getAddress());

        return id;
    }

    // 맛집 정보 삭제
    @Transactional
    public void delete(Long id, String userEmail) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다. id=" + id));

        // 현재 로그인한 사용자가 가게의 소유자인지 확인
        if (!store.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("해당 가게를 수정할 권한이 없습니다.");
        }

        storeRepository.delete(store);
    }

    // 맛집 상세 조회
    @Transactional(readOnly = true)
    public StoreListResponseDto findById(Long id) {
        Store entity = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 가게가 없습니다. id=" + id)));

        return new StoreListResponseDto(entity);
    }
}
