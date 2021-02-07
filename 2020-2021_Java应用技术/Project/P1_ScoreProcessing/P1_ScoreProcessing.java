import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

// Student's name and student id, as <student id>, <name>, and
// Score for one student of one course, as <student id>, <course name>, <marks>.
class MyStudent {
    public String id;
    public String name;
    public double ScoreSum = 0.0; // 分数总和
    public int CourseNum = 0; // 课程数
    public HashMap<String, Double> CouMap = new HashMap<>(); // 课程名，分数

    public MyStudent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MyStudent(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addOneCourse(String name, double score) {
        this.CourseNum += 1;
        this.ScoreSum += score;
        CouMap.put(name, score);
    }

    public double getAvg() {
        if (CourseNum == 0)
            return 0;
        else
            return (double) ScoreSum / CourseNum;
    }

}

public class P1_ScoreProcessing {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        HashMap<String, MyStudent> StuMap = new HashMap<>(); // 学号，姓名
        TreeSet<String> stu = new TreeSet<>(); // TreeSet 有序集合
        TreeSet<String> cou = new TreeSet<>();

        // Input
        while (in.hasNext()) {
            line = in.nextLine();
            if (line.equals("END"))
                break;
            String[] info = line.split("\\,\\s"); // 分割,','隔开
            if (info.length == 2) {// Student id & name
                String id = info[0];
                String stu_name = info[1];
                if (StuMap.containsKey(id)) { // 已存在id/先插入了课程
                    if (StuMap.get(id).name == null || StuMap.get(id).name.length() == 0) {
                        StuMap.get(id).setName(stu_name);
                    } else if (!StuMap.get(id).name.equals(stu_name)) {
                        System.out.println("Error! Conflict student info!");
                    }
                } else { // 不存在id
                    StuMap.put(id, new MyStudent(id, stu_name));
                    stu.add(id);
                }
            } else if (info.length == 3) {// student id & course name & score
                String stu_id = info[0];
                String cou_name = info[1];
                Double score = Double.parseDouble(info[2]);

                if (!StuMap.containsKey(stu_id)) { // 没有该学生
                    StuMap.put(stu_id, new MyStudent(stu_id));
                    stu.add(stu_id);
                }
                StuMap.get(stu_id).addOneCourse(cou_name, score);
                cou.add(cou_name);
            }
        }
        in.close();

        // 输出第一行
        System.out.print("student id, name");
        for (Iterator<String> iter = cou.iterator(); iter.hasNext();) {
            System.out.print(", " + iter.next());
        }
        System.out.println(", average");
        // 输出每个学生信息
        for (String id : stu) {
            String name = StuMap.get(id).name;
            System.out.print(id + ", " + name); // 学号，姓名
            MyStudent current_stu = StuMap.get(id);
            for (String cou_name : cou) { // 每一门课成绩
                if (current_stu.CouMap.containsKey(cou_name))
                    System.out.printf(", %.1f", current_stu.CouMap.get(cou_name));
                else
                    System.out.print(", ");
            }
            System.out.printf(", %.1f\n", current_stu.getAvg());
        }
    }
}
