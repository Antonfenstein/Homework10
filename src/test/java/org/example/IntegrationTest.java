package org.example;

import db.DBManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class IntegrationTest {

    @Test
    public void dummy() throws SQLException{
        DBManager dBManager = new DBManager();
        Connection connection = dBManager.connect();
        dBManager.close(connection);
    }
}
