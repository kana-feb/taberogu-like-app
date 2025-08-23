package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.RestaurantRepository;



@Service
public class RestaurantService {
	   private final RestaurantRepository restaurantRepository;
	    // 依存性の注入（DI）を行う（コンストラクタインジェクション）
	   public RestaurantService(RestaurantRepository restaurantRepository) {
	       this.restaurantRepository = restaurantRepository;
	   }
	
		// すべての店舗をページングされた状態で取得する (リポジトリにもともと用意されているメソッドを呼び出すだけでOKです)
		public Page<Restaurant> findAllRestaurants(Pageable pageable) {
		    return restaurantRepository.findAll(pageable);
		}
	   // 指定されたキーワードを店舗名に含む店舗を、ページングされた状態で取得する (restaurantRepositoryで定義した独自のメソッドを呼び出すだけでOK)
	   public Page<Restaurant> findRestaurantsByNameLike(String keyword, Pageable pageable) {
	       return restaurantRepository.findByName("%" + keyword + "%", pageable);
	   }
	
	   // 指定したidを持つ店舗を取得する (リポジトリにもともと用意されているメソッドを呼び出すだけでOKです)
	   public Optional<Restaurant> findRestaurantById(Integer id) {
	       return restaurantRepository.findById(id);
	   }
	
	   // 店舗のレコード数を取得する (リポジトリにもともと用意されているメソッドを呼び出すだけでOKです)
	   public long countRestaurants() {
	       return restaurantRepository.count();
	   }
	
	   // idが最も大きい店舗を取得する (restaurantRepositoryで定義した独自のメソッドを呼び出すだけでOK)
	   public Restaurant findFirstRestaurantByOrderByIdDesc() {
	       return restaurantRepository.findFirstByOrderByIdDesc();
	   }

	   
	   @Transactional
	   public void createRestaurant(RestaurantRegisterForm restaurantRegisterForm) {
		   Restaurant restaurant = new Restaurant();
	       MultipartFile imageFile = restaurantRegisterForm.getImageFile();
	       
	       if (!imageFile.isEmpty()) { //画像ファイルがある場合
	    	   String imageName = imageFile.getOriginalFilename();//元の名前を取得
	           String hashedImageName = generateNewFileName(imageName);//UUIDを使って重複しない別名を生成する（例: 6f1a3b12-xxxx.jpg）
	           Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);// 保存先パスを指定する（プロジェクトの static/storage/ 配下）
	           copyImageFile(imageFile, filePath);// 実際に画像ファイルを指定した場所にコピーする
	           restaurant.setImage(hashedImageName);// Restaurantエンティティの image フィールドに保存したファイル名をセットする
	       }
	       
	       // フォームの値をエンティティにセット
	       restaurant.setName(restaurantRegisterForm.getName());
	       restaurant.setDescription(restaurantRegisterForm.getDescription());
	       restaurant.setLowestPrice(restaurantRegisterForm.getLowestPrice());
	       restaurant.setHighestPrice(restaurantRegisterForm.getHighestPrice());
	       restaurant.setPostalCode(restaurantRegisterForm.getPostalCode());
	       restaurant.setAddress(restaurantRegisterForm.getAddress());
	       restaurant.setOpeningTime(restaurantRegisterForm.getOpeningTime());
	       restaurant.setClosingTime(restaurantRegisterForm.getClosingTime());
	       restaurant.setSeatingCapacity(restaurantRegisterForm.getSeatingCapacity());

	       restaurantRepository.save(restaurant);

   }

	   //引数で受け取ったRestaurantエンティティの各フィールドの値を更新してください。それ以外はcreateRestaurant()メソッドと同様です。
	   @Transactional
	   public void updateRestaurant(RestaurantEditForm restaurantEditForm, Restaurant restaurant) {
	       MultipartFile imageFile = restaurantEditForm.getImageFile();

	       if (!imageFile.isEmpty()) { //画像ファイルがある場合
	    	   String imageName = imageFile.getOriginalFilename();//元の名前を取得
	           String hashedImageName = generateNewFileName(imageName);//UUIDを使って重複しない別名を生成する（例: 6f1a3b12-xxxx.jpg）
	           Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);// 保存先パスを指定する（プロジェクトの static/storage/ 配下）
	           copyImageFile(imageFile, filePath);// 実際に画像ファイルを指定した場所にコピーする
	           restaurant.setImage(hashedImageName);// Restaurantエンティティの image フィールドに保存したファイル名をセットする
	       }
	       
	       // フォームの値をエンティティにセット	
	       restaurant.setName(restaurantEditForm.getName());
	       restaurant.setDescription(restaurantEditForm.getDescription());
	       restaurant.setLowestPrice(restaurantEditForm.getLowestPrice());
	       restaurant.setHighestPrice(restaurantEditForm.getHighestPrice());
	       restaurant.setPostalCode(restaurantEditForm.getPostalCode());
	       restaurant.setAddress(restaurantEditForm.getAddress());
	       restaurant.setOpeningTime(restaurantEditForm.getOpeningTime());
	       restaurant.setClosingTime(restaurantEditForm.getClosingTime());
	       restaurant.setSeatingCapacity(restaurantEditForm.getSeatingCapacity());

	       //データベースにユーザーを登録します。直前で作ったエンティティを渡すだけで、データベースに新しいデータの追加が可能
	       restaurantRepository.save(restaurant);
	   }

	   @Transactional
	   	//リポジトリにもともと用意されているメソッドを呼び出し、引数で受け取ったRestaurantエンティティを削除
	   public void deleteRestaurant(Restaurant restaurant) {
	       restaurantRepository.delete(restaurant);
	   }

	   // UUIDを使って生成したファイル名を返す
	   public String generateNewFileName(String fileName) {
	       String[] fileNames = fileName.split("\\.");

	       for (int i = 0; i < fileNames.length - 1; i++) {
	           fileNames[i] = UUID.randomUUID().toString();
	       }

	       String hashedFileName = String.join(".", fileNames);

	       return hashedFileName;
	   }

	   // 送信された画像ファイルを保存先にコピーする処理  画像ファイルを指定したファイルにコピーする
	   public void copyImageFile(MultipartFile imageFile, Path filePath) {
	       try {
	           Files.copy(imageFile.getInputStream(), filePath);
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	   }

	   // 価格が正しく設定されているかどうかをチェックする
	   //最高価格が最低価格以上かどうかを比較演算子を使ってチェック
	   public boolean isValidPrices(Integer lowestPrice, Integer highestPrice) {
	       return highestPrice >= lowestPrice;
	   }

	   // 営業時間が正しく設定されているかどうかをチェックする
	   //閉店時間が開店時間よりも後かどうかをLocalTimeクラスのメソッドを使ってチェック
	   public boolean isValidBusinessHours(LocalTime openingTime, LocalTime closingTime) {
	       return closingTime.isAfter(openingTime);
	       //isAfter は、Javaの LocalTime クラスに属するメソッドで、ある時刻が別の時刻より後かどうかを判定するために使われます。
	   }
} 
	   
