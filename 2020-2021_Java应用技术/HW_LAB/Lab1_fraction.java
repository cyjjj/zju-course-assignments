import java.util.Scanner;

public class Lab1_fraction {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Fraction a = new Fraction(in.nextInt(), in.nextInt());
        Fraction b = new Fraction(in.nextInt(), in.nextInt());
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.plus(b));
        System.out.println(a.multiply(b).plus(new Fraction(5, 6)));
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.toDouble());
        in.close();
    }
}

/* 请在这里填写答案 */
// 第4个测试点：最大的int，负数---答案错误
class Fraction {
    private int numerator; // 分子
    private int denominator; // 分母

    // 构造函数
    public Fraction(int a, int b) {
        int abs_nu = Math.abs(a);
        int abs_de = Math.abs(b);
        // 符号，负数符号放在分子
        if (a > 0 && b < 0) {
            a = -abs_nu;
            b = abs_de;
        } else if (a < 0 && b < 0) {
            a = abs_nu;
            b = abs_de;
        }
        // 化简
        int gcd = gcd(abs_nu, abs_de); // 最大公因数，用于化简
        this.numerator = a / gcd;
        this.denominator = b / gcd;
    }

    // 求最大公因数
    public int gcd(int a, int b) {
        int min = a > b ? b : a;
        int max = a > b ? a : b;
        if (min == 0)
            return max;
        else
            return gcd(max % min, min);
    }

    // 将分数转换为double
    public double toDouble() {
        return (double) numerator / denominator;
    }

    // 将自己的分数和r的分数相加，产生一个新的Fraction的对象
    public Fraction plus(Fraction r) {
        return new Fraction(numerator * r.denominator + r.numerator * denominator, denominator * r.denominator);
    }

    // 将自己的分数和r的分数相乘，产生一个新的Fraction的对象
    public Fraction multiply(Fraction r) {
        return new Fraction(numerator * r.numerator, denominator * r.denominator);
    }

    // 将自己以“分子/分母”的形式产生一个字符串
    // 如果分数是1/1，应该输出"1"。当分子大于分母时，不需要提出整数部分。
    @Override
    public String toString() {
        if (numerator == 0)
            return "0";
        else if (denominator == numerator)
            return "1";
        else
            return (numerator + "/" + denominator);
    }
}
