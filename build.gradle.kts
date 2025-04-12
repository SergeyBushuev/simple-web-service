plugins {
    id("java")
    id("war")
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.34")

    implementation("org.springframework:spring-webmvc:6.2.1")
    implementation("org.springframework:spring-jdbc:6.2.1")

    implementation("org.postgresql:postgresql:42.7.2")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")

    testImplementation("org.springframework:spring-test:6.2.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("com.h2database:h2:2.2.224")


}

tasks.test {
    useJUnitPlatform()
}