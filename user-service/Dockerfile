#stage 1
#Start with a base image containing Java runtime
FROM openjdk:17-alpine as build

# Add Maintainer Info
LABEL maintainer="bekhterev"

COPY mvnw .
# Set maven and maven-wrapper from .mvn to prevent version conflicts
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Set permissions to execute mvnw commands
RUN chmod +x mvnw

# Package project
RUN ./mvnw -B package

# Unpackage jar file
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#stage 2
#Same Java runtime
FROM openjdk:17-alpine

#Add volume pointing to /tmp
VOLUME /tmp

#Copy unpackaged application to new container
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#Execute the application
ENTRYPOINT ["java","-cp","app:app/lib/*","ru.bekhterev.userservice.UserServiceApplication"]