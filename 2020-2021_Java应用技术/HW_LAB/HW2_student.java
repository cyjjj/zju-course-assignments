import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class HW2_student {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Student[] stus = new Student[3];

        for (int i = 0; i < 3; i++) {
            int no = scan.nextInt();
            String name = scan.next();
            Student s = new Student(no, name);
            stus[i] = s;
        }
        // 将stus中的3个学生对象，放入到HashSet中
        HashSet<Student> stuSet = new HashSet<Student>();
        for (Student s : stus) {
            stuSet.add(s);
        }
        // 要放入的第4个Student
        Student fourth = new Student(scan.nextInt(), scan.next());
        stuSet.add(fourth);// 如果fourth的学号（no）与stuSet中的已有学生的no重复则无法放入
        System.out.println(stuSet.size());

        Arrays.sort(stus);// 对stus中的3个原有对象，按照姓名首字符有小到大排序
        for (int i = 0; i < stus.length; i++) {
            System.out.println(stus[i]);// 输出的格式为：no=XX&name=YY
        }

        scan.close();
    }
}

class Student implements Comparable<Student> {
    int no; // 学号
    String name; // 姓名

    public Student() {
        no = 0;
        name = "";
    }

    public Student(int a, String b) {
        no = a;
        name = b;
    }

    @Override
    public String toString() {
        return ("no=" + no + "&name=" + name);
    }

    @Override
    public int compareTo(Student arg0) {
        int i, j;
        int length1 = name.length(), length2 = arg0.name.length();
        for (i = 0, j = 0; i < length1 && j < length2; i++, j++) { // 依次比较每个字母
            if (name.charAt(i) < arg0.name.charAt(j))
                return -1;
            else if (name.charAt(i) > arg0.name.charAt(j))
                return 1;
        }
        if (i == length1 && j < length2)
            return -1;
        else if (i < length1 && j == length2)
            return 1;
        return 0;
    }

    @Override
    public int hashCode() {
        return no;
    }

    @Override
    public boolean equals(Object obj) {
        Student add_Student = (Student) obj;
        if (no == add_Student.no)
            return true;
        return false;
    }
}