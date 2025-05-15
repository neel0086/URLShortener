UrlRedirectiona@echo off

echo Building ApiGateway
cd UrlShortener
call mvn clean package -DskipTests
cd ..