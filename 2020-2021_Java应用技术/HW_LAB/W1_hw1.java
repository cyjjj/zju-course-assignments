import java.util.Scanner;

public class W1_hw1 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String type;
    int i;
    while (true) {
      type = in.nextLine();
      // double 3个浮点数
      // 从左到右依次输出3个double(均保留2位小数输出，宽度为5)
      // 格式依次为：右侧填充空格，左侧填充空格，直接输出
      if (type.equals("double")) {
        double[] double_in = new double[3];
        for (i = 0; i < 3; i++)
          double_in[i]=in.nextDouble();
        System.out.printf("choice=%s\n%-5.2f,%5.2f,%.2f\n", type, double_in[0], double_in[1], double_in[2]);
        in.nextLine(); //Scanner.nextLine与Scanner的其他next函数混用有可能出错
      }
      // int 3个整数(以1个或多个空格分隔)
      // 将3个整数相加后输出
      else if (type.equals("int")) {
        int sum = 0;
        for (i = 0; i < 3; i++)
          sum += in.nextInt();
        System.out.printf("choice=%s\n%d\n", type, sum);
        in.nextLine();
      }
      // str 3个字符串
      // 去除空格，然后倒序输出3个字符
      else if (type.equals("str")) {
        String[] str = in.nextLine().split("\\s+");//将以1个或多个空格分隔开的字符串分割并放入字符串数组
        System.out.printf("choice=%s\n%s%s%s\n", type, str[2], str[1], str[0]);
      }
      // line 一行字符串
      // 转换成大写后输出
      else if (type.equals("line")) {
        String line_in = in.nextLine();
        String line_out = line_in.toUpperCase();
        System.out.printf("choice=%s\n%s\n", type, line_out);
      }
      // 输出other
      else {
        System.out.printf("choice=%s\nother\n", type);
      }
    }
    // in.close();
  }
}