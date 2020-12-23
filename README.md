# YAWS - Yet Another Web Shop
Study of web development using Spring Boot with Material Design Lite and many more technologies [see DEPENDENCY_SECTION](#depend) to create a web shop application. It was cloned from [artingo/spring-boot](https://github.com/artingo/spring-boot) on 20-12-2020 and since then developed indepently. Credits go to [Alfred Walther](https://github.com/artingo) for most of the work done. 

## Configuration
### application.properties

    # points to application-dev.properties
    spring.profiles.active=dev

    spring.servlet.multipart.enabled=true
    spring.servlet.multipart.max-file-size=5MB
    spring.servlet.multipart.max-request-size=5MB

    spring.thymeleaf.cache=false
    spring.thymeleaf.enabled=true
    spring.thymeleaf.prefix=classpath:/templates/
    spring.thymeleaf.suffix=.html

    spring.datasource.driverClassName=org.postgresql.Driver
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    
### application-dev.properties

    # Set corresponding Config Vars on heroku for these. They should be injected.
    # Set spring.profiles.active=dev in application.properties
    
    #####################
    # Database on Heroku
    #####################
    spring.datasource.url=jdbc:postgresql://DB_URL:5432/DB_NAME?user=DB_USER&password=DB_PASSWORD&sslmode=require

    #####################
    # Mail Setup
    #####################
    spring.mail.host=
    spring.mail.username=
    # Reply-to email adress; you must own this email adress
    spring.mail.sender=
    spring.mail.password=
    spring.mail.port=587
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

    #####################
    # Shop Admin Account
    #####################
    spring.security.user.name=
    spring.security.user.password=


## <a name="depend">Dependencies</a>

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Material Design Lite](https://getmdl.io/)


