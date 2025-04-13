# simple-web-service
Простое приложение веб-блога

## Установка

Сборка с помощью 
* `gradle clean build`
* `.\gradlew.bat clean build`

Отдельный запуск тестов
* `gradle test`
* `.\gradlew.bat test`

После собранный `.war` положить в рабочий сервлет-контейнер Tomcat в директорию `webapps`
Перейти по адресу `*tomca*/simple-web-service-1.0-SNAPSHOT/posts`

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

