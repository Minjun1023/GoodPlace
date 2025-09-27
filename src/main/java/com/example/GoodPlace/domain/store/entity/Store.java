package com.example.GoodPlace.domain.store.entity;

import com.example.GoodPlace.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "store", uniqueConstraints = {
        @UniqueConstraint(
                name = "user_store_unique", columnNames = {"user_id", "name", "address"}
        )
})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 맛집 고유 ID

    @Column(nullable = false)
    private String name; // 가게 이름

    @Column(nullable = false)
    private String address; // 가게 주소

    private Double latitude;    // 위도
    private Double longitude;   // 경도

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 이 맛집을 등록한 사용자

    @Builder
    public Store(String name, String address, User user, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
