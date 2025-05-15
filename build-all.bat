@echo off
set services=UrlShortener UrlRedirection ApiGateway ServiceRegistry UrlAnalytics

for %%s in (%services%) do (
    echo Building %%s...
    cd %%s
    call mvn clean package -DskipTests
    cd ..
)
