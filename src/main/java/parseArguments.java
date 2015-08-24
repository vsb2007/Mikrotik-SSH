import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by VSB on 24.08.2015.
 */
public class parseArguments {
    public static void parseArguments(String arg) throws Exception
    {
        BufferedReader input = new BufferedReader(new FileReader(arg));
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
                MainSolution.HOSTNAME = value;
                //System.out.println(HOSTNAME);
            }
            else if (param.equals("mik_user"))
            {
                MainSolution.USERNAME = value;
                //System.out.println(USERNAME);
            }
            else if (param.equals("mik_password"))
            {
                MainSolution.PASSWORD = value;
                //System.out.println(PASSWORD);
            }
            else if (param.equals("db_url"))
            {
                MainSolution.dbHOSTNAME = value;
                //System.out.println(dbHOSTNAME);
            }
            else if (param.equals("db_name"))
            {
                MainSolution.dbNAME = value;
                //System.out.println(dbNAME);
            }
            else if (param.equals("db_username"))
            {
                MainSolution.dbUSERNAME = value;
                //System.out.println(dbUSERNAME);
            }
            else if (param.equals("db_password"))
            {
                MainSolution.dbPASSWORD = value;
                //System.out.println(dbPASSWORD);
            }
            else if (param.equals("db_class"))
            {
                MainSolution.dbCLASS = value;
                //System.out.println(dbCLASS);
            }
            else if (param.equals("ip_file"))
            {
                MainSolution.ipFILE = value;
                //System.out.println(dbCLASS);
            }
            else {
                //System.out.println(line);
            }
        }
    }
}
