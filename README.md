# Tennis Scoreboard - Java Web Application

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
* **Controller** - `MatchScoreServlet`, `NewMatchServlet`, `MatchesServlet`
* **Service** - `MatchScoreCalculationService`, `OngoingMatchesService`, `FinishedMatchesPersistenceService` и др.

---

## Скриншоты приложения

<img width="1479" height="797" alt="image" src="https://github.com/user-attachments/assets/7edcc377-9de8-49a8-bd07-05dc5a5fd92a" />

---

<img width="1441" height="879" alt="image" src="https://github.com/user-attachments/assets/ff710011-360a-4d20-a2d0-db2f49663618" />

&nbsp;
&nbsp;

---

<img width="1450" height="905" alt="image" src="https://github.com/user-attachments/assets/07fbd825-0147-4de2-9f18-9d8b886d0919" />

---

<img width="1514" height="849" alt="image" src="https://github.com/user-attachments/assets/b2b1eeec-04a9-4bc1-bf6d-c8d536bc5e8a" />




