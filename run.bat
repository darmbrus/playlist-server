ECHO REMOVING BUILD OLD FILES
CALL gradlew clean

ECHO BUILDING JAR
CALL gradlew build

ECHO COPYING FILES
CALL copy application.properties \run
CALL copy secure.properties \run
CALL copy build\libs\spi* \run

ECHO RUNNING PROJECT
CALL java -jar C:\run\spi-0.1.0-SNAPSHOT.jar