@echo off

echo Building UrlShortener
cd UrlShortener
call mvn clean package -DskipTests
cd ..

