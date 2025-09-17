package com.example.GoodPlace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 가게 이름

    @Column(nullable = false)
    private String address; // 가게 주소

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 이 맛집을 등록한 사용자

    @Builder
    public Store(String name, String address, User user) {
        this.name = name;
        this.address = address;
        this.user = user;
    }

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
