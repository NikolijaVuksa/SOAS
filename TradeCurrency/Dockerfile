FROM openjdk:17
EXPOSE 8600
ADD target/trade-currency-1.0.0.jar /app/trade-currency.jar
ENTRYPOINT [ "java", "-jar", "/app/trade-currency.jar" ]