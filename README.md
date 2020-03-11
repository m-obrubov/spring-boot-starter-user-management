# Модуль "Управление пользователями"

Модуль для управления пользователями. Встраивается в приложения на Spring Boot 2+.

## Описание
Выполняет следующие задачи:
* Предоставляет REST API для CRUD-операций с пользователями
* Аудит операций над пользователями
* Роли пользователей задаются через конфигурацию
## API
### Создание пользователя
POST /user
request
* body:
```json
{
  "firstName": "Иван",
  "LastName": "Иванов",
  "patronymic": "Иванович",
  "nickname": "ivanov",
  "role": "USER"
}
```
response:
* status: 201
* body:
```
{
  "guid": "9175bdb9-5e06-4977-88c6-2e81479bcedc",
}
```
### Изменение данных пользователя
PUT /user/{guid}
request:
* path: guid - внешний идентификатор пользователя
* body:
```json
{
  "firstName": "Иван",
  "LastName": "Иванов",
  "patronymic": "Иванович",
  "nickname": "ivanov",
  "role": "USER"
}
```
response status: 200
### Получение данных пользователя
GET /user/{guid}
request:
* path: guid - внешний идентификатор пользователя

response:
* status: 200
* body:
```json
{
  "firstName": "Иван",
  "LastName": "Иванов",
  "patronymic": "Иванович",
  "nickname": "ivanov",
  "role": "USER"
}
```
### Удаление пользователя
DELETE /user/{guid}
request:
* path: guid - внешний идентификатор пользователя

response:
* status: 200

### Получение истории изменений данных пользователя
GET /user/history
request:
* query param: guid
* query param: dateFrom
* query param: dateTo
* query param: author
* query param: operation

response:
* status: 200
* body:
```json
[
  {
    "datetime": "01-01-2000T13:00:00",
    "userGuid": "9175bdb9-5e06-4977-88c6-2e81479bcedc",
    "operation": "CREATED",
    "author": "main-admin",
    "details": "Ok"
  }
]
```
## Пример использования
Добавляем зависимость Maven в pom.xml проекта
```xml
<dependency>
    <groupId>com.github.m-obrubov</groupId>
    <artifactId>spring-boot-starter-user-management</artifactId>
    <version>1.0.0</version>
</dependency>
```

**TODO**
