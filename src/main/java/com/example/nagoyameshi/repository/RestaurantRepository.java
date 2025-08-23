package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    // 店舗名に部分一致（大文字小文字無視）＋ページング
    Page<Restaurant> findByName(String name, Pageable pageable);

    // id が最大の店舗を1件取得
    Restaurant findFirstByOrderByIdDesc();
}
