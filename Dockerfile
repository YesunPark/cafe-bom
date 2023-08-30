FROM openjdk:11-jre-slim
COPY build/libs/CafeBom-0.0.1-SNAPSHOT.jar CafeBom-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-jar" ,"CafeBom-0.0.1-SNAPSHOT.jar"]