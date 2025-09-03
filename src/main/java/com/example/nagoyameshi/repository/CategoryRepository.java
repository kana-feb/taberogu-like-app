package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Category;


	public interface CategoryRepository extends JpaRepository<Category, Integer> {

	    // 部分一致（大文字小文字無視）＋ページング ＜＞の中はエンティティ名
		public Page<Category> findByNameLike(String keyword, Pageable pageable);

	    // id が最大のカテゴリを1件取得
	    public Category findFirstByOrderByIdDesc();
	    
	    //指定したカテゴリ名を持つ最初のカテゴリを取得するメソッド
	    public Category findFirstByName(String name);
	}
	
