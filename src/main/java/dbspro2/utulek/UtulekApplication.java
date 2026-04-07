package dbspro2.utulek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class UtulekApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(UtulekApplication.class, args);
    }

}
