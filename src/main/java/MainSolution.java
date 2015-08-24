import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSB on 05.08.2015.
 */
public class MainSolution {

    private static String HOSTNAME="";
    private static String USERNAME="";
    private static String PASSWORD="";
    public static String dbHOSTNAME="";
     static String dbNAME="";
     static String dbCLASS="";
     static String dbUSERNAME="";
     static String dbPASSWORD="";

    public static void main(String[] args) throws Exception{

        if (args.length==0)
        {
            System.out.printf("no params");
            System.exit(-1);
        }
        System.out.println(args[0]);
        BufferedReader input = new BufferedReader(new FileReader(args[0]));
        String line;
        while ((line = input.readLine()) != null) {
            String param="";
            String value="";
            char[] chars = line.toCharArray();
            boolean flag=false;
            if (chars[0] == '#' || chars[0] == ';') continue;
            for (int i=0; i<chars.length; i++)
            {
                if (chars[i] == '=')
                {
                    flag = true;
                    continue;
                }
                if (chars[i]!=' ' && !flag)
                {
                    param += chars[i];
                    continue;
                }
                if (flag)
                {
                    if (chars[i]!=' ' && chars[i]!='"' && chars[i]!=';')
                    {
                        value += chars[i];
                    }
                }

            }
            if (param.equals("mik_host"))
            {
                HOSTNAME = value;
                //System.out.println(HOSTNAME);
            }
            else if (param.equals("mik_user"))
            {
                USERNAME = value;
                //System.out.println(USERNAME);
            }
            else if (param.equals("mik_password"))
            {
                PASSWORD = value;
                //System.out.println(PASSWORD);
            }
            else if (param.equals("db_url"))
            {
                dbHOSTNAME = value;
                //System.out.println(dbHOSTNAME);
            }
            else if (param.equals("db_name"))
            {
                dbNAME = value;
                //System.out.println(dbNAME);
            }
            else if (param.equals("db_username"))
            {
                dbUSERNAME = value;
                //System.out.println(dbUSERNAME);
            }
            else if (param.equals("db_password"))
            {
                dbPASSWORD = value;
                //System.out.println(dbPASSWORD);
            }
            else if (param.equals("db_class"))
            {
                dbCLASS = value;
                //System.out.println(dbCLASS);
            }
            else {
                //System.out.println(line);
            }
        }

        //System.exit(0);
        sshMikrotik manager = new sshMikrotik();
        //List<String> lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD);
        String command = "ip firewall address-list print terse where list=dropGos";
        String lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
        //System.out.println(lines);
        //System.out.println("111");
        String[] list = lines.split("\n");
        for (int i=0; i<list.length; i++)
        {
            //System.out.println(list[i]);
        }


        mysqlConnection connection = new mysqlConnection(dbHOSTNAME,dbCLASS,dbNAME,dbUSERNAME,dbPASSWORD);
        connection.testConnect();
        //String command2 = "ip firewall address-list add list=dropGos address=192.168.0.2";
        //lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD,command2);


        //System.out.println(lines);
    }
}
