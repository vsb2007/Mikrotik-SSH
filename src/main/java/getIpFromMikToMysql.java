import java.util.ArrayList;

/**
 * Created by VSB on 24.08.2015.
 */
public class getIpFromMikToMysql {
    public static void getIpFromMikToMysql(String lines,mysqlConnection connection) throws Exception
    {
        //ArrayList<String> list = new ArrayList<String>();

        String[] strings = lines.split("\n");
        String sql = "delete from list2";
        connection.putInsertQuery(sql);
        sql = "insert into list2 (id_list2,ip2) values ";

        for (int i=0; i<strings.length-1; i++)
        {
            //System.out.println(strings[i]);
            String[]words = strings[i].split(" ");
            String[]id = strings[i].split("list");
            String num_id = "";
            if (id.length>0)
            {
                 num_id = id[0].replace(" ","");
            }


           for (int j=0; j < words.length; j++) {
               String[] word2 = words[j].split("=");
               //System.out.println(word2[0]);
               if (word2.length>1)
               {
                   if (word2[0].equals("address")) {
                       //System.out.println(word2[0] + " " + word2[1]);
                       sql += "("+num_id+",\""+ word2[1] + "\")";
                       //list.add(word2[1]);
                   }
               }
            }
            if (i<strings.length-1-1) sql += ",";
        }

        //System.out.println(sql);
        if (strings.length-1 > 1)
        {
            connection.putInsertQuery(sql);
        }


    }

}
