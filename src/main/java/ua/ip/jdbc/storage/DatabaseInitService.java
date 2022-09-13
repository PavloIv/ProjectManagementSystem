package ua.ip.jdbc.storage;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.Properties;

public class DatabaseInitService {
    public void initDb(String Url, String user,String pass){
        Flyway flyway = Flyway
                .configure()
                .dataSource(Url,user,pass)
                .load();
        flyway.migrate();
    }
}
