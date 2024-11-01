plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.luizalabs.logistica"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    // Testcontainers core
    testImplementation ("org.testcontainers:testcontainers:1.19.0")
    // Testcontainers para JUnit 5
    testImplementation ("org.testcontainers:junit-jupiter:1.19.0")
    // Testcontainers para PostgreSQL
    testImplementation ("org.testcontainers:postgresql:1.19.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
