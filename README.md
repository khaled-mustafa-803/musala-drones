# musala-drones
 Prerequisites installation: Java 11, Maven

     Build command: mvn clean install
     Run command: mvn spring-boot:run
     Test command: mvn clean test
     Sonar qube analysis command: mvn clean compile sonar:sonar -Dsonar.login={sonar_admin_token}


Note that the application uses in-memory database(H2)