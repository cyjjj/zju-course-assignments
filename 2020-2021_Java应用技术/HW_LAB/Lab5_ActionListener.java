// import java.util.Arrays;
// import java.util.Scanner;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// //已有MyStarter类(你无需编写，直接使用)，其具有：
// // 构造函数：public  MyStarter(ActionListener ac)
// // 方法：start()启动任务

// // main方法执行流程：
// // 输入整数n和字符串x。
// // 创建MyStarter对象。该对象的任务为输出n个x字符串，并在循环结束后，使用如下代码
// //  System.out.println(this.getClass().getName());
// //  System.out.println(Arrays.toString(this.getClass().getInterfaces()));
// // 打印一些标识信息。 注意：MyStarter类的构造函数public MyStarter(ActionListener ac)要接收ActionListener类型的对象，我们需要建立这个对象并在该对象相应的方法中编写相关功能代码。
// // 最后：调用MyStarter对象的start方法启动任务。

// public class Lab5_ActionListener {
//     public static void main(String[] args) {
//         MyStarter starter;
//         // 这边写上你的代码
//         Scanner sc = new Scanner(System.in);
//         int n = sc.nextInt();
//         String x = sc.next();
//         // 创建匿名内部类
//         ActionListener ac = new ActionListener() {
//             // 构建代码块
//             {
//                 for (int i = 0; i < n; i++)
//                     System.out.println(x);
//                 System.out.println(this.getClass().getName());
//                 System.out.println(Arrays.toString(this.getClass().getInterfaces()));
//             }

//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // TODO Auto-generated method stub
//             }

//         };
//         starter = new MyStarter(ac);

//         starter.start();
//         sc.close();
//     }
// }
