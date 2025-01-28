//package edu.java.scrapper.repository;
//
//import liquibase.Contexts;
//import liquibase.LabelExpression;
//import liquibase.Liquibase;
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.exception.LiquibaseException;
//import liquibase.resource.ClassLoaderResourceAccessor;
//import liquibase.resource.SearchPathResourceAccessor;
//import lombok.SneakyThrows;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.JdbcDatabaseContainer;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import java.io.File;
//import java.sql.DriverManager;
//import static org.postgresql.core.ConnectionFactory.openConnection;
//
//@Testcontainers
//public abstract class IntegrationTest {
//    public static PostgreSQLContainer<?> POSTGRES;
//
//    static {
//        POSTGRES = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("scrapper")
//            .withUsername("weuizx")
//            .withPassword("weuizx");
//        POSTGRES.start();
//
//        runMigrations(POSTGRES);
//    }
//
//    @SneakyThrows
//    private static void runMigrations(JdbcDatabaseContainer<?> c){
//        java.sql.Connection connection = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());
//
//        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//        String path =
//            new File(".").toPath().toAbsolutePath().getParent().getParent().resolve("migrations").toString();
//
//        Liquibase liquibase =
//            new liquibase.Liquibase("master.xml", new SearchPathResourceAccessor(path), database);
//
//        liquibase.update(new Contexts(), new LabelExpression());
//    }
//
//    @DynamicPropertySource
//    static void jdbcProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
//        registry.add("spring.datasource.username", POSTGRES::getUsername);
//        registry.add("spring.datasource.password", POSTGRES::getPassword);
//    }
//}
