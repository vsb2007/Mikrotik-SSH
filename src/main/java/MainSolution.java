import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSB on 05.08.2015.
 */
public class MainSolution {

    private static final String HOSTNAME = "172.16.1.1";
    private static final String USERNAME = "denyUser";
    private static final String PASSWORD = "111";

    public static void main(String[] args) {

        sshMikrotik manager = new sshMikrotik();
        //List<String> lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD);
        String command = "ip firewall address-list print terse where list=dropGos";
        String lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD, command);
        //System.out.println(lines);
        //System.out.println("111");
        String[] list = lines.split("\n");
        for (int i=0; i<list.length; i++)
        {
            System.out.println(list[i]);
        }


        mysqlConnection connection = new mysqlConnection();
        connection.testConnect();
        //String command2 = "ip firewall address-list add list=dropGos address=192.168.0.2";
        //lines = manager.connectAndExecuteListCommand(HOSTNAME, USERNAME, PASSWORD,command2);


        //System.out.println(lines);
    }
}
