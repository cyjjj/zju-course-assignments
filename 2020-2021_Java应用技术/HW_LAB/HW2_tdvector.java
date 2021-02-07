import java.util.Scanner;

class TDVector {
    private double x;
    private double y;

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    /** 你所提交的代码将被嵌在这里（替换此行） **/

    // 无参构造，向量的x和y默认为0
    public TDVector() {
        x = 0.0;
        y = 0.0;
    }

    // 按照参数构造向量的x和y
    public TDVector(double a, double b) {
        x = a;
        y = b;
    }

    // 按照向量b构造向量
    public TDVector(TDVector b) {
        x = b.getX();
        y = b.getY();
    }

    // getX
    public double getX() {
        return x;
    }

    // getY
    public double getY() {
        return y;
    }

    // setX
    public void setX(double new_x) {
        x = new_x;
    }

    // setY
    public void setY(double new_y) {
        y = new_y;
    }

    // add
    public TDVector add(TDVector b) {
        return new TDVector(this.x + b.getX(), this.y + b.getY());
    }
}

public class HW2_tdvector {
    public static void main(String[] args) {
        TDVector a = new TDVector(); /* 无参构造，向量的x和y默认为0 */
        Scanner sc = new Scanner(System.in);
        double x, y, z;
        x = sc.nextDouble();
        y = sc.nextDouble();
        z = sc.nextDouble();
        TDVector b = new TDVector(x, y); /* 按照参数构造向量的x和y */
        TDVector c = new TDVector(b); /* 按照向量b构造向量 */
        a.setY(z);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        c.setX(z);
        a = b.add(c);
        System.out.println(a);
        System.out.println("b.x=" + b.getX() + " b.y=" + b.getY());
        sc.close();
    }
}