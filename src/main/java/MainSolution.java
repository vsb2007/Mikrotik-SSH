import java.sql.ResultSet;

/**
 * Created by VSB on 05.08.2015.
 */
public class MainSolution {
    public static String HOSTNAME = "";
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String dropLIST = "";
    public static String dbHOSTNAME = "";
    public static String dbNAME = "";
    public static String dbCLASS = "";
    public static String dbUSERNAME = "";
    public static String dbPASSWORD = "";
    public static String ipFILE = "";

    enum TableColumns1 {
        id_list1, ip1, id_list2, ip2
    }

    enum TableColumns2 {
        id_list2, ip2, id_list1, ip1
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.printf("no params");
            System.exit(-1);
        }
        ParseArguments.parseArguments(args[0]);
        MysqlConnection connection = new MysqlConnection(dbHOSTNAME, dbCLASS, dbNAME, dbUSERNAME, dbPASSWORD);
        NewIpToMysql.newIpToMysql(ipFILE, connection);
        SshMikrotik manager = new SshMikrotik();
        String command;
        String lines;
        command = "ip firewall address-list print terse";
        lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
        GetIpFromMikToMysql.getIpFromMikToMysql(lines, connection, dropLIST);
        ResultSet resultSet;// = connection.getSelectQuery(
        resultSet = connection.getSelectQuery(
                "select * from (select * from list2 left join list1 on ip2 = ip1 ) sel where sel.ip1 is null order by id_list2 desc");
        command = "";
        int count2 = 0;
        command += "ip firewall address-list remove numbers=";
        boolean flag1 = false;
        while (resultSet.next()) {
            flag1 = true;
            count2++;
            Integer id = resultSet.getInt(TableColumns2.id_list2.toString());
            //String text = resultSet.getString(TableColumns2.ip2.toString());
            command += id;
            if (count2 % 100 == 0) {
                manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
                command = "ip firewall address-list remove numbers=";
                continue;
            }
            if (!resultSet.isLast()) command += ",";
        }
        if (flag1) {
            manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
            flag1 = false;
        }
        resultSet = connection.getSelectQuery(
                "select * from (select * from list1 left join list2 on ip1 = ip2 ) sel where sel.ip2 is null group by ip1");
        command = "";
        int count = 0;
        while (resultSet.next()) {
            flag1 = true;
            count++;
            Integer id = resultSet.getInt(TableColumns1.id_list1.toString());
            String text = resultSet.getString(TableColumns1.ip1.toString());
            command += "ip firewall address-list add list=" + dropLIST + " address=" + text + ";";
            if (count % 100 == 0) {
                manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
                command = "";
            }
        }
        if (flag1) {
            manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
            //flag1 = false;
        }
    }
}
