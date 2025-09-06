package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.RestaurantService;

@Controller // クラスの頭に付ける
@RequestMapping("/restaurants")
public class RestaurantController {

    // ==================== フィールド ====================
    //private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService; // 依存性注入用のサービス
    private final CategoryService categoryService;
    //private final CategoryRestaurantService categoryRestaurantService;    
    //private final RegularHolidayService regularHolidayService;
    //private final RegularHolidayRestaurantService regularHolidayRestaurantService;    


    // ==================== コンストラクタ ====================
    // コンストラクタで依存性注入
    
    public RestaurantController(RestaurantService restaurantService,
            CategoryService categoryService)
    {        
        this.restaurantService = restaurantService;
    	this.categoryService = categoryService;
     }
	
    
    //@RequestParam：フォームから送られたキーワードを受け取る
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,//検索ボックスに入力した文字
                        @RequestParam(name = "categoryId", required = false) Integer categoryId,
                        @RequestParam(name = "price", required = false) Integer price,
                        @RequestParam(name = "order", required = false) String order,//並び順
                        @PageableDefault(page = 0, size = 15, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model)//HTML（画面）に値を渡すための箱(デフォルト値）

    {
    Page<Restaurant> restaurantPage;
    
    //キーワード検索
    if (keyword != null && !keyword.isEmpty()) {
        if (order != null && order.equals("lowestPriceAsc")) {
            restaurantPage = restaurantService.findRestaurantsByNameLikeOrAddressLikeOrCategoryNameLikeOrderByLowestPriceAsc(keyword, keyword, keyword, pageable);
        } else {
            restaurantPage = restaurantService.findRestaurantsByNameLikeOrAddressLikeOrCategoryNameLikeOrderByCreatedAtDesc(keyword, keyword, keyword, pageable);
        }
    }
    
    //カテゴリ検索
    else if (categoryId != null) {
        if (order != null && order.equals("lowestPriceAsc")) {
            restaurantPage = restaurantService.findRestaurantsByCategoryIdOrderByLowestPriceAsc(categoryId, pageable);
        } else {
            restaurantPage = restaurantService.findRestaurantsByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
        }
    }
    //価格検索
    else if (price != null) {
        if (order != null && order.equals("lowestPriceAsc")) {
            restaurantPage = restaurantService.findRestaurantsByLowestPriceLessThanEqualOrderByLowestPriceAsc(price, pageable);
        } else {
            restaurantPage = restaurantService.findRestaurantsByLowestPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
        }
    }
    //条件なし（全件）
    else {
        if (order != null && order.equals("lowestPriceAsc")) {
            restaurantPage = restaurantService.findAllRestaurantsByOrderByLowestPriceAsc(pageable);
        } else {
            restaurantPage = restaurantService.findAllRestaurantsByOrderByCreatedAtDesc(pageable);
        }
    }
    
    List<Category> categories = categoryService.findAllCategories();
    model.addAttribute("restaurantPage", restaurantPage);
    model.addAttribute("categories", categories);
    model.addAttribute("keyword", keyword);
    model.addAttribute("categoryId", categoryId);
    model.addAttribute("price", price);
    model.addAttribute("order", order);

    return "restaurants/index";


}
}

