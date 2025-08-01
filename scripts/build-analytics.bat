@echo off

echo Building UrlAnalytics
cd ..
cd UrlAnalytics
call mvn clean package -DskipTests
cd ..


