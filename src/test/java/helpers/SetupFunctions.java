package helpers;

import com.google.gson.Gson;
import dto.Credentials;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {
    String baseUrl;
    String login;
    String password;
    String dbhost;
    String dbname;
    String dbport;
    String dbusername;
    String dbpassword;

    public String getDbhost() {
        return dbhost;
    }

    public String getDbname() {
        return dbname;
    }

    public String getDbport() {
        return dbport;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public SetupFunctions() {
        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            baseUrl = properties.getProperty("baseUrl");
            login = properties.getProperty("login");
            password = properties.getProperty("password");
            dbhost = properties.getProperty("dbhost");
            dbname = properties.getProperty("dbname");
            dbpassword = properties.getProperty("dbpassword");
            dbport = properties.getProperty("dbport");
            dbusername = properties.getProperty("dbusername");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String createUser() {
        Credentials credentials = new Credentials(login, password);
        Gson gson = new Gson();
        return gson.toJson(credentials);
    }

    public String getToken() {

        return given().
                header("Content-type", "application/json").
                log().
                all().
                body(createUser()).
                when().
                post(baseUrl + "/login/student").
                then().
                log().
                all().
                extract().
                response().
                asString();
    }

    public String generateRandomName() {
        return RandomStringUtils.random(8, true, false);

    }

    public String generateRandomPassword() {
        return RandomStringUtils.random(10, true, true);

    }
}
