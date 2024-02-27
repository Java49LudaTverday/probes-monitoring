FROM eclipse-temurin:17-jdk-alpine
COPY ./target/gateway-0.0.1.jar app.jar
EXPOSE 8090
ENV CONFIG_SERVER_HOST=config-server
ENV ACCOUNTS_PROVIDER_HOST=accounts-provider
ENV ADMIN_CONSOLE_PORT=admin-console
ENV EMAIL_PROVIDER_PORT=email-data-provider
ENV RANGE_PROVIDER_PORT=sensor-range-provider
ENTRYPOINT [ "java", "-jar", "app.jar" ]
