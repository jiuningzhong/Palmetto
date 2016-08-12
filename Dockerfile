FROM tomcat:8.0.36-jre8

RUN apt-get update
RUN apt-get install -y maven
RUN apt-get install -y openjdk-8-jdk

ADD src /usr/src/palmetto/src
ADD LICENSE /usr/src/palmetto/LICENSE
ADD pom.xml /usr/src/palmetto/pom.xml
ADD README.md /usr/src/palmetto/README.md
WORKDIR /usr/src/palmetto

RUN mvn clean package
RUN cp /usr/src/palmetto/target/palmetto-webapp.war /usr/local/tomcat/webapps/palmetto-webapp.war

#Add tomcat-users.xml if needed to have access to tomcat admin interface
#ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
