package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "restaurants") // テーブル名
@Data
@ToString(exclude = "categoriesRestaurants")

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 主キー

    @Column(name = "name", nullable = false)//NULLを禁止する
    private String name; // 店舗名

    @Column(name = "image")
    private String image; // 店舗画像ファイル名

    @Column(name = "description", nullable = false)
    private String description; // 説明

    @Column(name = "lowest_price", nullable = false)
    private Integer lowestPrice; // 最低価格

    @Column(name = "highest_price", nullable = false)
    private Integer highestPrice; // 最高価格

    @Column(name = "postal_code", nullable = false)
    private String postalCode; // 郵便番号

    @Column(name = "address", nullable = false)
    private String address; // 住所

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime; // 開店時間

    @Column(name = "closing_time")
    private LocalTime closingTime; // 閉店時間

    @Column(name = "seating_capacity")
    private Integer seatingCapacity; // 座席数

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
    
    
    //カテゴリとのリレーション　カスケード
    //mappedBy:外部キーは CategoryRestaurant エンティティの restaurant フィールドにある
    //FetchType.EAGER :関連エンティティを即座にロードします
    
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id ASC")//デフォルトの並び順を「idが小さい順（昇順）」に設定する   
    private List<CategoryRestaurant> categoriesRestaurants; //List<相手のエンティティのクラス型>型でフィールドを定義
    
    // 定休日とのリレーション
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id ASC") 
    private List<RegularHolidayRestaurant> regularHolidaysRestaurants;

    
}
