package com.example.GoodPlace.Repository;

import com.example.GoodPlace.entity.Store;
import com.example.GoodPlace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 특정 사용자가 등록한 모든 맛집을 정렬 (최신순)
    List<Store> findAllByUserOrderByIdDesc(User user);
}
