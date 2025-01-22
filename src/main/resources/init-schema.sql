-- brand 테이블 생성
CREATE TABLE IF NOT EXISTS brand
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- product 테이블 생성
CREATE TABLE IF NOT EXISTS product
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand_id BIGINT         NOT NULL,
    category VARCHAR(50)    NOT NULL,
    name     VARCHAR(100)   NOT NULL,
    price    DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brand (id)
);

