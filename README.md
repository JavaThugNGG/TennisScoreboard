# Tennis Scoreboard - Java Web Application

<img width="1445" height="872" alt="image" src="https://github.com/user-attachments/assets/e44fcb13-a1a1-4166-a39b-ac9e7db4145b" />


## Описание

Java Web-приложение для ведения учёта счёта в теннисных матчах. Приложение реализовано без использования фреймворков (в целях практики MVCS), полностью на Java Servlets, JSP, Hibernate (ORM), H2 (database).

## Функционал

* Создание нового матча
* Учёт текущего счёта
* Сохранение завершённых матчей в БД
* Фильтр и пагинация завершённых матчей

## Стек технологий

* Java 21
* Maven
* Servlet API / JSP
* H2 Database
* Hibernate ORM
* JUnit 5
* HTML/CSS (JSP)
* Tomcat 9+

## Сборка проекта

1. Убедитесь, что у вас установлен Maven и JDK:

```bash
java -version
mvn -version
```

2. Соберите `.war` файл:

```bash
mvn clean package
```

3. Готовый `.war`-файл будет в `/target/*.war`

## Запуск на сервере

### Требуется:

* Удалённый Linux-сервер
* Установленный JRE
* Apache Tomcat 9+

### Шаги:

1. Скопируйте `.war` на сервер (scp/др.):

```bash
scp target/tennis-scoreboard.war user@server_ip:/path/to/tomcat/webapps/
```

2. Перезапустите Tomcat или дождитесь деплоя

3. Приложение будет доступно:

```
http://<server_ip>:8080/<context_path>/
```

## Тесты

* Покрытие MatchScoreCalculationService юнит-тестами (JUnit 5)
* Запуск:

```bash
mvn test
```

## Структура MVCS

* **Model** - `MatchScore`, `Player`, `Match`
* **View** - JSP-шаблоны (HTML+JSP)
* **Controller** - `MatchScoreController`, `NewMatchController`, `MatchesController`
* **Service** - `MatchScoreCalculationService`, `OngoingMatchesService`, `FinishedMatchesPersistenceService`

---

Успешного запуска проекта!
