FROM openjdk:11

ADD build/libs/TradingService.jar tradingapp.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=aws","-jar","tradingapp.jar" ]
EXPOSE 8080
