FROM java:8
VOLUME /tmp
#COPY target/*.jar app.jar
# The application's jar file
# Add the application's jar to the container
ARG JAR_FILE=build/libs/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} myplace.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myplace.jar"]