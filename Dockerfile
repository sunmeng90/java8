FROM java:8

VOLUME /tmp

COPY target/java-0.0.1-SNAPSHOT.jar java.jar

RUN bash -c "touch java.jar"

ENTRYPOINT ["java", "-cp", "java.jar", "org.meng.java.thread.pool.ThreadPoolDemo"]