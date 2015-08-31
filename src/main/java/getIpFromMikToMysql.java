

/**
 * Created by VSB on 24.08.2015.
 */
public class getIpFromMikToMysql {
    public static void getIpFromMikToMysql(String lines,mysqlConnection connection, String dropLIST) throws Exception
    {

        String[] strings = lines.split("\n");
        //String sql = "delete from list2";
        String sql = "TRUNCATE list2";
        connection.putInsertQuery(sql);
        sql = "insert into list2 (id_list2,ip2) values ";

        for (int i=0; i<strings.length-1; i++)
        {

            String[]words = strings[i].split(" ");
            String[]id = strings[i].split("list");
            String num_id = "";
            if (id.length>0)
            {
                 num_id = id[0].replace(" ","");
            }
            boolean flagList = false;
            for (int j=0; j < words.length; j++) {
                words[j] = words[j].replace(" ","");
                String[] word2 = words[j].split("=");

                if (word2.length>1)
                {
                    if (word2[0].equals("list")) {

                       if (word2[1].equals(dropLIST))
                       {
                           flagList = true;
                           continue;
                       }
                        else break;
                   }
                   if (word2[0].equals("address") && flagList) {
                       if (!sql.equals("insert into list2 (id_list2,ip2) values ")) sql += ",";
                       sql += "("+num_id+",\""+ word2[1] + "\")";
                       break;
                   }
               }
            }

        }


        if (strings.length-1 > 1)
        {
            connection.putInsertQuery(sql);
        }


    }

}
