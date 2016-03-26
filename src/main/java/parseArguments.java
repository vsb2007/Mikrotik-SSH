import java.io.BufferedReader;
import java.io.FileReader;

public class ParseArguments {
    public static void parseArguments(String arg) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(arg));
        String line;
        while ((line = input.readLine()) != null) {
            String param = "";
            String value = "";
            char[] chars = line.toCharArray();
            boolean flag = false;
            if (chars[0] == '#' || chars[0] == ';') continue;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '=') {
                    flag = true;
                    continue;
                }
                if (chars[i] != ' ' && !flag) {
                    param += chars[i];
                    continue;
                }
                if (flag) {
                    if (chars[i] != ' ' && chars[i] != '"' && chars[i] != ';') {
                        value += chars[i];
                    }
                }
            }
            if (param.equals("mik_host")) {
                MainSolution.HOSTNAME = value;
            } else if (param.equals("mik_user")) {
                MainSolution.USERNAME = value;
            } else if (param.equals("mik_password")) {
                MainSolution.PASSWORD = value;
            } else if (param.equals("mik_list")) {
                MainSolution.dropLIST = value;
            } else if (param.equals("db_url")) {
                MainSolution.dbHOSTNAME = value;
            } else if (param.equals("db_name")) {
                MainSolution.dbNAME = value;
            } else if (param.equals("db_username")) {
                MainSolution.dbUSERNAME = value;
            } else if (param.equals("db_password")) {
                MainSolution.dbPASSWORD = value;
            } else if (param.equals("db_class")) {
                MainSolution.dbCLASS = value;
            } else if (param.equals("ip_file")) {
                MainSolution.ipFILE = value;
            }
        }
    }
}
