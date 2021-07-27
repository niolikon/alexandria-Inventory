FROM openjdk:latest
COPY target/Inventory-0.0.0-A06.jar /usr/src/
WORKDIR /usr/src/
CMD ["java", "-jar", "Inventory-0.0.0-A06.jar"]
