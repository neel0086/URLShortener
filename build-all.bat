@echo off
set services=UrlShortener UrlRedirection ApiGateway ServiceRegistry

for %%s in (%services%) do (
    echo Building %%s...
    cd %%s
    call mvn clean package 
    cd ..
)
