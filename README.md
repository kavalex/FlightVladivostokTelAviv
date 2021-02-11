# FlightVladivostokTelAviv
Консольная программа которая парсит json c данными перелетов и высчитывает среднее арифметическое время перелета и 90-й процентиль

Стек технологий: Maven, Jackson

## Инструкция по сборке и запуску приложения

Получение, сборка и запуск приложения происходит в терминале, порядок действий:

1. Создать директорию для проекта перейти в нее:
```console
mkdir another_project && cd another_project
```
2. Скопировать проект на локальный диск и перейти в корневую директорию проекта:
```console
git clone https://github.com/kavalex/FlightVladivostokTelAviv.git && cd FlightVladivostokTelAviv
```
3. Собрать проект:
```console
mvn install
```
4. Запустить приложение:
```console
mvn exec:java -Dexec.mainClass="FlightVladivostokTelAviv" -Dexec.args="tickets.json"
```

Вывод программы
```console
Перелет Владивосток - Тель-Авив
-------------------------------
1. Cреднее время: 452.0 минут
2. 90-й процентиль: 9.0
-------------------------------
```
