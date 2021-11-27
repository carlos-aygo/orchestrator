FROM openjdk:11
COPY build/libs/orchestrator-*-all.jar orchestrator.jar
EXPOSE 8080
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar orchestrator.jar