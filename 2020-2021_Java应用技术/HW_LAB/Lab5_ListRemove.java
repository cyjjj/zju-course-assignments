import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lab5_ListRemove {
    /* covnertStringToList函数代码 */
    /* 以空格(单个或多个)为分隔符，将line中的元素抽取出来，放入一个List */
    public static List<String> convertStringToList(String line) {
        String[] str = line.split("\\s+");
        List<String> StrList = new ArrayList<>();
        for (int i = 0; i < str.length; i++)
            StrList.add(str[i]);
        return StrList;
    }

    /* remove函数代码 */
    /* 在list中移除掉与str内容相同的元素 */
    public static void remove(List<String> list, String str) {
        if (!list.contains(str))
            return;
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                it.remove();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            List<String> list = convertStringToList(sc.nextLine());
            System.out.println(list);
            String word = sc.nextLine();
            remove(list, word);
            System.out.println(list);
        }
        sc.close();
    }
}
