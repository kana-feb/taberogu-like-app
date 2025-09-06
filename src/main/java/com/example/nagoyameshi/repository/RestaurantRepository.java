package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Restaurant;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    // 店舗名に部分一致（大文字小文字無視）＋ページング　＜＞の中はエンティティ名
	public Page<Restaurant> findByName(String keyword, Pageable pageable);

    // id が最大の店舗を1件取得
	public Restaurant findFirstByOrderByIdDesc();
    
    //★すべての店舗を作成日時が新しい順に並べ替え、ページングされた状態で取得するメソッド
    public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);

    
    //すべての店舗を最低価格が安い順に並べ替え、ページングされた状態で取得する
    public Page<Restaurant> findAllByOrderByLowestPriceAsc(Pageable pageable);
    
    //指定されたキーワードを店舗名または住所またはカテゴリ名に含む店舗を"作成日時が新しい順"に並べ替え、ページングされた状態で取得する
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN r.categoriesRestaurants cr " +
            "WHERE r.name LIKE %:name% " +
            "OR r.address LIKE %:address% " +
            "OR cr.category.name LIKE %:categoryName% " +
            "ORDER BY r.createdAt DESC")
    public Page<Restaurant> findByNameLikeOrAddressLikeOrCategoryNameLikeOrderByCreatedAtDesc(@Param("name") String nameKeyword,
            @Param("address") String addressKeyword,
            @Param("categoryName") String categoryNameKeyword,
            Pageable pageable);

    // 指定されたキーワードを店舗名または住所またはカテゴリ名に含む店舗を"最低価格"が安い順に並べ替え、ページングされた状態で取得する
    @Query("SELECT DISTINCT r FROM Restaurant r " +
           "LEFT JOIN r.categoriesRestaurants cr " +
           "WHERE r.name LIKE %:name% " +
           "OR r.address LIKE %:address% " +
           "OR cr.category.name LIKE %:categoryName% " +
           "ORDER BY r.lowestPrice ASC")
    public Page<Restaurant> findByNameLikeOrAddressLikeOrCategoryNameLikeOrderByLowestPriceAsc(@Param("name") String nameKeyword,
            @Param("address") String addressKeyword,
            @Param("categoryName") String categoryNameKeyword,
            Pageable pageable);

       
    // 指定されたidのカテゴリが設定された店舗を作成日時が新しい順に並べ替え、ページングされた状態で取得する
    @Query("SELECT r FROM Restaurant r " +
           "INNER JOIN r.categoriesRestaurants cr " +
           "WHERE cr.category.id = :categoryId " +
           "ORDER BY r.createdAt DESC")
    public Page<Restaurant> findByCategoryIdOrderByCreatedAtDesc(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 指定されたidのカテゴリが設定された店舗を最低価格が安い順に並べ替え、ページングされた状態で取得する
    @Query("SELECT r FROM Restaurant r " +
           "INNER JOIN r.categoriesRestaurants cr " +
           "WHERE cr.category.id = :categoryId " +
           "ORDER BY r.lowestPrice ASC")
    public Page<Restaurant> findByCategoryIdOrderByLowestPriceAsc(@Param("categoryId") Integer categoryId, Pageable pageable);
    
    //指定された最低価格以下の店舗を作成日時が新しい順に並べ替え、ページングされた状態で取得する
    public Page<Restaurant> findByLowestPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
    
    //指定された最低価格以下の店舗を最低価格が安い順に並べ替え、ページングされた状態で取得する
    public Page<Restaurant> findByLowestPriceLessThanEqualOrderByLowestPriceAsc(Integer price, Pageable pageable);    

}
