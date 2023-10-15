   FROM ubuntu:latest AS build

   RUN apt-get update
   RUN apt-get install openjdk-17-jdk -y

   COPY . .

   RUN apt-get install maven -y
   RUN mvn install

   FROM openjdk:17-jdk-slim
   EXPOSE 8080

   COPY --from=build /out/artifacts/TodoList.jar app.jar

   ENTRYPOINT ["java", "-jar", "app.jar"]
