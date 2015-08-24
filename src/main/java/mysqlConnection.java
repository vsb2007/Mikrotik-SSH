/**
 * Created by VSB on 06.08.2015.
 */
import java.sql.*;

public class mysqlConnection {
        private String dbUrl;
        private String dbClass;
        private String dbName;
        private String username;
        private String password;

    public mysqlConnection(String dbUrl, String dbClass, String dbName, String username, String password) {
        this.dbUrl = dbUrl+"/"+dbName;
        this.dbClass = dbClass;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    enum TestTableColumns{
        id_list1,ip1;
    }
    public void testConnect() {

            String query = "select * from "+dbName+".list1";
            System.out.println(dbUrl);
            try {
                Class.forName(dbClass);
                connection = DriverManager.getConnection(dbUrl, username, password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Integer id = resultSet.getInt(TestTableColumns.id_list1.toString());
                    String text = resultSet.getString(TestTableColumns.ip1.toString());
                    System.out.println("id: "+id);
                    System.out.println("text: "+text);
                }
                connection.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

