# Модуль spring-boot-starter-user-management

Данный модуль предназрачен для задач, когда в проекте необходимо иметь базовую функциональность по 
управлению пользователями. При использовании данного модуля можно не писать boilerplate операции для создания, 
изменения, удаления и просмотра пользователей Вашей системы.

## Возможности

* REST API для CRUD-операций с пользователями
* Набор атрибутов пользователя:

| Атрибут           | Формат    | Nullable  | Описание                                                  |
| ----------------- | --------- | --------- | --------------------------------------------------------- |
| guid              | UUID      | false     | Уникальный идентификатор, первичный ключ                  |
| login             | string    | false     | Логин, уникальный для всех пользователей                  |
| password          | string    | false     | Хешированный пароль                                       |
| nickName          | string    | true      | Имя пользователя, **отдельно от логина**                  |
| email             | string    | true      | Адрес электронной почты                                   |
| role              | string    | false     | Роль пользователя в системе                               |
| createDate        | timestamp | false     | Дата создания пользователя                                |
| createdBy         | string    | false     | Юзернейм того, кто создал пользователя                    |
| lastUpdateDate    | timestamp | false     | Дата последнего изменения данных пользователя             |
| lastUpdatedBy     | string    | false     | Юзернейм того, кто последним изменял данные пользователя  |
| lastLoginDate     | timestamp | true      | Дата последнего успешного входа пользователя в систему    |
| loginFailCount    | integer   | false     | Количество последних подряд неуспешных попыток входа      |
| locked            | boolean   | false     | Признак блокировки пользователя                           |
| temporalPassword  | boolean   | false     | Признак временного пароля                                 |
| deleted           | boolean   | false     | Признак удаления                                          |
 
* Генерация паролей разной сложности - прописные символы, строчные символы, цифры, специальный символы 
(сложность можно задать через конфигурацию)
* Хэширование паролей алгоритмом PBKDF2 с солью SHA1PRNG
* Возможные роли пользователей задаются через конфигурацию, возможна пометка, является ли роль - ролью администратора
* Создание схемы данных возможно путем автогенерации Hibernate или патчем liquibase
* Поддержка Spring Boot автоконфигурации - модуль имеет стандартную конфигурацию из коробки
* Аудит операций при помощи Spring Auditing

## API

[Описание API модуля](./api.yaml) представлено в формате Swagger 2.0. 

## Использование

Так как модуль не размещен в глобальных репозиториях Maven, парад использованием нужно загрузить исходный код и 
собрать модуль локально:

```$sh
git clone https://github.com/m-obrubov/spring-boot-starter-user-management.git
mvn clean install -DskipTests
```

Для использования модуля в Вашем проекте достаточно импортировать Maven артефакт:
```xml
<dependency>
    <groupId>com.github.m-obrubov</groupId>
    <artifactId>spring-boot-starter-user-management</artifactId>
    <version>1.0.0</version>
</dependency>
```
При этом в приложении сразу появятся REST-методы

### Создание схемы БД

ПОрядока создания схемы БД зависит от того, как Вы это делаете в своем приложении. 
Модуль имеет следующие варианты создания схемы: 

* Авто-генерация Hibernate - все необходимые аннотации уже расставлены в классе-сущности
* Патчи liquibase - написан патч [master-changelog.xml](./changelogs/master-changelog.xml)
* Вручную - скрипта нет, всё зависит от типа используемой СУБД

### Конфигурирование

Для более гибкой настройки необходимо в конфигурации системы (`application.yml` или `application.properties`) 
добавить блок `management.user`. Описание настроек модуля:

| Название                          | Возможные варианты                                | Значение по умолчанию     | Описание                              |
| --------------------------------- | ------------------------------------------------- | ------------------------- | ------------------------------------- |
| management.user.password.length   | 0..*                                              | 6                         | Длина генерируемого пароля            |
| management.user.password.strength | `LOWER_CASE`, `UPPER_CASE`, `DIGITS`, `SPECIAL`   | `DIGITS`                  | Сложность генерируемого пароля        |
| management.user.roles             |                                                   |                           | Список возможных ролей пользователей  |
| management.user.roles[].name      | *                                                 |                           | Наименование роли                     |
| management.user.roles[].isAdmin   | `true`, `false`, `null`                           | null                      | Признак администратора                |

Пример находится [тут](./config/application.yml)

## Развитие

Я вижу развитие модуля в следующем:

* Интеграция со Spring Security для возможности управления доступом к различным ресурсам на базе ролей
* Аудит пользователей
* Аутентификация, функция "забыли пароль"
* Валидация email
* Покрытие кода автотестами
* Настройка пайплайнов для автотестов, публикации артефакта в глобальный Maven репозиторий