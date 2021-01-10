FROM openjdk:11-jdk-slim as builder
WORKDIR /app-src
ADD build.gradle gradlew settings.gradle ./
ADD ./gradle ./gradle
ADD ./src ./src
RUN ./gradlew --version
RUN ./gradlew build --no-daemon

FROM openjdk:11-jre-slim
#ENV MAIN_CLASS_NAME=com.github.anishitani.dsrouting.Application
ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /usr/local/bin/tini
RUN chmod +x /usr/local/bin/tini && mkdir /app
COPY --from=builder /app-src/build/libs/*.jar /app/dsrouting.jar
ENTRYPOINT ["tini", "--"]
CMD["java", "-jar", "/app/dsrouting.jar"]