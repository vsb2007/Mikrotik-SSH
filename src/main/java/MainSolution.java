import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSB on 05.08.2015.
 */
public class MainSolution {

    public static String HOSTNAME="";
    public static String USERNAME="";
    public static String PASSWORD="";
    public static String dbHOSTNAME="";
    public static String dbNAME="";
    public static String dbCLASS="";
    public static String dbUSERNAME="";
    public static String dbPASSWORD="";
    public static String ipFILE="";
    enum TestTableColumns{
        id_list1,ip1;
    }
    public static void main(String[] args) throws Exception{

        if (args.length==0)
        {
            System.out.printf("no params");
            System.exit(-1);
        }
        //System.out.println(args[0]);
        parseArguments.parseArguments(args[0]);
        mysqlConnection connection = new mysqlConnection(dbHOSTNAME,dbCLASS,dbNAME,dbUSERNAME,dbPASSWORD);
        newIpToMysql.newIpToMysql(ipFILE,connection);

        //System.exit(0);
        sshMikrotik manager = new sshMikrotik();
        //List<String> lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD);
        String command = "ip firewall address-list print terse where list=dropGos";
        String lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
        //System.out.println(lines);
        //System.out.println("111");
        getIpFromMikToMysql.getIpFromMikToMysql(lines);

        ResultSet resultSet = connection.getSelectQuery("select * from list1");

        /*while (resultSet.next()) {
            Integer id = resultSet.getInt(TestTableColumns.id_list1.toString());
            String text = resultSet.getString(TestTableColumns.ip1.toString());
            System.out.println("id: "+id);
            System.out.println("text: "+text);
        }*/
        //System.out.println(lines);
    }
}
