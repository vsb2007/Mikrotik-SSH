import java.sql.*;

public class MysqlConnection {
    private String dbUrl;
    private String dbClass;
    private String dbName;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public MysqlConnection(String dbUrl, String dbClass, String dbName, String username, String password) throws Exception {
        this.dbUrl = dbUrl + "/" + dbName;
        this.dbClass = dbClass;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        //Class.forName(dbClass);
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(this.dbUrl, username, password);
        statement = connection.createStatement();
    }

    public ResultSet getSelectQuery(String query) throws Exception {
        return statement.executeQuery(query);
    }

    public void putInsertQuery(String query) throws Exception {
        preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}

