# ReadMate

## Описание
ReadMate — это веб-приложение, предназначенное для читателей, желающих упорядочить свои записи о прочитанных книгах. Проект позволяет создавать дневник чтения, добавлять книги и оставлять записи о своих впечатлениях.

## Функциональность
- Регистрация и авторизация с использованием JWT (на основе RSA ключей).
- Добавление новых книг.
- Просмотр списка добавленных книг.
- Оставление записей и оценок для книг.

## Технологический стек
- **Язык программирования:** Java 21
- **Фреймворк для бэкенда:** Spring Boot
- **Аутентификация и авторизация:** Spring Security, JWT (с RSA)
- **База данных:** PostgreSQL (в продакшне), H2 (в режиме разработки)
- **Отображение шаблонов:** JTE
- **Управление зависимостями:** Gradle
- **Тестирование:** JUnit 5, Mockito
- **Деплой:** Render.com
- **Контейнеризация:** Docker

## Структура проекта
```
ReadMate/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── app.ReadMate/         # Пакеты с кодом
│   │   │       ├── config/          # Конфигурация приложения
│   │   │       ├── controller/      # Контроллеры
│   │   │       ├── dto/             # DTO классы
│   │   │       ├── mapper/          # Мапперы данных
│   │   │       ├── model/           # Модели данных
│   │   │       ├── repository/      # Репозитории для взаимодействия с БД
│   │   │       ├── service/         # Логика приложения
│   │   │       └── util/            # Утилитарные классы
│   │   ├── resources/
│   │       ├── certs/               # Сертификаты
│   │       ├── static.css           # Стили CSS
│   │       └── templates/           # Шаблоны JTE
│   └── test/                         # Тесты
├── build.gradle                      # Файл сборки Gradle
├── Dockerfile                        # Dockerfile для создания контейнера
└── README.md                         # Текущий файл
```

## Установка и запуск
1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/username/ReadMate.git
   ```
2. Сборка проекта:
   ```bash
   ./gradlew build
   ```
3. Запуск Docker контейнера:
   ```bash
   docker-compose up
   ```

## Планы на развитие
- Реализовать поддержку ролей (администратор, стандартный пользователь).
- Добавить графики и статистику по чтению.
- Реализовать удобную навигацию и поиск по рецензиям.

