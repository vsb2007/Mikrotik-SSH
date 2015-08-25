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
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public mysqlConnection(String dbUrl, String dbClass, String dbName, String username, String password) throws Exception{
        this.dbUrl = dbUrl+"/"+dbName;
        this.dbClass = dbClass;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

            connection = DriverManager.getConnection(this.dbUrl, username, password);
            Class.forName(dbClass);
            statement = connection.createStatement();
            //preparedStatement = connection.prepareStatement("insert into list1 values (default,?)");

    }
    public ResultSet getSelectQuery(String query) throws Exception{

        return statement.executeQuery(query);
    }
    public void putInsertQuery(String query) throws Exception
    {
        preparedStatement = this.connection.prepareStatement(query);
        //preparedStatement.setString(1,"");
        preparedStatement.executeUpdate();
        //System.out.println();
    }
}

