plugins {
	java
	id("org.springframework.boot") version "3.3.6"
	id("io.spring.dependency-management") version "1.1.6"
	id("gg.jte.gradle") version "3.1.12"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Основные зависимости
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-web-services")

	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	implementation("jakarta.validation:jakarta.validation-api:3.0.0")
	implementation("gg.jte:jte:3.1.12")
	implementation("gg.jte:jte-spring-boot-starter-3:3.1.12")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

	implementation ("org.yaml:snakeyaml:1.29")
	implementation("com.github.javafaker:javafaker:1.0.2")

	implementation("com.h2database:h2:2.2.220")
	implementation("postgresql:postgresql:9.1-901-1.jdbc4")

	// Компиляционные зависимости
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Тестовые зависимости
	compileOnly("org.projectlombok:lombok:1.18.36")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	"developmentOnly"("org.springframework.boot:spring-boot-devtools")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


jte {
	generate()
	binaryStaticContent = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}
