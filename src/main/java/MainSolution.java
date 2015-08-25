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
    enum TableColumns1{
        id_list1,ip1,id_list2,ip2;
    }
    enum TableColumns2{
        id_list2,ip2,id_list1,ip1;
    }
    public static void main(String[] args) throws Exception{

        if (args.length==0)
        {
            System.out.printf("no params");
            System.exit(-1);
        }
        parseArguments.parseArguments(args[0]);
        mysqlConnection connection = new mysqlConnection(dbHOSTNAME,dbCLASS,dbNAME,dbUSERNAME,dbPASSWORD);
        newIpToMysql.newIpToMysql(ipFILE,connection);

        sshMikrotik manager = new sshMikrotik();
        String command;// = "ip firewall address-list print terse where list=dropGos";
        String lines; //= manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
        command = "ip firewall address-list print terse where list=dropGos";
        lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);

        getIpFromMikToMysql.getIpFromMikToMysql(lines,connection);

        //String sql = "select * from (select * from list2 left join list1 on ip2 = ip1 ) sel where sel.ip1 is null";
        ResultSet resultSet;// = connection.getSelectQuery(
        resultSet = connection.getSelectQuery(
                "select * from (select * from list2 left join list1 on ip2 = ip1 ) sel where sel.ip1 is null");

        command = "";

        int count2=0;
        command += "ip firewall address-list remove numbers=";
        boolean flag1 = false;
        while (resultSet.next()) {
            flag1 = true;
            count2++;
            Integer id = resultSet.getInt(TableColumns2.id_list2.toString());
            String text = resultSet.getString(TableColumns2.ip2.toString());
            //System.out.println("id: "+id);
            //System.out.println("text: "+text);
            //ip firewall address-list remove [find address=192.168.0.2]
            command += id;
            if (count2%100==0)
            {
                lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
                //System.out.println(command);
                command="ip firewall address-list remove numbers=";
                continue;
            }
            if (!resultSet.isLast()) command += ",";


        }
        if (flag1){
            lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
            flag1=false;
        }

        resultSet = connection.getSelectQuery(
                "select * from (select * from list1 left join list2 on ip1 = ip2 ) sel where sel.ip2 is null group by ip1");

        command = "";

        int count=0;
        while (resultSet.next()) {
            flag1=true;
            count++;
            Integer id = resultSet.getInt(TableColumns1.id_list1.toString());
            String text = resultSet.getString(TableColumns1.ip1.toString());
            command += "ip firewall address-list add list=dropGos address="+text+";";
            if (count%100==0)
            {
                lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
                //System.out.println(command);
                command="";
            }
        }
        //System.out.println(count2);
        if (flag1){
            lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
            flag1=false;
        }

        //System.out.println(lines);
    }
}
