@echo off

echo Building UrlRedirection
cd UrlShortener
call mvn clean package -DskipTests
cd ..