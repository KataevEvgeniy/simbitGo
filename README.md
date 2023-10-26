# Инструкция

# GitHub https://github.com/KataevEvgeniy/simbitGo

## 1.Удаленный API и база данных
### API: http://92.63.177.142:8080/api/swagger-ui/#/
### Для подключения в pgAdmin user - postgres pass - postgres db - simbir_go
### DB: http://92.63.177.142:49154/ 

## 2.Локально собрать проект через docker-compose
### 1.mvn package
### 2.docker-compose up --build
### 3.необходимо восстановить базу данных из файла postgres/database_dump.sql имя для базы данных simbir_go по аддресу localhost:49154

## 3.Локально собрать проект
### 1.необходимо восстановить базу данных из файла postgres/database_dump.sql имя для базы данных simbir_go по аддресу localhost:5432
### 2.изменить в application.properties datasource.url с db:5432 на localhost:5432
### 3.mvn package
### 4.cd target
### 5.java -jar simbirGo.jar