# Wordpress-utils

This is a personal project to manage wordpress website. 

## Objectives

* **Export / import** content using `wp-cli` WXR format
* Export blog content into a custom format (pivot) for integration on other platforms
* Render text content and pictures in a text/PDF format
* Be able to re-construct a wordpress site based on the backup


## Composition

This project is composed of many parts:
* PHP and SHELL SCRIPTS to be executed on the server to extract / import data. I rely on the [wp cli][https://github.com/wp-cli/wp-cli/] 
* Java part to parse and generate WP-CLI XML data export

## Backup/restore tool

This is the backup/restore tool (_work in progress_).

### Requirements

You need:
* [Apache maven 3.8.1](https://maven.apache.org/download.cgi)
* [Graal VM 21.2](https://www.graalvm.org/)

Environments variables:
* `JAVA_HOME` must point to GraalVM
* `GRAALVM_HOME` must point to GraalVM too

### Compilation

```shell script
# Built-in tool
./mvnw clean install 
# Old way
mvn clean install
```

Make sure the "mvnw" utility works. You'll need it for the rest of the operations.


Unit tests use **H2** (embedded database).
* By default content is in memory only
* You can write a file on disk if you prefer. Checkout the configuration in `src/main/resources/application.configuration`


### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

The DEV mode uses **MySQL** server. Checkout the configuration in `src/main/resources/application.configuration`

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/

* Default landing page: http://localhost:8080
* Run the _Authors_ web-service: http://localhost:8080/wordpress/wxr/getAuthors
* Development UI: http://localhost:8080/q/dev/

(i) Might be dumb dumb: don't forget to close other connections before starting.


### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.
It will start a VertX server and listen on 8080 port automatically.

