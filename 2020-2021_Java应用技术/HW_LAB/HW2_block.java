import java.util.*;

public class HW2_block {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<Book>();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            String str = in.nextLine();
            String[] data = str.split(",");
            Book book = new Book(data[0], Integer.parseInt(data[1]), data[2], Integer.parseInt(data[3]));
            books.add(book);
        }

        System.out.println(totalprice(books));
    }

    /* 计算所有book的总价 */
    public static int totalprice(List<Book> books) {
        int result = 0;
        for (int i = 0; i < books.size(); i++) {
            result += books.get(i).getPrice();
        }
        return result;
    }
}

/* 请在这里填写答案 */
// 该类有四个私有属性,分别是书籍名称、价格、作者、出版年份，以及相应的set与get方法
// 该类有一个含有四个参数的构造方法，这四个参数依次是书籍名称、价格、作者、出版年份 。
class Book {
    private String name;
    private int price;
    private String author;
    private int year;

    // 构造函数
    public Book(String name, int price, String author, int year) {
        this.name = name;
        this.price = price;
        this.author = author;
        this.year = year;
    }

    // set函数
    public void setName(String new_name) {
        name = new_name;
    }

    public void setPrice(int new_price) {
        price = new_price;
    }

    public void setAuthor(String new_author) {
        author = new_author;
    }

    public void setYear(int new_year) {
        year = new_year;
    }

    // get函数
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}