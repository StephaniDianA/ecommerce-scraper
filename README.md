# ecommerce-scraper
A mini project to demonstrate data collection with a web scraping.

The web scraping technique used in this project is HTML parsing with the target url of e-commerce [**Tokopedia**]( https://www.tokopedia.com/p/handphone-tablet/handphone?ob=5&page=) aiming to get a list of the top 100 products from the **Handphone** category. The product list is based on the highest number of reviews, which indicates that the product is widely purchased by consumers.

# How To

1. Clone this repo and run the following command
````
./gradlew clean build
````

2. Run the service
````
./gradlew clean build
````

3. Use any REST Client (Postman etc.) or open on your preferred browser the following HTTP request
`http://localhost:8080/api/scraper/product`

4. Wait until the process is finished and the data will be stored in the root directory of this repo, with name format
`list_top_product_[timestamp].csv`

# Technologies and Libraries
This project uses the following technologies and libraries:
- Spring Boot
- Java
- Gradle
- Jsoup
- SonarLint
