# 1. 프로젝트 설명
- 스파르타 코딩 클럽의 "백엔드 개발자를 위한 과제 테스트 대비반" - API 유형 기출문제1 풀이
 
# 2. 프로젝트 설치 방법
- JDK
    - Version: jdk-21.0.6+7
    - Vender: Eclipse Temurin
    - Download: https://adoptium.net/temurin/releases/

- Spring Boot
  - Version: 3.4.2

- Database
    - Name: H2DB 

- Build Tools
    - Gradle
        - Version: 8.10

# 3. API 목록 및 사용 방법
## API File: CUSTOMER-API-TEST.http
```http request
GET http://localhost:9999/api/product/v1/lowest-products-by-category
```http request
GET http://localhost:9999/api/product/v1/lowest-price-by-brand
```
```http request
GET http://localhost:9999/api/product/v2/lowest-price-by-brand
```
```http request
GET http://localhost:9999/api/product/v1/min-max-price-by-category?category=바지
```


## API File: ADMIN-API-TEST.http
```http request
POST http://localhost:9999/api/admin/v1/create/brand
Content-Type: application/json

{
"brand_name": "J"
}
```

```http request
POST http://localhost:9999/api/admin/v1/create/product
Content-Type: application/json

{
"brand_name": "J",
"category": "상의",
"product_name": "상의",
"price": 10000
}
```

```http request
POST http://localhost:9999/api/admin/v2/modify/product
Content-Type: application/json

{
"product_id": 73,
"brand_name": "J",
"category": "바지",
"product_name": "바지",
"price": 500
}
```

 