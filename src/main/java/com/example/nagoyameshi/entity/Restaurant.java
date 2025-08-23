package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "restaurants") // テーブル名
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 主キー

    @Column(name = "name", nullable = false)
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt; // 作成日時

    @Column(name = "updated_at")
    private Timestamp updatedAt; // 更新日時
}
