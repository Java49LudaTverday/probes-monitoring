FROM eclipse-temurin:17-jdk-alpine
COPY ./target/gateway-0.0.1.jar app.jar
EXPOSE 8090
ENV CONFIG_SERVER_HOST=config-server
ENV ACCOUNTS_PROVIDER_HOST=accounts-provider
ENTRYPOINT [ "java", "-jar", "app.jar" ]
