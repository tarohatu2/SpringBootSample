FROM eclipse-temurin:21-jdk as build
WORKDIR /app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN ./gradlew build -x test
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)

# FROM eclipse-temurin:21-jdk
FROM ghcr.io/graalvm/jdk-community:21
# FROM --platform=linux/arm64/v8 ghcr.io/graalvm/native-image-community:21-muslib
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs/dependency
COPY --from=build /app/build/libs/*.jar app.jar
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.sampleapp.demo.DemoApplication"]
ENTRYPOINT ["java", "-jar", "app.jar", "-Xlog:gc=debug", "-XX:+PrintFlagsFinal","-Xms2g", "-Xmx2g", "-verbose:gc"]

EXPOSE 8080