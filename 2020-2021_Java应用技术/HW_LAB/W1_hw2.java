import java.util.Scanner;

public class W1_hw2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String old_word = in.nextLine();
        String new_word = in.nextLine();
        in.close();
        // 替换的单元是单词，若某单词中有子字符串符合，不替换
        // 将以1个或多个空格分隔开的单词分割并放入字符串数组，分别判断各单词
        String[] words = str.split("\\s+");
        int i;
        for (i = 0; i < words.length; i++) {
            if (words[i].equals(old_word))
                words[i] = new_word;
        }
        String result = words[0];
        for (i = 1; i < words.length; i++)
            result += " " + words[i];
        System.out.println(result);
        //in.close();
    }
}
