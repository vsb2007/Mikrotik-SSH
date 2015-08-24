import java.util.ArrayList;

/**
 * Created by VSB on 24.08.2015.
 */
public class getIpFromMikToMysql {
    public static void getIpFromMikToMysql(String lines)
    {
        ArrayList<String> list = new ArrayList<String>();

        String[] strings = lines.split("\n");
        for (int i=0; i<strings.length; i++)
        {
            //System.out.println(strings[i]);
            String[]words = strings[i].split(" ");

           for (int j=0; j < words.length; j++) {
               String[] word2 = words[j].split("=");
               //System.out.println(word2[0]);
               if (word2.length>1)
               {
                   if (word2[0].equals("address")) {
                       //System.out.println(word2[0] + " " + word2[1]);
                       list.add(word2[1]);
                   }
               }
            }

        }


    }

}
