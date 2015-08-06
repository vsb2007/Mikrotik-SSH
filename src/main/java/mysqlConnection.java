/**
 * Created by VSB on 06.08.2015.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class mysqlConnection {

        public void testConnect() {
            String dbUrl = "jdbc:mysql://192.168.19.20/dropGos";
            String dbClass = "com.mysql.jdbc.Driver";
            String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
            String username = "dropGosUser";
            String password = "dropGosPassword";
            try {

                Class.forName(dbClass);
                Connection connection = DriverManager.getConnection(dbUrl,
                        username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String tableName = resultSet.getString(1);
                    System.out.println("Table name : " + tableName);
                }
                connection.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

