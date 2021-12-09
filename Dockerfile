FROM openjdk
WORKDIR passport
ADD target/passport-2.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar
