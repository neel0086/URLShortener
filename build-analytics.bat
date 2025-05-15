@echo off

echo Building UrlAnalytics
cd UrlAnalytics
call mvn clean package -DskipTests
cd ..

