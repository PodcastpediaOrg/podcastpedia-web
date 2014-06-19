podcastpedia-web
================

Web application backing the [Podcastpedia.org](http://www.podcastpedia.org) website

## Install and run the Podcastpedia.org website on your local machine

### Prerequisites
* MySQL 5.5 or 5.6 
* IDE ( preffered Eclipse 4.3+) 
* JDK 1.7 (if you want to use Jetty 9 with the jetty-maven-plugin from project)
* Maven 3.*
* Tomcat 7+ to be able to run the application on the Tomcat server
* install [podcastpedia-parent](https://github.com/podcastpedia/podcastpedia-parent) and [podcastpedia-common](https://github.com/podcastpedia/podcastpedia-common) in your local Maven repository

> Note: The application can be easily run from Jetty, with the help of jetty-maven-plugin which is configured in the [pom.xml](https://github.com/podcastpedia/podcastpedia-web/blob/master/pom.xml)

### Install the project
1. download/clone the project 
2. prepare the database as described in the [README.md](https://github.com/podcastpedia/podcastpedia-sql) of the podcastpedia-sql project
3. change to the root folder of the project and excute the following maven command 

```
mvn clean install -DskipTests=true
```

### Run the project
The easiest you can start the project with Jetty with the help of jetty-maven-plugin, by issuing the following command on the command line in the root directory:

```
mvn jetty:run -Djetty.port=8080
```
and then access the Podcastpedia website at http//localhost:8080

## License

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
