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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.projectlombok:lombok:1.18.34")

    implementation("org.springframework:spring-webmvc:6.2.1")
    implementation("org.springframework:spring-jdbc:6.2.1")

    implementation("org.postgresql:postgresql:42.7.2")

    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")

}

tasks.test {
    useJUnitPlatform()
}