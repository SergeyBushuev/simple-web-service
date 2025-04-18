# simple-web-service
Простое приложение веб-блога

## Установка

Сборка с помощью
* `mvn clean package`
* `.\mvnw.cmd clean build`

Отдельный запуск тестов
* `mvn test`
* `.\mvnw.cmd test`

После собранный `.jar` готов к запуску. 
Запуск: `java -jar BootBlogApp-0.0.1-SNAPSHOT.jar`

Послу успешного запуска приложения перейти по адресу `http://localhost:8080/posts`

## API

* `get /posts` главная страница с постами
* `get /posts/{id}` страница поста
* `get /posts/{id}/edit` форма редактирования поста
* `get /posts/add` страница добавления поста
* `post /posts/{id}/like` лайк поста
* `post /posts` создание нового поста
* `post /posts/{id}` обновление поста
* `post /posts/{id}/delete` удаление поста
* `post /posts/{id}/comments` добавление комментария к посту
* `post /posts/{id}/comments/{commentId}` обновление комментария к посту
* `post /posts/{id}/comments/{commentId}/delete` удаление комментария к посту
* `get /images/{id}` изображения на странице поста 
