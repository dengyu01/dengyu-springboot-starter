FROM maven:3.8.4-openjdk-17 as builder
WORKDIR application
COPY pom.xml .
COPY src src
# COPY .kube .kube
# Maven is not required, but build speed is slow.
# COPY mvnw .
# COPY .mvn .mvn
# RUN ./mvnw install -DskipTests

RUN sed -i -e 's/<mirrors>/& <mirror> <id>nexus-aliyun<\/id> \
    <url>http:\/\/maven.aliyun.com\/nexus\/content\/groups\/public<\/url> <mirrorOf>*<\/mirrorOf> <\/mirror>/'  \
    /usr/share/maven/conf/settings.xml
RUN mvn -DskipTests clean package
RUN mv target/*.jar application.jar
# Use layered jars to speed up docker build. Because src, dependencies and .. are separated to build.
RUN java -Djarmode=layertools -jar application.jar extract


FROM openjdk:17.0.2
MAINTAINER hscovo <hscovo@mail.ustc.edu.cn>
WORKDIR application
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# Resolve time zone issues
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
CMD ["--spring.profiles.active=prod"]