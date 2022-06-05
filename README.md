# Getting Started with CurrencyGif

Создать сервис, который обращается к сервису курсов валют, и отдает gif в ответ:
+ если курс по отношению к рублю за сегодня стал выше вчерашнего,
+ то отдаем рандомную отсюда https://giphy.com/search/rich
+ если ниже - отсюда https://giphy.com/search/broke
 Ссылки
+ REST API курсов валют - https://docs.openexchangerates.org/
+ REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
 Must Have
+ Сервис на Spring Boot 2 + Java / Kotlin
+ Запросы приходят на HTTP endpoint, туда передается код валюты
+ Для взаимодействия с внешними сервисами используется Feign
+ Все параметры (валюта по отношению к которой смотрится курс,
+ адреса внешних сервисов и т.д.) вынесены в настройки
+ На сервис написаны тесты
+ (для мока внешних сервисов можно использовать @mockbean или WireMock)
+ Для сборки должен использоваться Gradle
+ Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
 Nice to Have
+ Сборка и запуск Docker контейнера с этим сервисом
  
Must change these parameters for successful launch:
---
```
giphy.api.key=enter_your_api_key_here
```
```
openexchangerates.app.id=enter_your_app_id_here
```
---
Endpoints:
---  
Получить список кодов для валют:
```
GET /api/codes/all
```  
Получить гифку  
(пример ответа: https://api.giphy.com/v1/gifs/random?api_key=enter_your_api_key_here&tag=rich):
```
GET /api/gif/{Char Code}
```  
Простой html+js доступен по localhost:8080/

Сборка и запуск .jar:
---
```
./gradlew build
```
```
java -jar build/libs/currency-gif-0.0.1-SNAPSHOT.jar
```
Docker:
---
Для создания образа Docker перейти в корневую директорию проекта, 
где лежит фаил Dockerfile: