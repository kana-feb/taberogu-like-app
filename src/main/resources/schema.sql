CREATE TABLE IF NOT EXISTS roles (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   furigana VARCHAR(50) NOT NULL,
   postal_code VARCHAR(50) NOT NULL,
   address VARCHAR(255) NOT NULL,
   phone_number VARCHAR(50) NOT NULL,
   birthday DATE,
   occupation VARCHAR(50),
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   role_id INT NOT NULL,
   enabled BOOLEAN NOT NULL,
   stripe_customer_id VARCHAR(255) UNIQUE,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS verification_tokens(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL UNIQUE,
	token  VARCHAR(255)	NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)	
);

--店舗用のテーブル
CREATE TABLE IF NOT EXISTS restaurants (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,      -- 主キー、自動採番
    name VARCHAR(50) NOT NULL,                       -- 店舗名（必須）
    image VARCHAR(255),                              -- 店舗画像（任意）
    description TEXT NOT NULL,                       -- 説明（必須）
    lowest_price INT NOT NULL,                       -- 最低価格（必須）
    highest_price INT NOT NULL,                      -- 最高価格（必須）
    postal_code VARCHAR(50) NOT NULL,                -- 郵便番号（必須）
    address VARCHAR(255) NOT NULL,                   -- 住所（必須）
    opening_time TIME NOT NULL,                      -- 開店時間（必須）
    closing_time TIME NOT NULL,                      -- 閉店時間（必須）
    seating_capacity INT NOT NULL,                   -- 座席数（必須）
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 作成日時（必須、デフォルト現在時刻）
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新日時（必須、自動更新）
);

