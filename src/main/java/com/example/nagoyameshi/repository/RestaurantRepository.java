package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    // 店舗名に部分一致（大文字小文字無視）＋ページング　＜＞の中はエンティティ名
	public Page<Restaurant> findByName(String keyword, Pageable pageable);

    // id が最大の店舗を1件取得
	public Restaurant findFirstByOrderByIdDesc();
    
    //すべての店舗を作成日時が新しい順に並べ替え、ページングされた状態で取得するメソッド
    public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
