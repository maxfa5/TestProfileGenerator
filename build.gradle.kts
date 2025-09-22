plugins {
    id("java")
    id ("org.springframework.boot") version "3.2.4"
    id ("io.spring.dependency-management") version "1.1.4"
}

group = "org.project"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    implementation ("com.opencsv:opencsv:5.9")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-core:5.2.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")

    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:spring-mock-mvc:5.3.0")
}



tasks.test {
    useJUnitPlatform()
}