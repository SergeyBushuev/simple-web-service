plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework:spring-webmvc:6.2.1")
    implementation("org.springframework:spring-jdbc:6.2.1")

    implementation("org.postgresql:postgresql")

    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")

}

tasks.test {
    useJUnitPlatform()
}