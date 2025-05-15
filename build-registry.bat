@echo off

echo Building ServiceRegistry
cd UrlShortener
call mvn clean package -DskipTests
cd ..