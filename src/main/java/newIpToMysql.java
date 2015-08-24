import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;

/**
 * Created by VSB on 24.08.2015.
 */
public class newIpToMysql {
    public static void newIpToMysql(String filename,mysqlConnection connection) throws Exception
    {
        BufferedReader input = new BufferedReader(new FileReader(filename));
        //String line;
        String sql = "delete from list1";
        connection.putInsertQuery(sql);
        sql = "insert into list1 (ip1) values ";
        //String sql = "";
        int i = 0;
        while (input.ready()) {
            String line = input.readLine();
            sql += "(\""+ line + "\")";
            i++;
            if (i>10) break;
            if (input.ready()) sql += ",";
        }
        //sql += "";
        connection.putInsertQuery(sql);
        //System.out.println(sql);
    }
}
