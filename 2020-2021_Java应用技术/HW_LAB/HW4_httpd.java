import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
// ctrl+z结束输入

public class HW4_httpd {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<String, Integer> WebNum = new HashMap<>();
        String line, Row, Website;
        while (in.hasNextLine()) {
            line = in.nextLine();
            String[] s = line.split("\"");
            Row = s[1]; // POST /login.php HTTP/1.1
            String[] row_str = Row.split("\\s+");
            if (row_str[0].equals("GET")) { 
                String[] res_str = row_str[1].split("\\?"); // /showCourse.php?id=57
                Website = res_str[0];
                if (WebNum.containsKey(Website))
                    WebNum.put(Website, WebNum.get(Website) + 1);
                else
                    WebNum.put(Website, 1);
            } else if (row_str[0].equals("POST")) {
                Website = row_str[1]; // /login.php
                if (WebNum.containsKey(Website))
                    WebNum.put(Website, WebNum.get(Website) + 1);
                else
                    WebNum.put(Website, 1);
            }
        }
        in.close();
        Collection<Integer> num = WebNum.values();
        int max_num = Collections.max(num); // 最大访问量
        Set<String> Web_MaxNum = new TreeSet<>(); // 默认字母序
        for (Map.Entry<String, Integer> entry : WebNum.entrySet()) {
            String mapKey = entry.getKey();
            int mapValue = entry.getValue();
            if (mapValue == max_num)
                Web_MaxNum.add(mapKey); // 访问量最大的网址，字母序
        }
        for (String web : Web_MaxNum)
            System.out.println(max_num + ":" + web);
    }
}
