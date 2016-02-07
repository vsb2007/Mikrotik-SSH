import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by VSB on 24.08.2015.
 */
public class NewIpToMysql {
    public static void newIpToMysql(String filename, MysqlConnection connection) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(filename));
        String sql = "TRUNCATE list1";
        connection.putInsertQuery(sql);
        sql = "insert into list1 (ip1) values ";
        int i = 0;
        while (input.ready()) {
            String line = input.readLine();
            sql += "(\"" + line + "\")";
            i++;
            if (i > 1000) {
                connection.putInsertQuery(sql);
                i = 0;
                sql = "insert into list1 (ip1) values ";
                continue;
            }
            if (input.ready()) sql += ",";
        }
        connection.putInsertQuery(sql);
    }
}
