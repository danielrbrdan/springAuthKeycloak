Este é um projeto de exemplo que demonstra o login com autenticação e autorização com Keycloak em uma aplicação Spring Boot e como utilizar uma entidade JPA chamada Test para interagir com um banco de dados com suas devidas autorizações.

Inicialização:
Realize as configurações do clientId e clientSecret do keycloack
$ mvn clean package -DskipTests
$ docker-compose up --build
